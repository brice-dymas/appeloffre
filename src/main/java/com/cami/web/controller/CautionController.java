package com.cami.web.controller;

import com.cami.persistence.model.AppelOffre;
import com.cami.persistence.model.Banque;
import com.cami.persistence.model.Caution;
import com.cami.persistence.model.Legende;
import com.cami.persistence.model.Role;
import com.cami.persistence.model.TypeCaution;
import com.cami.persistence.service.IAppelOffreService;
import com.cami.persistence.service.IBanqueService;
import com.cami.persistence.service.ICautionService;
import com.cami.persistence.service.ILegendeService;
import com.cami.persistence.service.IRoleService;
import com.cami.persistence.service.ITypeCautionService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/caution")
public class CautionController {

    @Autowired
    ICautionService cautionService;

    @Autowired
    IBanqueService banqueService;

    @Autowired
    ITypeCautionService typeCautionService;

    @Autowired
    IRoleService roleService;

    @Autowired
    ILegendeService legendeService;

    @Autowired
    IAppelOffreService appelOffreService;

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
        return "caution/stat";
    }

    @RequestMapping(value = "/{id}/show", method = RequestMethod.GET)
    public String ShowAction(@PathVariable("id") final Long id,
            final ModelMap model) {
        final Caution caution = cautionService.findOne(id);
        final AppelOffre appelOffre = appelOffreService.findOne(caution
                .getAppelOffre().getId());
        caution.setAppelOffre(appelOffre);
        model.addAttribute("caution", caution);
        return "caution/show";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchAction(@Valid final Caution caution,
            final BindingResult result, final ModelMap model) {
        return "redirect:/caution?query=" + caution.getBanque()
                + "&page=1&size=2";
    }

    @RequestMapping(value = {"/print-cautions.xls", "/print-pdf", "/"}, method = RequestMethod.GET)
    public String indexAction(final ModelMap model, final WebRequest webRequest, HttpServletRequest request) {
        // The next line is used to generate the final report
        String pattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        Page<Caution> resultPage = null;
        

        Long banqueId = 0L;
        Long typeCautionId = 0L;
        try {
            banqueId = Long.valueOf(webRequest.getParameter("querybanque"));
        } catch (NumberFormatException numberFormatException) {
            banqueId = -1L;
        }

        try {
            typeCautionId = Long.valueOf(webRequest.getParameter("querytypecaution"));
        } catch (NumberFormatException numberFormatException) {
            typeCautionId = -1L;
        }

//        final Long banqueId =  (webRequest.getParameter("querybanque") != null
//                ? Long.valueOf(webRequest.getParameter("querybanque"))
//                : -1);
//        final Long typeCautionId = webRequest.getParameter("querytypecaution") != null
//                ? Long.valueOf(webRequest.getParameter("querytypecaution"))
//                : -1;
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
                Logger.getLogger(CautionController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        try {
            finPeriode = dateFormatter.parse(finPeriodeEcheance);
        } catch (ParseException ex) {
            try {
                finPeriode = dateFormatter.parse("31/12/9999");
            } catch (ParseException ex1) {
                Logger.getLogger(CautionController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        final Integer page = webRequest.getParameter("page") != null
                ? Integer
                        .valueOf(webRequest.getParameter("page"))
                : 0;
        final Integer size = webRequest.getParameter("size") != null
                ? Integer
                        .valueOf(webRequest.getParameter("size"))
                : 20;
        final String numero = (webRequest.getParameter("querynumero") != null && !webRequest.getParameter("querynumero").isEmpty())
                ? webRequest.getParameter("querynumero").trim() : "";
        final String maitreDouvrage = (webRequest.getParameter("querymaitredouvrage") != null && !webRequest.getParameter("querymaitredouvrage").isEmpty())
                ? webRequest.getParameter("querymaitredouvrage").trim() : "";
        System.out.println(pattern);
        if(pattern.contains("print-pdf") || pattern.contains("print-cautions.xls") ){
            System.out.println("print");
          resultPage = cautionService.filter(numero, maitreDouvrage, banqueId, typeCautionId, debutPeriode, finPeriode, 0, Integer.MAX_VALUE);
        }else{
          resultPage = cautionService.filter(numero, maitreDouvrage, banqueId, typeCautionId, debutPeriode, finPeriode, page, size);
        }
        
        System.out.println("page size "+resultPage.getSize());
        final Caution caution = new Caution();
        AppelOffre appelOffre = new AppelOffre();
        appelOffre.setMaitreDouvrage(maitreDouvrage);
        caution.setNumero(numero);
        caution.setAppelOffre(appelOffre);
        caution.setBanque(new Banque(banqueId));
        caution.setTypeCaution(new TypeCaution(typeCautionId));
        //model.addAttribute("cautionsReport", "cautionsReport"); //for the report
        model.addAttribute("caution", caution);
        model.addAttribute("querydebutperiode", debutPeriodeEcheance.equals("31/12/1975") ? "" : debutPeriodeEcheance);
        model.addAttribute("queryfinperiode", finPeriodeEcheance.equals("31/12/9999") ? "" : finPeriodeEcheance);
        model.addAttribute("querynumero", numero);
        model.addAttribute("querymaitredouvrage", maitreDouvrage);
        model.addAttribute("page", page);
        model.addAttribute("Totalpage", resultPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("cautions", resultPage.getContent());
        //model.addAttribute("cautions", resultPage.getContent());
        if(pattern.contains("print-pdf")){
          return "caution/print-pdf";
        }
        return "caution/index";
    }

    @RequestMapping(value = "{id}/edit", method = RequestMethod.GET)
    public String editAction(@PathVariable("id") final Long id, final ModelMap model) {
        final Caution caution = cautionService.findOne(id);
        model.addAttribute("caution", caution);
        return "caution/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateAction(final ModelMap model, @ModelAttribute("caution") @Valid final Caution caution,
            final BindingResult result, final RedirectAttributes redirectAttributes) {
        System.out.println("in caution controller");

        if (result.hasErrors()) {
            System.out.println("ERROR = " + result.getAllErrors());
            model.addAttribute("error", "error");
            model.addAttribute("caution", caution);
            return "caution/edit";
        } else {
            System.out.println("non nul");
            redirectAttributes.addFlashAttribute("info", "alert.success.new");
            cautionService.update(caution);
            return "redirect:/caution/" + caution.getId() + "/show";
        }
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

    @ModelAttribute("legendes")
    public Map<Long, String> populateLegendeFields() {
        final Map<Long, String> results = new HashMap<>();
        final List<Legende> legendes = legendeService.findAll();
        for (final Legende legende : legendes) {
            results.put(legende.getId(), legende.getLibelle());
        }
        return results;
    }

    @ModelAttribute("commercials")
    public Map<Long, String> populateCommerciauxFileds() {
        final Map<Long, String> results = new HashMap<>();
        final List<Role> roles = roleService.retrieveCommerciaux();
        for (Role role : roles) {
            results.put(role.getId(), role.getUser().getNom());
        }
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
}
