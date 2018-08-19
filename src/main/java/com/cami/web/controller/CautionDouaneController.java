package com.cami.web.controller;

import com.cami.persistence.model.Banque;
import com.cami.persistence.model.CautionDouane;
import com.cami.persistence.service.IBanqueService;
import com.cami.persistence.service.ICautionDouaneService;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cautiondouane")
public class CautionDouaneController {

    @Autowired
    ICautionDouaneService cautionService;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    IBanqueService banqueService;

    public String getTrueDate(final Date date) {
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

    @RequestMapping(value = "/stats", method = RequestMethod.GET)
    public String statAction(final ModelMap model, final WebRequest webRequest) {
        final Integer year = webRequest.getParameter("year") != null ? Integer.valueOf(webRequest.getParameter("year")) : 2018;

        List<Object[]> datas = cautionService.totalCautionParBanqueParMois(year);
        model.addAttribute("results", datas);
        model.addAttribute("year", year);
        System.out.println("HomeController");
        return "cautiondouane/stat";
    }

    @RequestMapping(value = "/{id}/show", method = RequestMethod.GET)
    public String ShowAction(@PathVariable("id") final Long id,
            final ModelMap model) {
        final CautionDouane caution = cautionService.findOne(id);
        model.addAttribute("cautiondouane", caution);
        return "cautiondouane/show";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchAction(@Valid final CautionDouane caution,
            final BindingResult result, final ModelMap model) {
        return "redirect:/caution?query=" + caution.getBanque()
                + "&page=1&size=2";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String indexAction(final ModelMap model, final WebRequest webRequest) {

        Long banqueId = 0L;
        int montant = 0;
        try {
            banqueId = Long.valueOf(webRequest.getParameter("querybanque"));
        } catch (NumberFormatException numberFormatException) {
            banqueId = -1L;
        }

        try {
            montant = Integer.valueOf(webRequest.getParameter("querymontant"));
        } catch (NumberFormatException numberFormatException) {
            montant = -1;
        }

//        final Long banqueId =  (webRequest.getParameter("querybanque") != null
//                ? Long.valueOf(webRequest.getParameter("querybanque"))
//                : -1);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

        final String debutPeriodeEcheance = (webRequest.getParameter("querydebutperiode") != null)
                ? webRequest.getParameter("querydebutperiode")
                : "31/12/1975";
        final String finPeriodeEcheance = webRequest.getParameter("queryfinperiode") != null
                ? webRequest.getParameter("queryfinperiode")
                : "31/12/9999";
        Date debutPeriode = new Date();
        Date finPeriode = new Date();
        try {
            debutPeriode = dateFormatter.parse(debutPeriodeEcheance);
        } catch (ParseException ex) {
            try {
                debutPeriode = dateFormatter.parse("31/12/1975");
            } catch (ParseException ex1) {
                Logger.getLogger(CautionDouaneController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        try {
            finPeriode = dateFormatter.parse(finPeriodeEcheance);
        } catch (ParseException ex) {
            try {
                finPeriode = dateFormatter.parse("31/12/9999");
            } catch (ParseException ex1) {
                Logger.getLogger(CautionDouaneController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        final Integer page = webRequest.getParameter("page") != null
                ? Integer
                        .valueOf(webRequest.getParameter("page"))
                : 0;
        final Integer size = webRequest.getParameter("size") != null
                ? Integer
                        .valueOf(webRequest.getParameter("size"))
                : 5;
        final Page<CautionDouane> resultPage = cautionService.filter(banqueId, montant, debutPeriode, finPeriode, page, size);

        final CautionDouane caution = new CautionDouane();
        caution.setBanque(new Banque(banqueId));
        caution.setMontant(montant);
        model.addAttribute("caution", caution);
        model.addAttribute("querybanque", banqueId);
        model.addAttribute("querymontant", montant);
        model.addAttribute("querydebutperiode", debutPeriodeEcheance.equals("31/12/1975") ? "" : debutPeriodeEcheance);
        model.addAttribute("queryfinperiode", finPeriodeEcheance.equals("31/12/9999") ? "" : finPeriodeEcheance);
        model.addAttribute("page", page);
        model.addAttribute("Totalpage", resultPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("cautiondouanes", resultPage.getContent());
        return "cautiondouane/index";
    }

    @ModelAttribute("todayDate")
    public Date getTodayDate() {
        return new Date();
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

    // write
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newAction(final ModelMap model) {
        model.addAttribute("cautiondouane", new CautionDouane());
        return "cautiondouane/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createAction(@ModelAttribute("cautiondouane") @Valid final CautionDouane cautiondouane,
            final BindingResult result, final ModelMap model,
            final RedirectAttributes redirectAttributes) {
        System.out.println("Dans createAction DE CAUTION DOUANE CONTROLLER ");

        if (result.hasErrors()) {
            System.out.println("ERREUR DETECTEE DANS createAction");
            model.addAttribute("error", "error");
            model.addAttribute("cautiondouane", cautiondouane);
            return "cautiondouane/new";
        } else {
            System.out.println("AUCUNE ERREUR DETECTEE DANS createAction");
            redirectAttributes.addFlashAttribute("info", "alert.success.new");
            cautionService.create(cautiondouane);
            return "redirect:/cautiondouane/" + cautiondouane.getId() + "/show";
        }

    }

    @RequestMapping(value = "{id}/edit", method = RequestMethod.GET)
    public String editAction(@PathVariable("id") final Long id, final ModelMap model) {
        final CautionDouane cautiondouane = cautionService.findOne(id);
        model.addAttribute("cautiondouane", cautiondouane);
        return "cautiondouane/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateAction(@ModelAttribute("cautiondouane") @Valid final CautionDouane cautiondouane, final BindingResult result,
            final ModelMap model, final RedirectAttributes redirectAttributes) {
        System.out.println("in cautiondouane controller");
        if (result.hasErrors()) {
            System.out.println("in cautiondouane controller: Error occured");
            model.addAttribute("error", "error");
            model.addAttribute("cautiondouane", cautiondouane);
            return "cautiondouane/edit";
        } else {
            System.out.println("in cautiondouane controller: no error");
            redirectAttributes.addFlashAttribute("info", "alert.success.new");
            cautionService.update(cautiondouane);
            System.out.println("in cautiondouane controller update method launched");
            return "redirect:/cautiondouane/" + cautiondouane.getId() + "/show";
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteAction(final CautionDouane cautiondouane, final ModelMap model) {
//        cautiondouaneService.deleteById(cautiondouane.getId());
        CautionDouane toDelete = cautionService.findOne(cautiondouane.getId());
        cautionService.disableEntity(toDelete);
        return "redirect:/cautiondouane/";
    }

    private void processData(CautionDouane cautionDouane)
            throws IllegalStateException,
            IOException {
        MultipartFile file = cautionDouane.getPieceJointe1Data();
        cautionDouane.setPieceJointe1(file.getOriginalFilename());
        System.out.println("PJ = " + cautionDouane.getPieceJointe1());
        processFileData(file, "documents");

        file = cautionDouane.getPieceJointe2Data();
        cautionDouane.setPieceJointe2(file.getOriginalFilename());
        System.out.println("PJ = " + cautionDouane.getPieceJointe2());
        processFileData(file, "documents");

        file = cautionDouane.getPieceJointe3Data();
        cautionDouane.setPieceJointe3(file.getOriginalFilename());
        System.out.println("PJ = " + cautionDouane.getPieceJointe3());
        processFileData(file, "documents");

        file = cautionDouane.getPieceJointe4Data();
        cautionDouane.setPieceJointe4(file.getOriginalFilename());
        System.out.println("PJ = " + cautionDouane.getPieceJointe4());
        processFileData(file, "documents");

        file = cautionDouane.getPieceJointe5Data();
        cautionDouane.setPieceJointe5(file.getOriginalFilename());
        System.out.println("PJ = " + cautionDouane.getPieceJointe5());
        processFileData(file, "documents");

        file = cautionDouane.getPieceJointe6Data();
        cautionDouane.setPieceJointe6(file.getOriginalFilename());
        System.out.println("PJ = " + cautionDouane.getPieceJointe6());
        processFileData(file, "documents");

        file = cautionDouane.getPieceJointe7Data();
        cautionDouane.setPieceJointe7(file.getOriginalFilename());
        System.out.println("PJ = " + cautionDouane.getPieceJointe7());
        processFileData(file, "documents");

        file = cautionDouane.getPieceJointe8Data();
        cautionDouane.setPieceJointe8(file.getOriginalFilename());
        System.out.println("PJ = " + cautionDouane.getPieceJointe8());
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
}
