/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.service.impl;

import com.cami.persistence.dao.IBanqueDao;
import com.cami.persistence.model.Banque;
import com.cami.persistence.service.IBanqueService;
import com.cami.persistence.service.common.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author Brice GUEMKAM <briceguemkam@gmail.com>
 */
@Service("banqueService")
public class BanqueService extends AbstractService<Banque> implements IBanqueService
{

    @Autowired
    IBanqueDao banqueDao;

    @Override
    protected PagingAndSortingRepository<Banque, Long> getDao()
    {
        return banqueDao;
    }

    @Override
    public Banque filterByNom(String nom)
    {
        return banqueDao.retrieveByName(nom);
    }

    @Override
    public Banque filterByCode(String code)
    {
        return banqueDao.retrieveByCode(code);
    }

    @Override
    public Page<Banque> findPaginated(String query, int i, Integer size)
    {
        if (query == null) {
            System.out.println("Query = " + query);
            return super.findPaginated(i, size);
        }
        else {
            System.out.println("Not null Query = " + query);
            return banqueDao.findByLibelle('%' + query + '%', new PageRequest(i, size));
        }
    }

    @Override
    public Page<Banque> findPaginated(String code, String nom, boolean deleted, int page, Integer size)
    {
        return banqueDao.chercherSuivant('%' + nom + '%', '%' + code + '%', deleted, new PageRequest(page, size, Sort.Direction.ASC, "code"));
    }

    @Override
    public Banque update(Banque entity)
    {
        Banque toUpdate = banqueDao.findOne(entity.getId());
        toUpdate.setCode(entity.getCode());
        toUpdate.setLibelle(entity.getLibelle());
        return banqueDao.save(toUpdate);
    }

    @Override
    public void disableEntity(final Banque entity)
    {
        final Banque banqueToDisable = banqueDao.findOne(entity.getId());
        System.out.println("before saving banqueID=" + banqueToDisable.getId()
                + " deleted =" + banqueToDisable.isDeleted());
        if (banqueToDisable.isDeleted() == true) {
            banqueToDisable.setDeleted(false);
        }
        else {
            banqueToDisable.setDeleted(true);
        }
        banqueDao.save(banqueToDisable);
        System.out.println("after saving banqueID=" + banqueToDisable.getId()
                + " deleted =" + banqueToDisable.isDeleted());
    }
}
