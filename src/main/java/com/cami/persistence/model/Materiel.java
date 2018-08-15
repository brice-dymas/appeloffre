package com.cami.persistence.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Materiel extends EntityObject implements Serializable {


	@NotBlank(message = "{blank.message}")
	private String code;

	@NotBlank(message = "{blank.message}")
	private String description;

	@ManyToOne(targetEntity = TypeMateriel.class)
	private TypeMateriel typeMateriel;


	@NotBlank(message = "{blank.message}")
	private String nom;

	public Materiel() {

	}

	public String getCode() {
		return code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public TypeMateriel getTypeMateriel() {
		return typeMateriel;
	}

	public void setTypeMateriel(final TypeMateriel typeMateriel) {
		this.typeMateriel = typeMateriel;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(final String nom) {
		this.nom = nom;
	}
}
