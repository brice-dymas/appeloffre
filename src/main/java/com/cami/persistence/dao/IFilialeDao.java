package com.cami.persistence.dao;

import com.cami.persistence.model.Filiale;
import com.cami.persistence.model.TypeMateriel;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IFilialeDao extends JpaRepository<Filiale, Long>, JpaSpecificationExecutor<TypeMateriel>
{

    Page<Filiale> findByNomLike(String nom, Pageable pageable);

    List<Filiale> findByNomLike(String nom);

    @Query("SELECT f FROM Filiale f WHERE f.nom LIKE :nom and f.code LIKE :code and f.agence LIKE :agence AND f.deleted= :deleted")
    Page<Filiale> searchLike(@Param("agence") String agence, @Param("code") String code, @Param("nom") String nom, @Param("deleted") boolean deleted, Pageable pageable);

}
