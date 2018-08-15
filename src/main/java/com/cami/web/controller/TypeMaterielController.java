package com.cami.web.controller;

import com.cami.persistence.model.TypeMateriel;
import com.cami.persistence.service.ITypeMaterielService;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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
@RequestMapping(value = "/typemateriel")
public class TypeMaterielController
{

    @Autowired
    private ITypeMaterielService typeMaterielService;

    // API
    // read - one
    @RequestMapping(value = "/{id}/show", method = RequestMethod.GET)
    public String ShowAction(@PathVariable("id") final Long id, final ModelMap model)
    {
        final TypeMateriel typeMateriel = typeMaterielService.findOne(id);

        model.addAttribute("typeMateriel", typeMateriel);
        return "typemateriel/show";
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

        final String code = webRequest.getParameter("querycode") != null ? webRequest.getParameter("querycode") : "";
        final String nom = webRequest.getParameter("querynom") != null ? webRequest.getParameter("querynom") : "";
        final Integer page = webRequest.getParameter("page") != null ? Integer.valueOf(webRequest.getParameter("page")) : 0;
        final Integer size = webRequest.getParameter("size") != null ? Integer.valueOf(webRequest.getParameter("size")) : 55;
        boolean deleted = false;
        if (webRequest.getParameter("querydeleted") != null) {
            deleted = webRequest.getParameter("querydeleted").equals("true");
        }

        System.out.println("querynom = " + nom);
        System.out.println("querycode = " + code);

        final Page<TypeMateriel> resultPage = typeMaterielService.findPaginated(code, nom, deleted, page, size);
        final List<TypeMateriel> mesTypeMateriels = typeMaterielService
                .findAll();

        final TypeMateriel typeMateriel = new TypeMateriel();
        typeMateriel.setCode(code);
        typeMateriel.setNom(nom);
        model.addAttribute("mesTypeMateriels", mesTypeMateriels);
        model.addAttribute("typeMateriel", typeMateriel);
        model.addAttribute("page", page);
        model.addAttribute("Totalpage", resultPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("typeMateriels", resultPage.getContent());
        return "typemateriel/index";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newAction(final ModelMap model)
    {
        final TypeMateriel typeMateriel = new TypeMateriel();
        model.addAttribute("typeMateriel", typeMateriel);
        return "typemateriel/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createAction(@Valid final TypeMateriel typeMateriel,
            final BindingResult result, final ModelMap model,
            final RedirectAttributes redirectAttributes)
    {
        System.out.println("nous somme dans le controlleur et tm= " + typeMateriel.getCode() + "" + typeMateriel.getNom());
        if (result.hasErrors()) {
            System.out.println("il ya ereur");
            model.addAttribute("error", "error");
            model.addAttribute("typeMateriel", typeMateriel);
            return "typemateriel/new";
        }
        else {
            redirectAttributes.addFlashAttribute("info", "alert.success.new");
            typeMaterielService.create(typeMateriel);
            return "redirect:/typemateriel/" + typeMateriel.getId() + "/show";
        }

    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteAction(final TypeMateriel typeMateriel, final ModelMap model)
    {
//        typeMaterielService.deleteById(typeMateriel.getId());
        TypeMateriel toDelete = typeMaterielService.findOne(typeMateriel.getId());
        typeMaterielService.disableEntity(toDelete);
        return "redirect:/typemateriel/";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchAction(@Valid final TypeMateriel typeMateriel, final BindingResult result, final ModelMap model)
    {

        return "redirect:/typemateriel?query=" + typeMateriel.getNom() + "&page=1&size=2";

    }

    @RequestMapping(value = "{id}/edit", method = RequestMethod.GET)
    public String editAction(@PathVariable("id") final Long id, final ModelMap model)
    {
        final TypeMateriel typeMateriel = typeMaterielService.findOne(id);
        model.addAttribute("typeMateriel", typeMateriel);
        return "typemateriel/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateAction(final ModelMap model,
            @Valid final TypeMateriel typeMateriel, final BindingResult result,
            final RedirectAttributes redirectAttributes)
    {
        System.out.println("here we are in the controller update method");
        if (result.hasErrors()) {
            model.addAttribute("error", "error");
            return "typemateriel/edit";
        }
        else {
            redirectAttributes.addFlashAttribute("info", "alert.success.new");
            typeMaterielService.update(typeMateriel);
            return "redirect:/typemateriel/" + typeMateriel.getId() + "/show";
        }
    }

    @ModelAttribute("typeMateriels")
    public Map<String, String> populateTypeMateriel()
    {
        final Map<String, String> typeMateriels = new LinkedHashMap<>();
        typeMateriels.put("Vehicules Lourds", "Vehicules Lourds");
        typeMateriels.put("Vehicules Legers", "Vehicules Legers");
        typeMateriels.put("Motos", "Motos");
        typeMateriels.put("Hard Board", "Hard Board");
        typeMateriels.put("Pneumatiques", "Pneumatiques");
        typeMateriels.put("Generateurs", "Generateurs");
        return typeMateriels;
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
