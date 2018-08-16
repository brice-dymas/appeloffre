package com.cami.persistence.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Entity
public class CautionDouane
        extends EntityObject
        implements Serializable {

    @NotBlank(message = "{blank.message}")
    private String numero;

    @NotBlank(message = "{blank.message}")
    private String libelle;

    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @Min(value = 100000, message = "{min.message}")
    private int montant;

    @NotNull(message = "{blank.message}")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date dateFin;

    @NotBlank(message = "{blank.message}")
    private String divers;

    @ManyToOne(targetEntity = Banque.class)
    private Banque banque;

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

    @ElementCollection(fetch = FetchType.EAGER)
    @OrderColumn(name = "order_files")
    private List<String> files;

    public CautionDouane() {
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getDivers() {
        return divers;
    }

    public void setDivers(String divers) {
        this.divers = divers;
    }

    public Banque getBanque() {
        return banque;
    }

    public void setBanque(Banque banque) {
        this.banque = banque;
    }

    public String getPieceJointe1() {
        return pieceJointe1;
    }

    public void setPieceJointe1(String pieceJointe1) {
        this.pieceJointe1 = pieceJointe1;
    }

    public CommonsMultipartFile getPieceJointe1Data() {
        return pieceJointe1Data;
    }

    public void setPieceJointe1Data(CommonsMultipartFile pieceJointe1Data) {
        this.pieceJointe1Data = pieceJointe1Data;
    }

    public String getPieceJointe2() {
        return pieceJointe2;
    }

    public void setPieceJointe2(String pieceJointe2) {
        this.pieceJointe2 = pieceJointe2;
    }

    public CommonsMultipartFile getPieceJointe2Data() {
        return pieceJointe2Data;
    }

    public void setPieceJointe2Data(CommonsMultipartFile pieceJointe2Data) {
        this.pieceJointe2Data = pieceJointe2Data;
    }

    public String getPieceJointe3() {
        return pieceJointe3;
    }

    public void setPieceJointe3(String pieceJointe3) {
        this.pieceJointe3 = pieceJointe3;
    }

    public CommonsMultipartFile getPieceJointe3Data() {
        return pieceJointe3Data;
    }

    public void setPieceJointe3Data(CommonsMultipartFile pieceJointe3Data) {
        this.pieceJointe3Data = pieceJointe3Data;
    }

    public String getPieceJointe4() {
        return pieceJointe4;
    }

    public void setPieceJointe4(String pieceJointe4) {
        this.pieceJointe4 = pieceJointe4;
    }

    public CommonsMultipartFile getPieceJointe4Data() {
        return pieceJointe4Data;
    }

    public void setPieceJointe4Data(CommonsMultipartFile pieceJointe4Data) {
        this.pieceJointe4Data = pieceJointe4Data;
    }

    public String getPieceJointe5() {
        return pieceJointe5;
    }

    public void setPieceJointe5(String pieceJointe5) {
        this.pieceJointe5 = pieceJointe5;
    }

    public CommonsMultipartFile getPieceJointe5Data() {
        return pieceJointe5Data;
    }

    public void setPieceJointe5Data(CommonsMultipartFile pieceJointe5Data) {
        this.pieceJointe5Data = pieceJointe5Data;
    }

    public String getPieceJointe6() {
        return pieceJointe6;
    }

    public void setPieceJointe6(String pieceJointe6) {
        this.pieceJointe6 = pieceJointe6;
    }

    public CommonsMultipartFile getPieceJointe6Data() {
        return pieceJointe6Data;
    }

    public void setPieceJointe6Data(CommonsMultipartFile pieceJointe6Data) {
        this.pieceJointe6Data = pieceJointe6Data;
    }

    public String getPieceJointe7() {
        return pieceJointe7;
    }

    public void setPieceJointe7(String pieceJointe7) {
        this.pieceJointe7 = pieceJointe7;
    }

    public CommonsMultipartFile getPieceJointe7Data() {
        return pieceJointe7Data;
    }

    public void setPieceJointe7Data(CommonsMultipartFile pieceJointe7Data) {
        this.pieceJointe7Data = pieceJointe7Data;
    }

    public String getPieceJointe8() {
        return pieceJointe8;
    }

    public void setPieceJointe8(String pieceJointe8) {
        this.pieceJointe8 = pieceJointe8;
    }

    public CommonsMultipartFile getPieceJointe8Data() {
        return pieceJointe8Data;
    }

    public void setPieceJointe8Data(CommonsMultipartFile pieceJointe8Data) {
        this.pieceJointe8Data = pieceJointe8Data;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "CautionDouane{" + "numero=" + numero + ", libelle=" + libelle
                + ", montant=" + montant + ", dateDebut=" + dateDebut + ", dateFin="
                + dateFin + ", divers=" + divers + ", banque=" + banque
                + ", files=" + files + '}';
    }

}
