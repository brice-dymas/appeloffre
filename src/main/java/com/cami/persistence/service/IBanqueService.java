/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.service;

import com.cami.persistence.IOperations;
import com.cami.persistence.model.Banque;
import org.springframework.data.domain.Page;

/**
 *
 * @author Brice GUEMKAM <briceguemkam@gmail.com>
 */
public interface IBanqueService extends IOperations<Banque>
{

    public Banque filterByNom(String nom);

    public Banque filterByCode(String code);

    public Page<Banque> findPaginated(String query, int i, Integer size);

    public Page<Banque> findPaginated(String code, String nom, boolean deleted, int page, Integer size);
}
