package com.cami.persistence.dao;

import com.cami.persistence.model.CautionDouane;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ICautionDouaneDao extends JpaRepository<CautionDouane, Long>, JpaSpecificationExecutor<CautionDouane> {

    @Query("SELECT c FROM CautionDouane c WHERE c.dateDebut LIKE :dateDebut OR c.dateFin LIKE :dateFin OR c.banque LIKE :banque")
    Page<CautionDouane> filterByPeriod(@Param("dateDebut") String dateDebut,
            @Param("dateFin") String dateFin, @Param("banque") String banque,
            Pageable pageable);

    @Query("SELECT c FROM CautionDouane c WHERE c.banque.id = :banque "
            + "AND c.dateFin >= :debutPeriodeEcheance AND c.dateFin <= :finPeriodeEcheance")
    Page<CautionDouane> filterByBanque(@Param("banque") long banque,
            @Param("debutPeriodeEcheance") Date debutPeriodeEcheance,
            @Param("finPeriodeEcheance") Date finPeriodeEcheance, Pageable pageable);

    @Query("SELECT c FROM CautionDouane c WHERE c.montant = :montant "
            + "AND c.dateFin >= :debutPeriodeEcheance AND c.dateFin <= :finPeriodeEcheance")
    Page<CautionDouane> filterByMontant(@Param("montant") int montant,
            @Param("debutPeriodeEcheance") Date debutPeriodeEcheance,
            @Param("finPeriodeEcheance") Date finPeriodeEcheance, Pageable pageable);

    @Query("SELECT c FROM CautionDouane c WHERE c.dateFin >= :debutPeriodeEcheance AND c.dateFin <= :finPeriodeEcheance")
    Page<CautionDouane> filter(@Param("debutPeriodeEcheance") Date debutPeriodeEcheance, @Param("finPeriodeEcheance") Date finPeriodeEcheance, Pageable pageable);

    @Query("SELECT c FROM CautionDouane c WHERE c.banque.id = :banque AND c.montant = :montant "
            + "AND c.dateFin >= :debutPeriodeEcheance AND c.dateFin <= :finPeriodeEcheance")
    Page<CautionDouane> filterByMontantAndBanque(@Param("banque") long banque, @Param("montant") int montant,
            @Param("debutPeriodeEcheance") Date debutPeriodeEcheance,
            @Param("finPeriodeEcheance") Date finPeriodeEcheance, Pageable pageable);

    @Query("SELECT SUM(c.montant), b.libelle, MONTH(c.dateDebut) FROM CautionDouane c join c.banque b WHERE YEAR(c.dateDebut) = :year"
            + " GROUP BY b.libelle, MONTH(c.dateDebut) ORDER BY b.libelle, MONTH(c.dateDebut) ASC")
    List<Object[]> totalCautionParBanqueParMois(@Param("year") int year);
}
