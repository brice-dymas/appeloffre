/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.dao;

import com.cami.persistence.model.Banque;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Brice GUEMKAM <briceguemkam@gmail.com>
 */
public interface IBanqueDao extends JpaRepository<Banque, Long>, JpaSpecificationExecutor<Banque>
{

    @Query("SELECT b FROM Banque b WHERE b.libelle = :libelle")
    Banque retrieveByName(@Param("libelle") String libelle);

    @Query("SELECT b FROM Banque b WHERE b.code = :code")
    Banque retrieveByCode(@Param("code") String code);

    Page<Banque> findByLibelle(String libelle, Pageable pageable);

    @Query("SELECT b FROM Banque b WHERE b.libelle LIKE :libelle and b.code LIKE :code AND b.deleted= :deleted")
    Page<Banque> chercherSuivant(@Param("libelle") String libelle, @Param("code") String code, @Param("deleted") boolean deleted, Pageable pageable);

}
