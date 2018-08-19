package com.cami.web.controller;

import com.cami.persistence.model.AppelOffre;
import com.cami.persistence.model.Banque;
import com.cami.persistence.model.Caution;
import com.cami.persistence.model.TypeCaution;
import com.cami.persistence.service.IAppelOffreService;
import com.cami.persistence.service.IBanqueService;
import com.cami.persistence.service.ICautionService;
import com.cami.persistence.service.ITypeCautionService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    @RequestMapping(method = RequestMethod.GET)
    public String indexAction(final ModelMap model, final WebRequest webRequest) {
        // The next line is used to generate the final report
        final List<AppelOffre> appelOffres = cautionService.getThemComplete();

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
                : 50;
        final Page<Caution> resultPage = cautionService.filter(banqueId, typeCautionId, debutPeriode, finPeriode, page, size);

        final Caution caution = new Caution();
        caution.setBanque(new Banque(banqueId));
        caution.setTypeCaution(new TypeCaution(typeCautionId));
        model.addAttribute("cautionsReport", appelOffres); //for the report
        model.addAttribute("caution", caution);
        model.addAttribute("querydebutperiode", debutPeriodeEcheance.equals("31/12/1975") ? "" : debutPeriodeEcheance);
        model.addAttribute("queryfinperiode", finPeriodeEcheance.equals("31/12/9999") ? "" : finPeriodeEcheance);
        model.addAttribute("page", page);
        model.addAttribute("Totalpage", resultPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("cautions", resultPage.getContent());
        model.addAttribute("cautions", resultPage.getContent());
        return "caution/index";
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
