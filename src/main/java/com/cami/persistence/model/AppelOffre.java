package com.cami.persistence.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Entity
public class AppelOffre
        extends EntityObject
        implements Serializable
{

    private String pieceJointe1;

    @Transient
    private CommonsMultipartFile pieceJointe1Data;

    private String pieceJointe2;

    @Transient
    private CommonsMultipartFile pieceJointe2Data;

    private String pieceJointe3;

    @Transient
    private CommonsMultipartFile pieceJointe3Data;

    private String pieceJointe4;

    @Transient
    private CommonsMultipartFile pieceJointe4Data;

    private String pieceJointe5;

    @Transient
    private CommonsMultipartFile pieceJointe5Data;

    private String pieceJointe6;

    @Transient
    private CommonsMultipartFile pieceJointe6Data;

    private String pieceJointe7;

    @Transient
    private CommonsMultipartFile pieceJointe7Data;

    private String pieceJointe8;

    @Transient
    private CommonsMultipartFile pieceJointe8Data;

    @NotNull(message = "{blank.message}")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDepot;

    @NotBlank(message = "{blank.message}")
    private String delaiDeValidite;

    @ManyToOne(targetEntity = Filiale.class)
    private Filiale filiale;

    @OneToMany(mappedBy = "appelOffre", fetch = FetchType.LAZY)
    private List<LigneAppel> ligneAppels = new ArrayList<>();

    @OneToMany(mappedBy = "appelOffre")
    private List<Caution> cautions;

    @NotBlank(message = "{blank.message}")
    private String numero;

    @NotBlank(message = "{blank.message}")
    private String intitule;

    @NotBlank(message = "{blank.message}")
    private String maitreDouvrage;

    @ManyToOne(targetEntity = Role.class, fetch = FetchType.EAGER)
    Role user;

    @Temporal(TemporalType.DATE)
    private Date dateModification;

    private String etat;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @OrderColumn(name = "order_files")
    private List<String> files;

    public AppelOffre()
    {
    }

    public Date getDateModification()
    {
        return dateModification;
    }

    public void setDateModification(Date dateModification)
    {
        this.dateModification = dateModification;
    }

    public Role getUser()
    {
        return user;
    }

    public void setUser(Role user)
    {
        this.user = user;
    }

    public Date getDateDepot()
    {
        return dateDepot;
    }

    public void setDateDepot(final Date dateDepot)
    {
        this.dateDepot = dateDepot;
    }

    public Filiale getFiliale()
    {
        return filiale;
    }

    public void setFiliale(final Filiale filiale)
    {
        this.filiale = filiale;
    }

    public String getNumero()
    {
        return numero;
    }

    public void setNumero(final String numero)
    {
        this.numero = numero;
    }

    public String getIntitule()
    {
        return intitule;
    }

    public void setIntitule(final String intitule)
    {
        this.intitule = intitule;
    }

    public String getMaitreDouvrage()
    {
        return maitreDouvrage;
    }

    public void setMaitreDouvrage(final String maitreDouvrage)
    {
        this.maitreDouvrage = maitreDouvrage;
    }

    public List<Caution> getCautions()
    {
        return cautions;
    }

    public void setCautions(final List<Caution> cautions)
    {
        this.cautions = cautions;
    }

    public void addCautions(final Caution caution)
    {
        cautions.add(caution);
    }

    public List<LigneAppel> getLigneAppels()
    {
        return ligneAppels;
    }

    public void setLigneAppels(final List<LigneAppel> ligneAppels)
    {
        this.ligneAppels = ligneAppels;
    }

    public void addLigneAppels(final LigneAppel ligneAppel)
    {
        ligneAppels.add(ligneAppel);
    }

    public String getPieceJointe1()
    {
        return pieceJointe1;
    }

    public void setPieceJointe1(String pieceJointe1)
    {
        this.pieceJointe1 = pieceJointe1;
    }

    public CommonsMultipartFile getPieceJointe1Data()
    {
        return pieceJointe1Data;
    }

    public void setPieceJointe1Data(CommonsMultipartFile pieceJointe1Data)
    {
        this.pieceJointe1Data = pieceJointe1Data;
    }

    public String getPieceJointe2()
    {
        return pieceJointe2;
    }

    public void setPieceJointe2(String pieceJointe2)
    {
        this.pieceJointe2 = pieceJointe2;
    }

    public CommonsMultipartFile getPieceJointe2Data()
    {
        return pieceJointe2Data;
    }

    public void setPieceJointe2Data(CommonsMultipartFile pieceJointe2Data)
    {
        this.pieceJointe2Data = pieceJointe2Data;
    }

    public String getPieceJointe3()
    {
        return pieceJointe3;
    }

    public void setPieceJointe3(String pieceJointe3)
    {
        this.pieceJointe3 = pieceJointe3;
    }

    public CommonsMultipartFile getPieceJointe3Data()
    {
        return pieceJointe3Data;
    }

    public void setPieceJointe3Data(CommonsMultipartFile pieceJointe3Data)
    {
        this.pieceJointe3Data = pieceJointe3Data;
    }

    public String getPieceJointe4()
    {
        return pieceJointe4;
    }

    public void setPieceJointe4(String pieceJointe4)
    {
        this.pieceJointe4 = pieceJointe4;
    }

    public CommonsMultipartFile getPieceJointe4Data()
    {
        return pieceJointe4Data;
    }

    public void setPieceJointe4Data(CommonsMultipartFile pieceJointe4Data)
    {
        this.pieceJointe4Data = pieceJointe4Data;
    }

    public String getPieceJointe5()
    {
        return pieceJointe5;
    }

    public void setPieceJointe5(String pieceJointe5)
    {
        this.pieceJointe5 = pieceJointe5;
    }

    public CommonsMultipartFile getPieceJointe5Data()
    {
        return pieceJointe5Data;
    }

    public void setPieceJointe5Data(CommonsMultipartFile pieceJointe5Data)
    {
        this.pieceJointe5Data = pieceJointe5Data;
    }

    public String getPieceJointe6()
    {
        return pieceJointe6;
    }

    public void setPieceJointe6(String pieceJointe6)
    {
        this.pieceJointe6 = pieceJointe6;
    }

    public CommonsMultipartFile getPieceJointe6Data()
    {
        return pieceJointe6Data;
    }

    public void setPieceJointe6Data(CommonsMultipartFile pieceJointe6Data)
    {
        this.pieceJointe6Data = pieceJointe6Data;
    }

    public String getPieceJointe7()
    {
        return pieceJointe7;
    }

    public void setPieceJointe7(String pieceJointe7)
    {
        this.pieceJointe7 = pieceJointe7;
    }

    public CommonsMultipartFile getPieceJointe7Data()
    {
        return pieceJointe7Data;
    }

    public void setPieceJointe7Data(CommonsMultipartFile pieceJointe7Data)
    {
        this.pieceJointe7Data = pieceJointe7Data;
    }

    public String getPieceJointe8()
    {
        return pieceJointe8;
    }

    public void setPieceJointe8(String pieceJointe8)
    {
        this.pieceJointe8 = pieceJointe8;
    }

    public CommonsMultipartFile getPieceJointe8Data()
    {
        return pieceJointe8Data;
    }

    public void setPieceJointe8Data(CommonsMultipartFile pieceJointe8Data)
    {
        this.pieceJointe8Data = pieceJointe8Data;
    }

    /**
     * @return the date parameter to the format day - month - year
     */
    public String getTrueDate(final Date date)
    {
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

    /**
     * @return the etat
     */
    public String getEtat()
    {
        return etat;
    }

    /**
     * @param etat the etat to set
     */
    public void setEtat(final String etat)
    {
        this.etat = etat;
    }

    public String getDelaiDeValidite()
    {

        return this.delaiDeValidite;
    }

    public void setDelaiDeValidite(String delaiDeValidite)
    {
        this.delaiDeValidite = delaiDeValidite;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }
    
    public void addFile(String file){
        this.files.add(file);
    }
    
    

}
