package com.cami.persistence.model;

import java.io.Serializable;
import javax.persistence.Entity;
import org.hibernate.validator.constraints.NotBlank;

@Entity
public class TypeCaution
        extends EntityObject
        implements Serializable
{

    @NotBlank(message = "{blank.message}")
    private String pourcentage;

    @NotBlank(message = "{blank.message}")
    private String code;

    @NotBlank(message = "{blank.message}")
    private String nom;

    public TypeCaution()
    {

    }

    public TypeCaution(Long id) {
        this.id = id;
    }
    
    

    public String getPourcentage()
    {
        return pourcentage;
    }

    public void setPourcentage(final String pourcentage)
    {
        this.pourcentage = pourcentage;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(final String code)
    {
        this.code = code;
    }

    public String getNom()
    {
        return nom;
    }

    public void setNom(final String nom)
    {
        this.nom = nom;
    }
}
