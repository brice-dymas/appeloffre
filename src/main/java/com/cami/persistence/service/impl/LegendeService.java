/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.service.impl;

import com.cami.persistence.dao.ILegendeDao;
import com.cami.persistence.model.Legende;
import com.cami.persistence.service.ILegendeService;
import com.cami.persistence.service.common.AbstractService;
import com.google.common.collect.Lists;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sando
 */
@Transactional
@Service("legendeService")
public class LegendeService extends AbstractService<Legende> implements ILegendeService {

    @Autowired
    ILegendeDao dao;

    public LegendeService() {
        super();
    }

    // API
    @Override
    protected PagingAndSortingRepository<Legende, Long> getDao() {
        return dao;
    }

    // overridden to be secured
    @Override
    //    @Transactional(readOnly = true)
    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Legende> findAll() {
        return Lists.newArrayList(getDao().findAll());
    }

    @Override
    public Legende update(final Legende entity) {
        System.out.println("update method for ID " + entity.getId());
        final Legende temporalEntiy = dao.findOne(entity.getId());
        temporalEntiy.setLibelle(entity.getLibelle());
        return getDao().save(temporalEntiy);
    }

    @Override
    public List<Legende> filterByLibelle(final String nom) {
        System.out.println("ici = " + nom);
        return dao.findByLibelleLike('%' + nom + '%');
    }

    @Override
    public Page<Legende> findPaginated(final String query, final int i, final Integer size) {
        if (query == null) {
            System.out.println("Query = " + query);
            return super.findPaginated(i, size);
        } else {
            System.out.println("Not null Query = " + query);
            return dao.findByLibelleLike('%' + query + '%', new PageRequest(i, size));
        }
    }

    @Override
    public Page<Legende> findPaginated(final String nom, boolean deleted, final int page, final Integer size) {
        return dao.searchLike('%' + nom + '%', deleted, new PageRequest(page, size, Sort.Direction.DESC, "id"));
    }

    @Override
    public void disableEntity(final Legende entity) {
        final Legende legendeToDisable = dao.findOne(entity.getId());
//        System.out.println("before saving filialeID="+filialeToDisable.getId()+" deleted = "+filialeToDisable.isDeleted());
        if (legendeToDisable.isDeleted() == true) {
            legendeToDisable.setDeleted(false);
        } else {
            legendeToDisable.setDeleted(true);
        }
        dao.save(legendeToDisable);
    }

}
