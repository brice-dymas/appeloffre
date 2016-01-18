package com.cami.persistence.model;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class TypeMateriel extends EntityObject {


	@NotBlank(message = "{blank.message}")
	private String code;

	@NotBlank(message = "{blank.message}")
	private String nom;

	public TypeMateriel() {

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

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TypeMateriel [code=" + code + ", nom=" + nom + ", id=" + id
				+ ", version=" + version + "]";
	}


}
