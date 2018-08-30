/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.service;

import com.cami.persistence.IOperations;
import com.cami.persistence.model.Legende;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 *
 * @author sando
 */
public interface ILegendeService extends IOperations<Legende> {

    public List<Legende> filterByLibelle(String nom);

    public Page<Legende> findPaginated(String query, int i, Integer size);

    public Page<Legende> findPaginated(String libelle, boolean deleted, int page, Integer size);
}
