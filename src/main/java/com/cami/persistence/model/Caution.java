package com.cami.persistence.model;

import java.io.Serializable;
import static java.lang.Integer.MAX_VALUE;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

@Entity
public class Caution
        extends EntityObject
        implements Serializable
{

    @NotBlank(message = "{blank.message}")
    private String numero;

    @NotNull(message = "{blank.message}")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @Min(value = 100000, message = "{min.message}")
    private int montant;

    @NotNull(message = "{blank.message}")
    @Future(message = "{future.message}")
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
    private int montantMarche;

    @NotBlank(message = "{blank.message}")
    private String referenceMarche;

    @ManyToOne(targetEntity = AppelOffre.class)
    private AppelOffre appelOffre;

    @ManyToOne(targetEntity = Banque.class)
    private Banque banque;

    public Caution()
    {

    }

    public String getNumero()
    {
        return numero;
    }

    public void setNumero(final String numero)
    {
        this.numero = numero;
    }

    public Date getDateDebut()
    {
        return dateDebut;
    }

    public void setDateDebut(final Date dateDebut)
    {
        this.dateDebut = dateDebut;
    }

    public Banque getBanque()
    {
        return banque;
    }

    public void setBanque(final Banque banque)
    {
        this.banque = banque;
    }

    public int getMontant()
    {
        return montant;
    }

    public void setMontant(final int montant)
    {
        this.montant = montant;
    }

    public Date getDateFin()
    {
        return dateFin;
    }

    public void setDateFin(final Date dateFin)
    {
        this.dateFin = dateFin;
    }

    public TypeCaution getTypeCaution()
    {
        return typeCaution;
    }

    public void setTypeCaution(final TypeCaution typeCaution)
    {
        this.typeCaution = typeCaution;
    }

    public AppelOffre getAppelOffre()
    {
        return appelOffre;
    }

    public void setAppelOffre(final AppelOffre appelOffre)
    {
        this.appelOffre = appelOffre;
    }

    /**
     * @return the commercial
     */
    public Role getCommercial()
    {
        return commercial;
    }

    /**
     * @param date
     * <p>
     * @return the date parameter to the format day - month - year
     */
    public String getTrueDate(final Date date)
    {
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

    /**
     * @param commercial
     */
    public void setCommercial(final Role commercial)
    {
        this.commercial = commercial;
    }

    public int getMontantMarche()
    {
        return montantMarche;
    }

    public void setMontantMarche(int montantMarche)
    {
        this.montantMarche = montantMarche;
    }

    public String getReferenceMarche()
    {
        return referenceMarche;
    }

    public void setReferenceMarche(String referenceMarche)
    {
        this.referenceMarche = referenceMarche;
    }
}
