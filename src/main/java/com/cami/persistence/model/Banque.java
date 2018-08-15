/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.model;

import javax.persistence.Entity;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Brice GUEMKAM <briceguemkam@gmail.com>
 */
@Entity
public class Banque extends EntityObject
{

    @NotBlank(message = "{blank.message}")
    private String code;

    @NotBlank(message = "{blank.message}")
    private String libelle;

    public Banque()
    {
    }

    public Banque(Long id) {
        this.id = id;
    }
    
    

    public String getLibelle()
    {
        return libelle;
    }

    public void setLibelle(String libelle)
    {
        this.libelle = libelle;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    @Override
    public String toString()
    {
        return "Banque{" + "code=" + code + ", libelle=" + libelle + '}';
    }

}
