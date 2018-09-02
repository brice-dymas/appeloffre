package com.cami.persistence.model;

import java.io.Serializable;
import static java.lang.Integer.MAX_VALUE;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

@Entity
public class Caution
        extends EntityObject
        implements Serializable {

    @NotBlank(message = "{blank.message}")
    private String numero;

    @NotNull(message = "{blank.message}")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @Min(value = 100000, message = "{min.message}")
    private long montant;

    @NotNull(message = "{blank.message}")
//    @Future(message = "{future.message}")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date dateFin;

    @ManyToOne(targetEntity = Role.class)
    private Role commercial;

    @ManyToOne(targetEntity = TypeCaution.class)
    private TypeCaution typeCaution;

    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @Digits(fraction = 0, integer = MAX_VALUE, message = "{digit.message}")
    @Min(value = 10000, message = "{min.message}")
    private long montantMarche;

    @NotBlank(message = "{blank.message}")
    private String referenceMarche;

    @Column(columnDefinition=" varchar(255) default ''")
    private String statut;

    @ManyToOne(targetEntity = AppelOffre.class)
    private AppelOffre appelOffre;

    @ManyToOne(targetEntity = Banque.class)
    private Banque banque;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date dateModification;

    
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @Column(columnDefinition=" int8 default 0")
    @Digits(fraction = 0, integer = MAX_VALUE, message = "{digit.message}")
    @Min(value = 0, message = "{min.message}")
    private long commissionTrimestrielle;

    @ManyToOne(targetEntity = Legende.class, optional = true)
    private Legende legende;

    public Caution() {

    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(final String numero) {
        this.numero = numero;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(final Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Banque getBanque() {
        return banque;
    }

    public void setBanque(final Banque banque) {
        this.banque = banque;
    }

    public long getMontant() {
        return montant;
    }

    public void setMontant(final long montant) {
        this.montant = montant;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(final Date dateFin) {
        this.dateFin = dateFin;
    }

    public TypeCaution getTypeCaution() {
        return typeCaution;
    }

    public void setTypeCaution(final TypeCaution typeCaution) {
        this.typeCaution = typeCaution;
    }

    public AppelOffre getAppelOffre() {
        return appelOffre;
    }

    public void setAppelOffre(final AppelOffre appelOffre) {
        this.appelOffre = appelOffre;
    }

    /**
     * @return the commercial
     */
    public Role getCommercial() {
        return commercial;
    }

    /**
     * @param date
     * <p>
     * @return the date parameter to the format day - month - year
     */
    public String getTrueDate(final Date date) {
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

    /**
     * @param commercial
     */
    public void setCommercial(final Role commercial) {
        this.commercial = commercial;
    }

    public long getMontantMarche() {
        return montantMarche;
    }

    public void setMontantMarche(long montantMarche) {
        this.montantMarche = montantMarche;
    }

    public String getReferenceMarche() {
        return referenceMarche;
    }

    public void setReferenceMarche(String referenceMarche) {
        this.referenceMarche = referenceMarche;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    public long getCommissionTrimestrielle() {
        return commissionTrimestrielle;
    }

    public void setCommissionTrimestrielle(long commissionTrimestrielle) {
        this.commissionTrimestrielle = commissionTrimestrielle;
    }

    public Legende getLegende() {
        return legende;
    }

    public void setLegende(Legende legende) {
        this.legende = legende;
    }

    @Override
    public String toString() {
        return "Caution{" + "numero=" + numero + ", dateDebut=" + dateDebut
                + ", montant=" + montant + ", dateFin=" + dateFin + ", commercial=" + commercial
                + ", typeCaution=" + typeCaution + ", montantMarche=" + montantMarche
                + ", referenceMarche=" + referenceMarche + ", statut=" + statut
                + ", appelOffre=" + appelOffre + ", banque=" + banque + ", dateModification=" + dateModification
                + ", commissionTrimestrielle=" + commissionTrimestrielle + ", legende=" + legende + '}';
    }

}
