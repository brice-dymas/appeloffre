/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.web.controller;

import com.cami.persistence.model.Banque;
import com.cami.persistence.service.IBanqueService;
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

/**
 *
 * @author Brice GUEMKAM <briceguemkam@gmail.com>
 */
@Controller
@RequestMapping("/banque")
public class BanqueController
{

    @Autowired
    private IBanqueService banqueService;

    // API
    // read - one
    @RequestMapping(value = "/{id}/show", method = RequestMethod.GET)
    public String ShowAction(@PathVariable("id") final Long id, final ModelMap model)
    {
        final Banque banque = banqueService.findOne(id);

        model.addAttribute("banque", banque);
        return "banque/show";
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
        final String libelle = webRequest.getParameter("querynom") != null ? webRequest.getParameter("querynom") : "";
        final Integer page = webRequest.getParameter("page") != null ? Integer.valueOf(webRequest.getParameter("page")) : 0;
        final Integer size = webRequest.getParameter("size") != null ? Integer.valueOf(webRequest.getParameter("size")) : 55;
        boolean deleted = false;
        if (webRequest.getParameter("querydeleted") != null) {
            deleted = webRequest.getParameter("querydeleted").equals("true");
        }

        System.out.println("querynom = " + libelle);
        System.out.println("querycode = " + code);

        final Page<Banque> resultPage = banqueService.findPaginated(code, libelle, deleted, page, size);

        final Banque banque = new Banque();
        banque.setCode(code);
        banque.setLibelle(libelle);
        model.addAttribute("banque", banque);
        model.addAttribute("page", page);
        model.addAttribute("Totalpage", resultPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("banques", resultPage.getContent());
        return "banque/index";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newAction(final ModelMap model)
    {
        final Banque banque = new Banque();
        model.addAttribute("banque", banque);
        return "banque/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createAction(@Valid final Banque banque,
            final BindingResult result, final ModelMap model,
            final RedirectAttributes redirectAttributes)
    {
        System.out.println("nous somme dans le controlleur et tm= " + banque.getCode() + "" + banque.getLibelle());
        if (result.hasErrors()) {
            System.out.println("il ya ereur");
            model.addAttribute("error", "error");
            model.addAttribute("banque", banque);
            return "banque/new";
        }
        else {
            redirectAttributes.addFlashAttribute("info", "alert.success.new");
            banqueService.create(banque);
            return "redirect:/banque/" + banque.getId() + "/show";
        }

    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteAction(final Banque banque, final ModelMap model)
    {
        Banque banqueToDisable = banqueService.findOne(banque.getId());
        banqueService.disableEntity(banqueToDisable);
        return "redirect:/banque/";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchAction(@Valid final Banque banque, final BindingResult result, final ModelMap model)
    {

        return "redirect:/banque?query=" + banque.getLibelle() + "&page=1&size=2";

    }

    @RequestMapping(value = "{id}/edit", method = RequestMethod.GET)
    public String editAction(@PathVariable("id") final Long id, final ModelMap model)
    {
        final Banque banque = banqueService.findOne(id);
        model.addAttribute("banque", banque);
        return "banque/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateAction(final ModelMap model,
            @Valid final Banque banque, final BindingResult result,
            final RedirectAttributes redirectAttributes)
    {
        System.out.println("here we are in the controller update method");
        if (result.hasErrors()) {
            model.addAttribute("error", "error");
            return "banque/edit";
        }
        else {
            redirectAttributes.addFlashAttribute("info", "alert.success.new");
            banqueService.update(banque);
            return "redirect:/banque/" + banque.getId() + "/show";
        }
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
