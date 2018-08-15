package com.cami.persistence.dao;

import com.cami.persistence.model.TypeMateriel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ITypeMaterielDao extends JpaRepository<TypeMateriel, Long>, JpaSpecificationExecutor<TypeMateriel>
{

    @Query("SELECT t FROM TypeMateriel t WHERE t.nom = :nom")
    TypeMateriel retrieveByName(@Param("nom") String nom);

    Page<TypeMateriel> findByNomLike(String nom, Pageable pageable);

    Page<TypeMateriel> findByCodeLike(String code, Pageable pageable);

    @Query("SELECT t FROM TypeMateriel t WHERE t.nom LIKE :nom and t.code LIKE :code AND t.deleted= :deleted")
    Page<TypeMateriel> searchLike(@Param("code") String code, @Param("nom") String nom, @Param("deleted") boolean deleted, Pageable pageable);

}
