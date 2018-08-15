/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.service.impl;

import com.cami.persistence.dao.ILigneAppelDao;
import com.cami.persistence.model.LigneAppel;
import com.cami.persistence.service.ILigneAppelService;
import com.cami.persistence.service.common.AbstractService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author gervais
 */
@Service("ligneAppelService")
@Transactional
public class LigneAppelService extends AbstractService<LigneAppel> implements ILigneAppelService
{

    @Autowired
    private ILigneAppelDao dao;

    @Override
    protected PagingAndSortingRepository<LigneAppel, Long> getDao()
    {
        return dao;
    }

    @Override
    public List<LigneAppel> filterByAppelOffre(Long appelOffreId)
    {
        return dao.filterByAppelOffre(appelOffreId);
    }

    @Override
    public void disableEntity(final LigneAppel entity)
    {
        final LigneAppel ligneAppelToDisable = dao.findOne(entity.getId());
        if (ligneAppelToDisable.isDeleted() == true) {
            ligneAppelToDisable.setDeleted(false);
        }
        else {
            ligneAppelToDisable.setDeleted(true);
        }
        dao.save(ligneAppelToDisable);
    }

}
