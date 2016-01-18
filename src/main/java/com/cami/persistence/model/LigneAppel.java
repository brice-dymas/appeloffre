package com.cami.persistence.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;

@Entity
public class LigneAppel extends EntityObject implements Serializable {

	@ManyToOne(targetEntity = Materiel.class)
	private Materiel materiel;

	@NotNull(message = "{blank.message}")
	@NumberFormat(style = NumberFormat.Style.NUMBER)
	@Min(value = 1000, message = "{min.message}")
	private int prixUnitaire;


	@ManyToOne(targetEntity = AppelOffre.class)
	private AppelOffre appelOffre;

	@NotNull(message = "{blank.message}")
	@NumberFormat(style = NumberFormat.Style.NUMBER)
	@Min(value = 1, message = "{min.message}")
	private int quantite;

	public LigneAppel() {

	}

	public Materiel getMateriel() {
		return materiel;
	}

	public void setMateriel(final Materiel materiel) {
		this.materiel = materiel;
	}

	public int getPrixUnitaire() {
		return prixUnitaire;
	}

	public void setPrixUnitaire(final int prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
	}

	public AppelOffre getAppelOffre() {
		return appelOffre;
	}

	public void setAppelOffre(final AppelOffre appelOffre) {
		this.appelOffre = appelOffre;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(final int quantite) {
		this.quantite = quantite;
	}
}
