package com.cami.web.controller;

import com.cami.persistence.model.Filiale;
import com.cami.persistence.service.IFilialeService;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
@RequestMapping(value = "/filiale")
public class FilialeController
{

    @Autowired
    private IFilialeService filialeService;

    // API
    // read - one
    @RequestMapping(value = "/{id}/show", method = RequestMethod.GET)
    public String ShowAction(@PathVariable("id") final Long id, final ModelMap model)
    {
        System.out.println("filiale");
        final Filiale filiale = filialeService.findOne(id);
        model.addAttribute("filiale", filiale);
        return "filiale/show";
    }

    // read - all
    /**
     *
     * @param model
     * @param webRequest
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String indexAction(final ModelMap model, final WebRequest webRequest)
    {
        final String agence = webRequest.getParameter("queryagence") != null ? webRequest.getParameter("queryagence") : "";
        final String code = webRequest.getParameter("querycode") != null ? webRequest.getParameter("querycode") : "";
        final String nom = webRequest.getParameter("querynom") != null ? webRequest.getParameter("querynom") : "";
        final Integer page = webRequest.getParameter("page") != null ? Integer.valueOf(webRequest.getParameter("page")) : 0;
        final Integer size = webRequest.getParameter("size") != null ? Integer.valueOf(webRequest.getParameter("size")) : 5;
        boolean deleted = false;
        if (webRequest.getParameter("querydeleted") != null) {
            deleted = webRequest.getParameter("querydeleted").equals("true");
        }

        System.out.println("querynom = " + nom);
        System.out.println("querycode = " + code);

        final Page<Filiale> resultPage = filialeService.findPaginated(agence, code, nom, deleted, page, size);

        final Filiale filiale = new Filiale();
        filiale.setCode(code);
        filiale.setAgence(agence);
        filiale.setNom(nom);
        model.addAttribute("filiale", filiale);
        model.addAttribute("page", page);
        model.addAttribute("Totalpage", resultPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("filiales", resultPage.getContent());
        return "filiale/index";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newAction(final ModelMap model)
    {
        final Filiale filiale = new Filiale();
        model.addAttribute("filiale", filiale);
        return "filiale/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createAction(@Valid final Filiale filiale,
            final BindingResult result, final ModelMap model,
            final RedirectAttributes redirectAttributes)
    {

        if (result.hasErrors()) {
            model.addAttribute("error", "error");
            model.addAttribute("filiale", filiale);
            return "filiale/new";
        }
        else {
            redirectAttributes.addFlashAttribute("info", "alert.success.new");
            filialeService.create(filiale);
            return "redirect:/filiale/" + filiale.getId() + "/show";
        }

    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteAction(final Filiale filiale, final ModelMap model)
    {
//        filialeService.deleteById(filiale.getId());
        Filiale toDelete = filialeService.findOne(filiale.getId());
        filialeService.disableEntity(toDelete);
        return "redirect:/filiale/";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchAction(@Valid final Filiale filiale, final BindingResult result, final ModelMap model)
    {
        return "redirect:/filiale?query=" + filiale.getNom() + "&page=1&size=2";
    }

    @RequestMapping(value = "{id}/edit", method = RequestMethod.GET)
    public String editAction(@PathVariable("id") final Long id, final ModelMap model)
    {
        final Filiale filiale = filialeService.findOne(id);
        model.addAttribute("filiale", filiale);
        return "filiale/edit";
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String updateAction(@Valid final Filiale filiale,
            @PathVariable("id") final Long id, final BindingResult result,
            final ModelMap model, final RedirectAttributes redirectAttributes)
    {
        System.out.println("launchind update of controller");
        if (result.hasErrors()) {
            System.out.println("launching update of controller with errors ");
            System.out.println("erreur= " + result.getAllErrors());
            model.addAttribute("error", "error");
            model.addAttribute("filiale", filiale);
            return "filiale/edit";
        }
        else {
            redirectAttributes.addFlashAttribute("info", "alert.success.new");
            System.out.println("pas d'erreurs launching update method");
            filialeService.update(filiale);
            return "redirect:/filiale/" + filiale.getId() + "/show";
        }
    }

    @ModelAttribute("filiales")

    public Map<String, String> populateFiliale()
    {
        final Map<String, String> filiales = new LinkedHashMap<>();
        filiales.put("Vehicules Lourds", "Vehicules Lourds");
        filiales.put("Vehicules Legers", "Vehicules Legers");
        filiales.put("Motos", "Motos");
        filiales.put("Hard Board", "Hard Board");
        filiales.put("Pneumatiques", "Pneumatiques");
        filiales.put("Generateurs", "Generateurs");
        return filiales;
    }

    @ModelAttribute("etats")
    public Map<Boolean, String> populateEtatFields()
    {
        final Map<Boolean, String> results = new HashMap<>();
        results.put(false, "Actif");
        results.put(true, "Inactif");
        return results;
    }
}
