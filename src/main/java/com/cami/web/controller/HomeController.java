package com.cami.web.controller;

import com.cami.persistence.service.IAppelOffreService;
import com.cami.persistence.service.IBanqueService;
import com.cami.persistence.service.ICautionService;
import com.cami.persistence.service.IFilialeService;
import com.cami.persistence.service.ILigneAppelService;
import com.cami.persistence.service.IMaterielService;
import com.cami.persistence.service.IRoleService;
import com.cami.persistence.service.ITypeCautionService;
import java.util.List;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

@Controller
public class HomeController
{

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private IAppelOffreService appelOffreService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IBanqueService banqueService;

    @Autowired
    private ILigneAppelService ligneAppelService;

    @Autowired
    private ICautionService cautionService;

    @Autowired
    private IMaterielService materielService;

    @Autowired
    private ITypeCautionService typeCautionService;

    @Autowired
    private IFilialeService filialeService;

  
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexAction(final ModelMap model, final WebRequest webRequest)
    {
        final Integer year = webRequest.getParameter("year") != null ? Integer.valueOf(webRequest.getParameter("year")) : 2015;
        
        List<Object[]> datas = cautionService.totalCautionParBanqueParMois(year);
        model.addAttribute("results", datas);
        System.out.println("HomeController");
        return "hello";
    }
 
    @RequestMapping(value = "/index.html", method = RequestMethod.GET)
    public String homeAction(final ModelMap model)
    {
        
        return "user/home";
    }

   

}
