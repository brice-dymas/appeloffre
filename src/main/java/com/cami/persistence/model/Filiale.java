package com.cami.persistence.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Filiale extends EntityObject implements Serializable {

	@Column(unique=false,updatable=true,insertable=true,nullable=true,length=255,scale=0,precision=0)
	@Basic
	@NotBlank(message = "{blank.message}")
	private String agence;

	@Column(unique=false,updatable=true,insertable=true,nullable=true,length=255,scale=0,precision=0)
	@Basic
	@NotBlank(message = "{blank.message}")
	private String code;

	@Column(unique=false,updatable=true,insertable=true,nullable=true,length=255,scale=0,precision=0)
	@Basic
	@NotBlank(message = "{blank.message}")
	private String nom;

	public Filiale() {

	}

	public Filiale(final Long id) {
		this.id = id;
	}

	public String getAgence() {
		return agence;
	}

	public void setAgence(final String agence) {
		this.agence = agence;
	}

	public String getCode() {
		return code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(final String nom) {
		this.nom = nom;
	}
}
