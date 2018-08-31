/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.dao;

import com.cami.persistence.model.Legende;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author sando
 */
public interface ILegendeDao extends JpaRepository<Legende, Long>, JpaSpecificationExecutor<Legende> {

    List<Legende> findByLibelleLike(String nom);

    Page<Legende> findByLibelleLike(String nom, Pageable pageable);

    @Query("SELECT L FROM Legende L WHERE L.libelle LIKE :nom AND L.deleted= :deleted")
    Page<Legende> searchLike(@Param("nom") String nom, @Param("deleted") boolean deleted, Pageable pageable);

}
