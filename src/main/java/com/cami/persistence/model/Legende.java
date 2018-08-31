/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.model;

import java.io.Serializable;
import javax.persistence.Entity;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author sando
 */
@Entity
public class Legende extends EntityObject implements Serializable {

    @NotBlank(message = "{blank.message}")
    private String libelle;

    public Legende() {
    }

    public Legende(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "Legende{" + "libelle=" + libelle + '}';
    }

}
