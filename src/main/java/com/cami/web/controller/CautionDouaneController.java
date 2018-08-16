package com.cami.web.controller;

import com.cami.persistence.model.Banque;
import com.cami.persistence.model.CautionDouane;
import com.cami.persistence.service.IBanqueService;
import com.cami.persistence.service.ICautionDouaneService;
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
@RequestMapping("/cautionDouane")
public class CautionDouaneController {

    @Autowired
    ICautionDouaneService cautionService;

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
        return "caution/stat";
    }

    @RequestMapping(value = "/{id}/show", method = RequestMethod.GET)
    public String ShowAction(@PathVariable("id") final Long id,
            final ModelMap model) {
        final CautionDouane caution = cautionService.findOne(id);
        model.addAttribute("caution", caution);
        return "caution/show";
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
//        final Long typeCautionDouaneId = webRequest.getParameter("querytypecaution") != null
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
        model.addAttribute("querymontant", montant);
        model.addAttribute("querydebutperiode", debutPeriodeEcheance.equals("31/12/1975") ? "" : debutPeriodeEcheance);
        model.addAttribute("queryfinperiode", finPeriodeEcheance.equals("31/12/9999") ? "" : finPeriodeEcheance);
        model.addAttribute("page", page);
        model.addAttribute("Totalpage", resultPage.getTotalPages());
        model.addAttribute("size", size);
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
}
