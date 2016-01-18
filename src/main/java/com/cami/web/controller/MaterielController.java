/**
 *
 * @author fabrice
 */
package com.cami.web.controller;

import com.cami.persistence.model.Materiel;
import com.cami.persistence.model.TypeMateriel;
import com.cami.persistence.service.IMaterielService;
import com.cami.persistence.service.ITypeMaterielService;
import java.util.HashMap;
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
@RequestMapping(value = "/materiel")
public class MaterielController
{

    @Autowired
    private IMaterielService materielService;

    @Autowired
    private ITypeMaterielService typeMaterielService;

    // API
    // read - one
    @RequestMapping(value = "/{id}/show", method = RequestMethod.GET)
    public String ShowAction(@PathVariable("id") final Long id, final ModelMap model)
    {
        final Materiel materiel = materielService.findOne(id);
        model.addAttribute("materiel", materiel);
        return "materiel/show";
    }

    // read - all
    /**
     *
     * @param query
     * @param webRequest
     * @param page
     * @param size
     * @param model
     * <p>
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String indexAction(final ModelMap model, final WebRequest webRequest)
    {

        final Long typeMaterielId = (webRequest.getParameter("querytype") != null && !webRequest.getParameter("querytype").equals(""))
                ? Long.valueOf(webRequest.getParameter("querytype"))
                : -1;
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
        System.out.println("deletd=" + deleted);

        final Page<Materiel> resultPage = materielService.findPaginated(typeMaterielId, code, nom, deleted, page, size);
        final Materiel materiel = new Materiel();
        materiel.setCode(code);
        materiel.setNom(nom);
        final TypeMateriel typeMateriel = new TypeMateriel();
        typeMateriel.setId(typeMaterielId);
        materiel.setTypeMateriel(typeMateriel);

        model.addAttribute("materiel", materiel);
        model.addAttribute("page", page);
        model.addAttribute("Totalpage", resultPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("materiels", resultPage.getContent());
        return "materiel/index";
    }

    // write
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newAction(final ModelMap model)
    {
        model.addAttribute("materiel", new Materiel());
        return "materiel/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createAction(@Valid final Materiel materiel,
            final BindingResult result, final ModelMap model,
            final RedirectAttributes redirectAttributes)
    {

        if (result.hasErrors()) {
            model.addAttribute("error", "error");
            model.addAttribute("Materiel", materiel);
            return "materiel/new";
        }
        else {
            redirectAttributes.addFlashAttribute("info", "alert.success.new");
            materielService.create(materiel);
            return "redirect:/materiel/" + materiel.getId() + "/show";
        }

    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchAction(@Valid final Materiel materiel, final BindingResult result, final ModelMap model)
    {

        return "redirect:/materiel?query=" + materiel.getNom() + "&page=1&size=2";

    }

    @RequestMapping(value = "{id}/edit", method = RequestMethod.GET)
    public String editAction(@PathVariable("id") final Long id, final ModelMap model)
    {
        final Materiel materiel = materielService.findOne(id);
        model.addAttribute("materiel", materiel);
        return "materiel/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateAction(@Valid final Materiel materiel, final BindingResult result,
            final ModelMap model, final RedirectAttributes redirectAttributes)
    {
        System.out.println("in materiel controller");
        if (result.hasErrors()) {
            System.out.println("in materiel controller: Error occured");
            model.addAttribute("error", "error");
            model.addAttribute("materiel", materiel);
            return "materiel/edit";
        }
        else {
            System.out.println("in materiel controller: no error");
            redirectAttributes.addFlashAttribute("info", "alert.success.new");
            materielService.update(materiel);
            System.out.println("in materiel controller update method launched");
            return "redirect:/materiel/" + materiel.getId() + "/show";
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteAction(final Materiel materiel, final ModelMap model)
    {
//        materielService.deleteById(materiel.getId());
        Materiel toDelete = materielService.findOne(materiel.getId());
        materielService.disableEntity(toDelete);
        return "redirect:/materiel/";
    }

    @ModelAttribute("typeMateriels")
    public Map<Long, String> populateTypeMaterielFields()
    {
        final Map<Long, String> results = new HashMap<>();
        final List<TypeMateriel> typeMateriels = typeMaterielService.findAll();
        for (final TypeMateriel typeMateriel : typeMateriels) {
            results.put(typeMateriel.getId(), typeMateriel.getNom());
        }

        return results;
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
