/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.web.form;

import com.cami.persistence.model.AppelOffre;
import com.cami.persistence.model.Caution;
import com.cami.persistence.model.LigneAppel;
import com.cami.web.util.ShrinkableLazyList;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author gervais
 */
public class AppelOffreForm {

    @Valid
    private AppelOffre appelOffre;

    @Valid
    @NotEmpty
    // private AutoPopulatingList<Caution> cautions;
    private List<Caution> cautions = LazyList.decorate(
            new ArrayList(), FactoryUtils.instantiateFactory(Caution.class));

    @Valid
    @NotEmpty(message = "At least one row")
    //private AutoPopulatingList<LigneAppel> ligneAppels;
    private List<LigneAppel> ligneAppels = ShrinkableLazyList.decorate(
            new ArrayList(), FactoryUtils.instantiateFactory(LigneAppel.class));

    public AppelOffreForm() {
//        appelOffre = new AppelOffre();
    }

    public AppelOffre getAppelOffre() {
        if (appelOffre != null) {
            appelOffre.setCautions(cautions);
            appelOffre.setLigneAppels(ligneAppels);
        }
        return appelOffre;
    }

    public void setAppelOffre(AppelOffre appelOffre) {
        this.appelOffre = appelOffre;
    }

    public List<Caution> getCautions() {
        return cautions;
    }

    public void setCautions(List<Caution> cautions) {
        this.cautions = cautions;
    }

    public List<LigneAppel> getLigneAppels() {
        return ligneAppels;
    }

    public void setLigneAppels(List<LigneAppel> ligneAppels) {
        this.ligneAppels = ligneAppels;
    }

}
