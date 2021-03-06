package com.cami.web.controller;

import com.cami.persistence.model.AppelOffre;
import com.cami.persistence.model.Banque;
import com.cami.persistence.model.Caution;
import com.cami.persistence.model.Filiale;
import com.cami.persistence.model.LigneAppel;
import com.cami.persistence.model.Materiel;
import com.cami.persistence.model.Role;
import com.cami.persistence.model.TypeCaution;
import com.cami.persistence.service.IAppelOffreService;
import com.cami.persistence.service.IBanqueService;
import com.cami.persistence.service.ICautionService;
import com.cami.persistence.service.IFilialeService;
import com.cami.persistence.service.ILigneAppelService;
import com.cami.persistence.service.IMaterielService;
import com.cami.persistence.service.IRoleService;
import com.cami.persistence.service.ITypeCautionService;
import static com.cami.web.controller.FileController.SAVE_DIRECTORY;
import com.cami.web.form.AppelOffreForm;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/appeloffre")
public class AppelOffreController {

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private IAppelOffreService appelOffreService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IBanqueService banqueService;

    @Autowired
    private ILigneAppelService ligneAppelService;

    @Autowired
    private ICautionService cautionService;

    @Autowired
    private IMaterielService materielService;

    @Autowired
    private ITypeCautionService typeCautionService;

    @Autowired
    private IFilialeService filialeService;

    // API
    // read - one
    @RequestMapping(value = {"/{id}/show","/{id}/print-pdf"}, method = RequestMethod.GET)
    public String ShowAction(@PathVariable("id") final Long id, HttpServletRequest request,
            final ModelMap model) {
        String pattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
         System.out.println(pattern);
        final AppelOffre appelOffre;
        final List<Caution> cautions;
        appelOffre = appelOffreService.findOne(id);
        final List<LigneAppel> ligneAppels = ligneAppelService.filterByAppelOffre(appelOffre.getId());
        cautions = cautionService.filterByAppelOffre(appelOffre.getId());
        Role role = roleService.findOne(appelOffre.getUser().getId());
        appelOffre.setCautions(cautions);
        appelOffre.setLigneAppels(ligneAppels);
        appelOffre.setUser(role);
        final int nbFile = appelOffre.getFiles().size();
        model.addAttribute("appelOffre", appelOffre);
        model.addAttribute("nbFile", nbFile);
        model.addAttribute("user", role);
        model.addAttribute("ligneAppels", ligneAppels);
        model.addAttribute("cautions", cautions);
        System.out.println("this appelOffre is deleted ? " + appelOffre.isDeleted());
        if(pattern.contains("print-pdf")){
          return "appeloffre/print-pdf";
        }
        return "appeloffre/show";
    }

    @RequestMapping(value = "{id}/edit", method = RequestMethod.GET)
    public String editAction(@PathVariable("id") final Long id,
            final ModelMap model) {

        final AppelOffre appelOffre = appelOffreService.findOne(id);
        final List<LigneAppel> ligneAppels = ligneAppelService.filterByAppelOffre(appelOffre.getId());
        final List<Caution> cautions = cautionService.filterByAppelOffre(appelOffre.getId());
        final AppelOffreForm appelOffreForm = new AppelOffreForm();
        appelOffreForm.setAppelOffre(appelOffre);
        final int nbFile = appelOffre.getFiles().size();
        System.out.println("Le nombre de fichier est de " + nbFile);
        appelOffreForm.setCautions(cautions);
        appelOffreForm.setLigneAppels(ligneAppels);
        model.addAttribute("nbFile", nbFile);
        model.addAttribute("appelOffreForm", appelOffreForm);
        return "appeloffre/edit";
    }

    // read - all
    /**
     *
     * @param webRequest
     * @param model
     * <p>
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String indexAction(final ModelMap model, final WebRequest webRequest) {
        boolean deleted = false;
        final Long filialeId = (webRequest.getParameter("queryfiliale") != null && !webRequest.getParameter("queryfiliale").equals(""))
                ? Long.valueOf(webRequest.getParameter("queryfiliale"))
                : -1;
        final String numero = webRequest.getParameter("querynumero") != null
                ? webRequest.getParameter("querynumero").trim()
                : "";
        final String intitule = webRequest.getParameter("queryintitule") != null
                ? webRequest.getParameter("queryintitule").trim()
                : "";
        final String maitreDouvrage = webRequest.getParameter("querymaitredouvrage") != null
                ? webRequest.getParameter("querymaitredouvrage").trim()
                : "";
        if (webRequest.getParameter("querydeleted") != null) {
            if (webRequest.getParameter("querydeleted").equals("true")) {
                deleted = true;
            }
        }
        // POur la date
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

        final String debutPeriodeDepot = (webRequest.getParameter("querydebutperiode") != null)
                ? webRequest.getParameter("querydebutperiode")
                : "31/12/1975";
        final String finPeriodeDepot = webRequest.getParameter("queryfinperiode") != null
                ? webRequest.getParameter("queryfinperiode")
                : "31/12/9999";
        Date debutPeriode = new Date();
        Date finPeriode = new Date();
        try {
            debutPeriode = dateFormatter.parse(debutPeriodeDepot);
        } catch (ParseException ex) {
            try {
                debutPeriode = dateFormatter.parse("31/12/1975");
            } catch (ParseException ex1) {
                Logger.getLogger(CautionController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        try {
            finPeriode = dateFormatter.parse(finPeriodeDepot);
        } catch (ParseException ex) {
            try {
                finPeriode = dateFormatter.parse("31/12/9999");
            } catch (ParseException ex1) {
                Logger.getLogger(CautionController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

        final Integer page = webRequest.getParameter("page") != null
                ? Integer.valueOf(webRequest.getParameter("page"))
                : 0;
        final Integer size = webRequest.getParameter("size") != null
                ? Integer.valueOf(webRequest.getParameter("size"))
                : 25;

        final Page<AppelOffre> resultPage = appelOffreService.findPaginated(filialeId, numero, intitule, maitreDouvrage, debutPeriode, finPeriode, deleted, page, size);
        final AppelOffre appelOffre = new AppelOffre();
        appelOffre.setIntitule(intitule);
        appelOffre.setMaitreDouvrage(maitreDouvrage);
        appelOffre.setNumero(numero);
        appelOffre.setFiliale(new Filiale(filialeId));
        model.addAttribute("appelOffre", appelOffre);
        model.addAttribute("querydebutperiode", debutPeriodeDepot.equals("31/12/1975") ? "" : debutPeriodeDepot);
        model.addAttribute("queryfinperiode", finPeriodeDepot.equals("31/12/9999") ? "" : finPeriodeDepot);
        model.addAttribute("page", page);
        model.addAttribute("Totalpage", resultPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("appelOffres", resultPage.getContent());
        return "appeloffre/index";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newAction(final ModelMap model) {
        AppelOffreForm appelOffreForm = new AppelOffreForm();
        AppelOffre appelOffre = new AppelOffre();
        appelOffreForm.setAppelOffre(appelOffre);
        model.addAttribute("appelOffreForm", appelOffreForm);
        return "appeloffre/new";
    }

    /**
     *
     * @param appelOffreForm
     * @param files
     * @param result
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createAction(final ModelMap model, @RequestParam("fichiers") MultipartFile[] files,
            @Valid final AppelOffreForm appelOffreForm,
            final BindingResult result,
            final RedirectAttributes redirectAttributes) {

        if (result.hasErrors() || files[0].isEmpty()) {
            System.out.println("nul ou erreur and size = " + files.length);
            if (files[0].isEmpty()) {
                System.out.println("file error");
                model.addAttribute("fileError", "Veuillez téléverser au moins un fichier!");
            }
            model.addAttribute("error", "error");
            model.addAttribute("appelOffreForm", appelOffreForm);
            return "appeloffre/new";
        } else {
            System.out.println("non nul");
            redirectAttributes.addFlashAttribute("info", "alert.success.new");
            appelOffreService.create(appelOffreForm.getAppelOffre());
            if (!files[0].isEmpty()) {
                upload(files, appelOffreForm.getAppelOffre().getId());
            }
            return "redirect:/appeloffre/" + appelOffreForm.getAppelOffre().getId() + "/show";

        }
    }

    private AppelOffreForm generateAppelOffre() {
        AppelOffreForm appelOffreForm = new AppelOffreForm();
        AppelOffre appelOffre;
        List<Caution> cautions;
        List<LigneAppel> ligneAppels;
        int randomNum = ThreadLocalRandom.current().nextInt(1, 4);

        appelOffre = new AppelOffre();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        appelOffre.setDateDepot(new Date());
        appelOffre.setDelaiDeValidite("12");
        appelOffre.setEtat("EN COURS");
        appelOffre.setIntitule("TEST N-" + new Date().getTime());
        appelOffre.setMaitreDouvrage("KAPORAL--" + randomNum);
        appelOffre.setNumero("AP-" + new Date().getTime());
        appelOffre.setNumeroAffaire("AFN-" + new Date().getTime());
        appelOffre.setNumeroChrono("CHR-" + new Date().getTime());
        appelOffre.setFiliale(new Filiale(Long.valueOf(randomNum)));
        appelOffre.setDateModification(new Date());
        appelOffre.setDeleted(false);
        cautions = new ArrayList<>();

        System.out.println("Debut CREATION DES Caution ");
        for (int j = 1; j < 6; j++) {
            Caution caution = new Caution();
            caution.setBanque(new Banque(Long.valueOf(j)));
            caution.setTypeCaution(new TypeCaution(Long.valueOf(j)));
            caution.setDateDebut(new Date());
            Role role = new Role();
            role.setId(7L);
            int annee = 2016 + j;
            int jour = 2 * j;
            caution.setCommercial(role);
            caution.setDateFin(new Date(annee, j, jour));
            System.out.println("CAUTION date de fin = " + caution.getDateFin());
            caution.setDeleted(false);
            caution.setMontant(305090 * j);
            caution.setMontantMarche(78087 * j);
            caution.setNumero("CA-2018--00" + j);
            caution.setReferenceMarche("REF-2018--00" + j);
            cautions.add(caution);
        }

        ligneAppels = new ArrayList<>();
        System.out.println("Debut  CREATION DES LigneAppel ");
        for (int j = 1; j < 6; j++) {
            System.out.println("tour ligne - " + j);
            LigneAppel ligneAppel = new LigneAppel();
            ligneAppel.setDeleted(false);
            ligneAppel.setAppelOffre(appelOffre);
            Materiel materiel = new Materiel();
            materiel.setId(Long.valueOf(j));
            ligneAppel.setMateriel(materiel);
            ligneAppel.setPrixUnitaire(1250 * j);
            ligneAppel.setQuantite(j);
            ligneAppels.add(ligneAppel);
        }

        appelOffreForm.setAppelOffre(appelOffre);
        appelOffreForm.setCautions(cautions);
        appelOffreForm.setLigneAppels(ligneAppels);

        return appelOffreForm;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteAction(final AppelOffre appelOffre, final ModelMap model) {
        AppelOffre appelOffreToDelete = appelOffreService.findOne(appelOffre.getId());
        System.out.println("deleteAction of a appelOffre =" + appelOffreToDelete.getId() + " -intitulé=" + appelOffreToDelete.getIntitule() + " deleted ?= " + appelOffreToDelete.isDeleted());
        System.out.println("deletion beginning deleted = " + appelOffreToDelete.isDeleted());
        if (appelOffreToDelete.isDeleted() == true) {
            System.out.println("(suppose to be true) deleted= " + appelOffreToDelete.isDeleted());
            appelOffreToDelete.setDeleted(false);
            appelOffreService.disableEntity(appelOffreToDelete);
//            System.out.println("(suppose to be false) after calling service  deleted= " + appelOffreToDelete.isDeleted());
        } else {
            System.out.println("(suppose to be false) deleted= " + appelOffreToDelete.isDeleted());
            appelOffreToDelete.setDeleted(true);
            appelOffreService.disableEntity(appelOffreToDelete);
//            System.out.println("(suppose to be true) after calling service deleted= " + appelOffreToDelete.isDeleted());
        }

        System.out.println("deletion successfully ended deleted = " + appelOffreToDelete.isDeleted());
        return "redirect:/appeloffre/";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateAction(final ModelMap model, @RequestParam(value="fichiers", required=false) MultipartFile[] files,
            @Valid final AppelOffreForm appelOffreForm,
            final BindingResult result,
            final RedirectAttributes redirectAttributes) {
        System.out.println("enter");

        if (result.hasErrors() || (files != null && files[0].isEmpty())) {
            System.out.println("nul ou erreur and size = " + files.length);
            if (files != null) {
                model.addAttribute("nbFile", 1);
                if (files[0].isEmpty()) {
                    System.out.println("file error");
                    model.addAttribute("fileError", "Veuillez téléverser au moins un fichier!");
                }
            }
            model.addAttribute("appelOffreForm", appelOffreForm);
            return "appeloffre/edit";
        } else {
            System.out.println("non nul");
            redirectAttributes.addFlashAttribute("info", "alert.success.new");
            appelOffreService.update(appelOffreForm.getAppelOffre());
            if (files != null && !files[0].isEmpty()) {
                upload(files, appelOffreForm.getAppelOffre().getId());
            }
            return "redirect:/appeloffre/" + appelOffreForm.getAppelOffre().getId() + "/show";

        }
    }

    @ModelAttribute("todayDate")
    public Date getDate() {
        return new Date();
    }

    @ModelAttribute("filiales")
    public Map<Long, String> populateFilialesFields() {
        final Map<Long, String> results = new HashMap<>();
        final List<Filiale> filiales = filialeService.findAll();
        for (final Filiale filiale : filiales) {
            results.put(filiale.getId(), filiale.getNom());
        }
        return results;
    }

    @ModelAttribute("materiels")
    public Map<Long, String> populateMaterielFields() {
        final Map<Long, String> results = new HashMap<>();
        final List<Materiel> materiels = materielService.findAll();
        for (final Materiel materiel : materiels) {
            results.put(materiel.getId(), materiel.getNom());
        }

        return results;
    }

    @ModelAttribute("banques")
    public Map<Long, String> populateBanqueFields() {
        final Map<Long, String> results = new HashMap<>();
        final List<Banque> banques = banqueService.findAll();
        for (final Banque banque : banques) {
            results.put(banque.getId(), banque.getLibelle());
        }
        return results;
    }

    @ModelAttribute("commerciaux")
    public Map<Long, String> populateCommerciauxFileds() {
        final Map<Long, String> results = new HashMap<>();
        final List<Role> roles = roleService.retrieveCommerciaux();
        for (Role role : roles) {
            results.put(role.getId(), role.getUser().getNom());
        }
        return results;
    }

    @ModelAttribute("etats")
    public Map<Boolean, String> populateEtatFields() {
        final Map<Boolean, String> results = new HashMap<>();
        results.put(false, "Actif");
        results.put(true, "Inactif");
        return results;
    }

    @ModelAttribute("typeCautions")
    public Map<Long, String> populateTypeCautionFields() {
        final Map<Long, String> results = new HashMap<>();
        final List<TypeCaution> typeCautions = typeCautionService.findAll();
        for (final TypeCaution typeCaution : typeCautions) {
            results.put(typeCaution.getId(), typeCaution.getNom());
        }

        return results;
    }

    private void processData(AppelOffre appelOffre)
            throws IllegalStateException,
            IOException {
        MultipartFile file = appelOffre.getPieceJointe1Data();
        appelOffre.setPieceJointe1(file.getOriginalFilename());
        System.out.println("PJ = " + appelOffre.getPieceJointe1());
        processFileData(file, "documents");

        file = appelOffre.getPieceJointe2Data();
        appelOffre.setPieceJointe2(file.getOriginalFilename());
        System.out.println("PJ = " + appelOffre.getPieceJointe2());
        processFileData(file, "documents");

        file = appelOffre.getPieceJointe3Data();
        appelOffre.setPieceJointe3(file.getOriginalFilename());
        System.out.println("PJ = " + appelOffre.getPieceJointe3());
        processFileData(file, "documents");

        file = appelOffre.getPieceJointe4Data();
        appelOffre.setPieceJointe4(file.getOriginalFilename());
        System.out.println("PJ = " + appelOffre.getPieceJointe4());
        processFileData(file, "documents");

        file = appelOffre.getPieceJointe5Data();
        appelOffre.setPieceJointe5(file.getOriginalFilename());
        System.out.println("PJ = " + appelOffre.getPieceJointe5());
        processFileData(file, "documents");

        file = appelOffre.getPieceJointe6Data();
        appelOffre.setPieceJointe6(file.getOriginalFilename());
        System.out.println("PJ = " + appelOffre.getPieceJointe6());
        processFileData(file, "documents");

        file = appelOffre.getPieceJointe7Data();
        appelOffre.setPieceJointe7(file.getOriginalFilename());
        System.out.println("PJ = " + appelOffre.getPieceJointe7());
        processFileData(file, "documents");

        file = appelOffre.getPieceJointe8Data();
        appelOffre.setPieceJointe8(file.getOriginalFilename());
        System.out.println("PJ = " + appelOffre.getPieceJointe8());
        processFileData(file, "documents");
    }

    private String getSavedFileName(MultipartFile file, String uploadDir) {
        String webappRoot = servletContext.getRealPath("/");
        String relativeFolder = File.separator + "resources" + File.separator + "bootstrap" + File.separator
                + uploadDir + File.separator;
        String filename = webappRoot + relativeFolder + file.getOriginalFilename();
        System.out.println(filename);
        return filename;
    }

    private void processFileData(MultipartFile file, String uploadDir)
            throws IllegalStateException,
            IOException {

        String filename = getSavedFileName(file, uploadDir);
        file.transferTo(new File(filename));

    }

    void upload(MultipartFile[] files, Long idAppelOffre) {
        AppelOffre appelOffre = appelOffreService.findOne(idAppelOffre);
        for (MultipartFile file : files) {

            try {

                String saveName = getFileName(file, appelOffre);
                processFileData(file, SAVE_DIRECTORY, saveName);

                appelOffre.addFile(saveName);
                appelOffre = appelOffreService.updateFiles(appelOffre);

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void processFileData(MultipartFile file, String uploadDir, String nameOfFile)
            throws IllegalStateException,
            IOException {

        String filename = getSavedFileName(uploadDir, nameOfFile);
        file.transferTo(new File(filename));

    }

    private String getFileName(MultipartFile file, AppelOffre appelOffre) {
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        String saveName = appelOffre.getId() + "_" + new Date().getTime() + "." + ext;
        return saveName;

    }

    private String getSavedFileName(String uploadDir, String nameOfFile) {
        String webappRoot = servletContext.getRealPath("/");
        String relativeFolder = File.separator + "resources" + File.separator + "bootstrap" + File.separator
                + uploadDir + File.separator;
        String filename = webappRoot + relativeFolder + nameOfFile;

        System.out.println(filename);
        return filename;
    }

}
