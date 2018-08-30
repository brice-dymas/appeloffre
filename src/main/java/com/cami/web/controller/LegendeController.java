package com.cami.web.controller;

import com.cami.persistence.model.Legende;
import com.cami.persistence.service.ILegendeService;
import java.util.HashMap;
import java.util.Map;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/legende")
public class LegendeController {

    @Autowired
    private ILegendeService legendeService;

    // API
    // read - one
    @RequestMapping(value = "/{id}/show", method = RequestMethod.GET)
    public String ShowAction(@PathVariable("id") final Long id, final ModelMap model) {
        System.out.println("legende");
        final Legende legende = legendeService.findOne(id);
        model.addAttribute("legende", legende);
        return "legende/show";
    }

    // read - all
    /**
     *
     * @param model
     * @param webRequest
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String indexAction(final ModelMap model, final WebRequest webRequest) {
        final String nom = webRequest.getParameter("querynom") != null ? webRequest.getParameter("querynom").trim() : "";
        final Integer page = webRequest.getParameter("page") != null ? Integer.valueOf(webRequest.getParameter("page")) : 0;
        final Integer size = webRequest.getParameter("size") != null ? Integer.valueOf(webRequest.getParameter("size")) : 20;
        boolean deleted = false;
        if (webRequest.getParameter("querydeleted") != null) {
            deleted = webRequest.getParameter("querydeleted").equals("true");
        }

        final Page<Legende> resultPage = legendeService.findPaginated(nom, deleted, page, size);

        final Legende legende = new Legende();
        legende.setLibelle(nom);
        model.addAttribute("legende", legende);
        model.addAttribute("page", page);
        model.addAttribute("Totalpage", resultPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("legendes", resultPage.getContent());
        return "legende/index";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newAction(final ModelMap model) {
        final Legende legende = new Legende();
        model.addAttribute("legende", legende);
        return "legende/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createAction(@Valid final Legende legende,
            final BindingResult result, final ModelMap model,
            final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("error", "error");
            model.addAttribute("legende", legende);
            return "legende/new";
        } else {
            redirectAttributes.addFlashAttribute("info", "alert.success.new");
            legendeService.create(legende);
            return "redirect:/legende/" + legende.getId() + "/show";
        }

    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteAction(final Legende legende, final ModelMap model) {
//        legendeService.deleteById(legende.getId());
        Legende toDelete = legendeService.findOne(legende.getId());
        legendeService.disableEntity(toDelete);
        return "redirect:/legende/";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchAction(@Valid final Legende legende, final BindingResult result, final ModelMap model) {
        return "redirect:/legende?query=" + legende.getLibelle() + "&page=1&size=20";
    }

    @RequestMapping(value = "{id}/edit", method = RequestMethod.GET)
    public String editAction(@PathVariable("id") final Long id, final ModelMap model) {
        final Legende legende = legendeService.findOne(id);
        model.addAttribute("legende", legende);
        return "legende/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateAction(final ModelMap model,
            @Valid final Legende legende, final BindingResult result,
            final RedirectAttributes redirectAttributes) {
        System.out.println("launchind update of controller");
        if (result.hasErrors()) {
            System.out.println("launching update of controller with errors ");
            System.out.println("erreur= " + result.getAllErrors());
            model.addAttribute("error", "error");
            model.addAttribute("legende", legende);
            return "legende/edit";
        } else {
            redirectAttributes.addFlashAttribute("info", "alert.success.new");
            System.out.println("pas d'erreurs launching update method");
            legendeService.update(legende);
            return "redirect:/legende/" + legende.getId() + "/show";
        }
    }

    @ModelAttribute("etats")
    public Map<Boolean, String> populateEtatFields() {
        final Map<Boolean, String> results = new HashMap<>();
        results.put(false, "Actif");
        results.put(true, "Inactif");
        return results;
    }
}
