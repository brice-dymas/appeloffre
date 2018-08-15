package com.cami.persistence.dao;

import com.cami.persistence.model.LigneAppel;
import com.cami.persistence.model.TypeMateriel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ILigneAppelDao extends JpaRepository<LigneAppel, Long>, JpaSpecificationExecutor<TypeMateriel> {

    @Query("SELECT l FROM LigneAppel l WHERE l.appelOffre.id = :appelOffreId")
    List<LigneAppel> filterByAppelOffre(@Param("appelOffreId")Long appelOffreId);

}
