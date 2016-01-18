package com.cami.web.controller;

import com.cami.persistence.model.TypeCaution;
import com.cami.persistence.service.ITypeCautionService;
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
@RequestMapping(value = "/typecaution")
public class TypeCautionController
{

    @Autowired
    private ITypeCautionService typeCautionService;

    // API
    // read - one
    @RequestMapping(value = "/{id}/show", method = RequestMethod.GET)
    public String ShowAction(@PathVariable("id") final Long id, final ModelMap model)
    {
        final TypeCaution typeCaution = typeCautionService.findOne(id);
        model.addAttribute("typeCaution", typeCaution);
        return "typecaution/show";
    }

    // read - all
    /**
     *
     * @param model
     * @param webRequest
     * <p>
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String indexAction(final ModelMap model, final WebRequest webRequest)
    {

        final String code = webRequest.getParameter("querycode") != null
                ? webRequest.getParameter("querycode")
                : "";
        final String nom = webRequest.getParameter("querynom") != null
                ? webRequest.getParameter("querynom")
                : "";
        final Integer page = webRequest.getParameter("page") != null
                ? Integer.valueOf(webRequest.getParameter("page"))
                : 0;
        final Integer size = webRequest.getParameter("size") != null
                ? Integer.valueOf(webRequest.getParameter("size"))
                : 5;
        boolean deleted = false;
        if (webRequest.getParameter("querydeleted") != null) {
            deleted = webRequest.getParameter("querydeleted").equals("true");
        }

        System.out.println("querynom = " + nom);
        System.out.println("querycode = " + code);

        final Page<TypeCaution> resultPage = typeCautionService.findPaginated(code, nom, deleted, page, size);

        final TypeCaution typeCaution = new TypeCaution();
        typeCaution.setCode(code);
        typeCaution.setNom(nom);
        model.addAttribute("typeCaution", typeCaution);
        model.addAttribute("page", page);
        model.addAttribute("Totalpage", resultPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("typeCautions", resultPage.getContent());
        return "typecaution/index";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newAction(final ModelMap model)
    {
        final TypeCaution typeCaution = new TypeCaution();
        model.addAttribute("typeCaution", typeCaution);
        return "typecaution/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createAction(@Valid final TypeCaution typeCaution,
            final BindingResult result, final ModelMap model,
            final RedirectAttributes redirectAttributes)
    {

        if (result.hasErrors()) {
            model.addAttribute("error", "error");
            model.addAttribute("typeCaution", typeCaution);
            return "typecaution/new";
        }
        else {
            redirectAttributes.addFlashAttribute("info", "alert.success.new");
            typeCautionService.create(typeCaution);
            return "redirect:/typecaution/" + typeCaution.getId() + "/show";
        }

    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteAction(final TypeCaution typeCaution, final ModelMap model)
    {
//        typeCautionService.deleteById(typeCaution.getId());
        TypeCaution toDelete = typeCautionService.findOne(typeCaution.getId());
        typeCautionService.disableEntity(toDelete);
        return "redirect:/typecaution/";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchAction(@Valid final TypeCaution typeCaution, final BindingResult result, final ModelMap model)
    {

        return "redirect:/typecaution?query=" + typeCaution.getNom() + "&page=1&size=2";

    }

    @RequestMapping(value = "{id}/edit", method = RequestMethod.GET)
    public String editAction(@PathVariable("id") final Long id, final ModelMap model)
    {
        final TypeCaution typeCaution = typeCautionService.findOne(id);
        model.addAttribute("typeCaution", typeCaution);
        return "typecaution/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
//    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String updateAction(@Valid final TypeCaution typeCaution,
            final BindingResult result, final ModelMap model,
            final RedirectAttributes redirectAttributes)
    {
        System.out.println("in type caution controller ");
        if (result.hasErrors()) {
            System.out.println("errors detected ");
            model.addAttribute("error", "error");
//            model.addAttribute("typeCaution", typeCaution);
            return "typecaution/edit";
        }
        else {
            System.out.println("no error detected");
            redirectAttributes.addFlashAttribute("info", "alert.success.new");
            System.out.println("launching update method from controller ..");
            typeCautionService.update(typeCaution);
            return "redirect:/typecaution/" + typeCaution.getId() + "/show";
        }
    }

    @ModelAttribute("typeCautions")

    public Map<String, String> populateTypeCaution()
    {
        final Map<String, String> typeCautions = new LinkedHashMap<>();
        typeCautions.put("Vehicules Lourds", "Vehicules Lourds");
        typeCautions.put("Vehicules Legers", "Vehicules Legers");
        typeCautions.put("Motos", "Motos");
        typeCautions.put("Hard Board", "Hard Board");
        typeCautions.put("Pneumatiques", "Pneumatiques");
        typeCautions.put("Generateurs", "Generateurs");
        return typeCautions;
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
