package com.cami.persistence.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Periode extends EntityObject {

	@NotBlank(message = "{blank.message}")
	private String libelle;

	@NotNull(message = "{blank.message}")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date dateDebut;

	@NotNull(message = "{blank.message}")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date dateFin;

	@OneToMany
	private List<AppelOffre> appelOffre;

	public Periode() {
	}

	/**
	 * @param libelle
	 * @param dateDebut
	 * @param dateFin
	 * @param appelOffre
	 */
	public Periode(final String libelle, final Date dateDebut,
			final Date dateFin) {
		this.libelle = libelle;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
	}

	/**
	 * @return the libelle
	 */
	public String getLibelle() {
		return libelle;
	}

	/**
	 * @param libelle
	 *            the libelle to set
	 */
	public void setLibelle(final String libelle) {
		this.libelle = libelle;
	}

	/**
	 * @return the dateDebut
	 */
	public Date getDateDebut() {
		return dateDebut;
	}

	/**
	 * @param dateDebut
	 *            the dateDebut to set
	 */
	public void setDateDebut(final Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	/**
	 * @return the dateFin
	 */
	public Date getDateFin() {
		return dateFin;
	}

	/**
	 * @param dateFin
	 *            the dateFin to set
	 */
	public void setDateFin(final Date dateFin) {
		this.dateFin = dateFin;
	}

	/**
	 * @return the appelOffre
	 */
	public List<AppelOffre> getAppelOffre() {
		return appelOffre;
	}

	/**
	 * @param appelOffre
	 *            the appelOffre to set
	 */
	public void setAppelOffre(final List<AppelOffre> appelOffre) {
		this.appelOffre = appelOffre;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((appelOffre == null) ? 0 : appelOffre.hashCode());
		result = prime * result
				+ ((dateDebut == null) ? 0 : dateDebut.hashCode());
		result = prime * result + ((dateFin == null) ? 0 : dateFin.hashCode());
		result = prime * result + ((libelle == null) ? 0 : libelle.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Periode other = (Periode) obj;
		if (appelOffre == null) {
			if (other.appelOffre != null)
				return false;
		} else if (!appelOffre.equals(other.appelOffre))
			return false;
		if (dateDebut == null) {
			if (other.dateDebut != null)
				return false;
		} else if (!dateDebut.equals(other.dateDebut))
			return false;
		if (dateFin == null) {
			if (other.dateFin != null)
				return false;
		} else if (!dateFin.equals(other.dateFin))
			return false;
		if (libelle == null) {
			if (other.libelle != null)
				return false;
		} else if (!libelle.equals(other.libelle))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Periode [libelle=" + libelle + ", dateDebut=" + dateDebut
				+ ", dateFin=" + dateFin + ", appelOffre=" + appelOffre
				+ ", id=" + id + ", version=" + version + "]";
	}


}
