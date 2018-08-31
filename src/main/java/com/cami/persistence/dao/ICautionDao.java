package com.cami.persistence.dao;

import com.cami.persistence.model.Caution;
import com.cami.persistence.model.TypeMateriel;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ICautionDao extends JpaRepository<Caution, Long>, JpaSpecificationExecutor<TypeMateriel> {

    @Query("SELECT c FROM Caution c WHERE c.appelOffre.id = :appelOffreId")
    List<Caution> filterByAppelOffre(@Param("appelOffreId") Long appelOffreId);

    @Query("SELECT c FROM Caution c WHERE c.appelOffre.id = :appelOffreId AND c.commercial.id= :idCommercial")
    List<Caution> filterByAppelOffreAndUser(@Param("appelOffreId") Long appelOffreId, @Param("idCommercial") Long idCommercial);

    @Query("SELECT c FROM Caution c WHERE c.dateDebut LIKE :dateDebut OR c.dateFin LIKE :dateFin OR c.banque LIKE :banque")
    Page<Caution> filterByPeriod(@Param("dateDebut") String dateDebut,
            @Param("dateFin") String dateFin, @Param("banque") String banque,
            Pageable pageable);

    @Query("SELECT c FROM Caution c WHERE c.banque.id = :banque AND c.numero LIKE :numero AND c.appelOffre.maitreDouvrage LIKE :maitreDouvrage "
            + "AND c.dateFin >= :debutPeriodeEcheance AND c.dateFin <= :finPeriodeEcheance")
    Page<Caution> filterByBanque(@Param("numero") String numero, @Param("maitreDouvrage") String maitreDouvrage,
            @Param("banque") long banque, @Param("debutPeriodeEcheance") Date debutPeriodeEcheance,
            @Param("finPeriodeEcheance") Date finPeriodeEcheance, Pageable pageable);

    @Query("SELECT c FROM Caution c WHERE c.typeCaution.id = :typeCaution AND c.numero LIKE :numero AND c.appelOffre.maitreDouvrage LIKE :maitreDouvrage "
            + "AND c.dateFin >= :debutPeriodeEcheance AND c.dateFin <= :finPeriodeEcheance")
    Page<Caution> filterByTypeCaution(@Param("numero") String numero, @Param("maitreDouvrage") String maitreDouvrage,
            @Param("typeCaution") long typeCaution, @Param("debutPeriodeEcheance") Date debutPeriodeEcheance, @Param("finPeriodeEcheance") Date finPeriodeEcheance, Pageable pageable);

    @Query("SELECT c FROM Caution c WHERE c.dateFin >= :debutPeriodeEcheance AND c.dateFin <= :finPeriodeEcheance "
            + "AND c.numero LIKE :numero AND c.appelOffre.maitreDouvrage LIKE :maitreDouvrage ")
    Page<Caution> filter(@Param("numero") String numero, @Param("maitreDouvrage") String maitreDouvrage,
            @Param("debutPeriodeEcheance") Date debutPeriodeEcheance, @Param("finPeriodeEcheance") Date finPeriodeEcheance, Pageable pageable);

    @Query("SELECT c FROM Caution c WHERE c.banque.id = :banque AND c.typeCaution.id = :typeCaution "
            + "AND c.dateFin >= :debutPeriodeEcheance AND c.dateFin <= :finPeriodeEcheance "
            + "AND c.numero LIKE :numero AND c.appelOffre.maitreDouvrage LIKE :maitreDouvrage ")
    Page<Caution> filterByTypeCautionAndBanque(@Param("numero") String numero, @Param("maitreDouvrage") String maitreDouvrage,
            @Param("banque") long banque, @Param("typeCaution") long typeCaution, @Param("debutPeriodeEcheance") Date debutPeriodeEcheance, @Param("finPeriodeEcheance") Date finPeriodeEcheance, Pageable pageable);

    @Query("SELECT c FROM Caution c WHERE c.banque.id = :banque AND c.typeCaution.id = :typeCaution "
            + "AND c.dateFin >= :debutPeriodeEcheance AND c.dateFin <= :finPeriodeEcheance AND c.commercial.id= :idUser "
            + "AND c.numero LIKE :numero AND c.appelOffre.maitreDouvrage LIKE :maitreDouvrage ")
    Page<Caution> filterByUser(@Param("numero") String numero, @Param("maitreDouvrage") String maitreDouvrage,
            @Param("banque") long banque, @Param("typeCaution") long typeCaution, @Param("debutPeriodeEcheance") Date debutPeriodeEcheance, @Param("finPeriodeEcheance") Date finPeriodeEcheance, @Param("idUser") long idUser, Pageable pageable);

    @Query("SELECT c FROM Caution c WHERE c.banque.id = :banque "
            + "AND c.dateFin >= :debutPeriodeEcheance AND c.dateFin <= :finPeriodeEcheance AND c.commercial.id= :idUser"
            + " AND c.numero LIKE :numero AND c.appelOffre.maitreDouvrage LIKE :maitreDouvrage ")
    Page<Caution> filterByBanqueAndUser(@Param("numero") String numero, @Param("maitreDouvrage") String maitreDouvrage,
            @Param("banque") long banque, @Param("debutPeriodeEcheance") Date debutPeriodeEcheance, @Param("finPeriodeEcheance") Date finPeriodeEcheance, @Param("idUser") long idUser, Pageable pageable);

    @Query("SELECT c FROM Caution c WHERE c.typeCaution.id = :typeCaution "
            + "AND c.dateFin >= :debutPeriodeEcheance AND c.dateFin <= :finPeriodeEcheance AND c.commercial.id= :idUser "
            + "AND c.numero LIKE :numero AND c.appelOffre.maitreDouvrage LIKE :maitreDouvrage ")
    Page<Caution> filterByTypeCautionAndUser(@Param("numero") String numero, @Param("maitreDouvrage") String maitreDouvrage,
            @Param("typeCaution") long typeCaution, @Param("debutPeriodeEcheance") Date debutPeriodeEcheance, @Param("finPeriodeEcheance") Date finPeriodeEcheance, @Param("idUser") long idUser, Pageable pageable);

    @Query("SELECT c FROM Caution c WHERE c.dateFin >= :debutPeriodeEcheance AND c.dateFin <= :finPeriodeEcheance AND c.commercial.id= :idUser "
            + "AND c.numero LIKE :numero AND c.appelOffre.maitreDouvrage LIKE :maitreDouvrage ")
    Page<Caution> filterByUser(@Param("numero") String numero, @Param("maitreDouvrage") String maitreDouvrage,
            @Param("debutPeriodeEcheance") Date debutPeriodeEcheance, @Param("finPeriodeEcheance") Date finPeriodeEcheance, @Param("idUser") long idUser, Pageable pageable);

    @Query("SELECT c FROM Caution c WHERE c.banque.id = :banque AND c.typeCaution.id = :typeCaution "
            + "AND c.dateFin >= :debutPeriodeEcheance AND c.dateFin <= :finPeriodeEcheance AND c.commercial.id= :idUser "
            + "AND c.numero LIKE :numero AND c.appelOffre.maitreDouvrage LIKE :maitreDouvrage ")
    Page<Caution> filterByTypeCautionBanqueAndUser(@Param("numero") String numero, @Param("maitreDouvrage") String maitreDouvrage,
            @Param("banque") long banque, @Param("typeCaution") long typeCaution, @Param("debutPeriodeEcheance") Date debutPeriodeEcheance, @Param("finPeriodeEcheance") Date finPeriodeEcheance, @Param("idUser") long idUser, Pageable pageable);

    @Query("SELECT c FROM Caution c WHERE c.commercial.user.username LIKE :username")
    Page<Caution> filterByUsername(@Param("username") String username, Pageable pageable);

    @Query("SELECT SUM(c.montant), b.libelle, MONTH(c.dateDebut) FROM Caution c join c.banque b WHERE YEAR(c.dateDebut) = :year"
            + " GROUP BY b.libelle, MONTH(c.dateDebut) ORDER BY b.libelle, MONTH(c.dateDebut) ASC")
    List<Object[]> totalCautionParBanqueParMois(@Param("year") int year);
}
