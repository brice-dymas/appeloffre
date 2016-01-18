package com.cami.persistence.dao;

import com.cami.persistence.model.TypeCaution;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ITypeCautionDao extends JpaRepository<TypeCaution, Long>, JpaSpecificationExecutor<TypeCaution>
{

    Page<TypeCaution> findByNomLike(String nom, Pageable pageable);

    List<TypeCaution> findByNomLike(String nom);

    @Query("SELECT t FROM TypeCaution t WHERE t.nom LIKE :nom and t.code LIKE :code AND t.deleted= :deleted")
    Page<TypeCaution> searchLike(@Param("code") String code, @Param("nom") String nom, @Param("deleted") boolean deleted, Pageable pageable);

}
