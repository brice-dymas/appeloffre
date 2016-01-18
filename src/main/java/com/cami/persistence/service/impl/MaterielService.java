/**
 *
 * @author fabrice
 */
package com.cami.persistence.service.impl;

import com.cami.persistence.dao.IMaterielDao;
import com.cami.persistence.dao.ITypeMaterielDao;
import com.cami.persistence.model.Materiel;
import com.cami.persistence.service.IMaterielService;
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

@Service("materielService")
@Transactional
public class MaterielService
        extends AbstractService<Materiel>
        implements IMaterielService
{

    @Autowired
    private IMaterielDao dao;

    @Autowired
    private ITypeMaterielDao typeMaterielDao;

    public MaterielService()
    {
        super();
    }

    // API
    @Override
    protected PagingAndSortingRepository<Materiel, Long> getDao()
    {
        return dao;
    }

    // custom methods
    @Override
    public Materiel retrieveByName(final String name)
    {
        return dao.retrieveByName(name);
    }

    // overridden to be secured
    @Override
//    @Transactional(readOnly = true)
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Materiel> findAll()
    {
        return Lists.newArrayList(getDao().findAll());
    }

    @Override
    public Materiel update(Materiel entity)
    {
        System.out.println("update materiel");
        Materiel temporalEntiy = getDao().findOne(entity.getId());
        System.out.println("materiel retrieved");
        temporalEntiy.setCode(entity.getCode());
        System.out.println("materiel code setted");
        temporalEntiy.setNom(entity.getNom());
        System.out.println("materiel nom setted");
        temporalEntiy.setDescription(entity.getDescription());
        System.out.println("materiel description setted");
        temporalEntiy.setTypeMateriel(typeMaterielDao.findOne(entity.getTypeMateriel().getId()));
        System.out.println("materiel typeMateriel setted");
        return getDao().save(temporalEntiy);

    }

    @Override
    public Page<Materiel> findPaginated(String query, int i, Integer size)
    {
        if (query == null) {
            System.out.println("Query = " + query);
            return super.findPaginated(i, size);
        }
        else {
            System.out.println("Not null Query = " + query);
            return dao.findByNomLike('%' + query + '%', new PageRequest(i, size));
        }
    }

    @Override
    @Transactional
    public Materiel create(Materiel materiel)
    {
        materiel.setTypeMateriel(typeMaterielDao.findOne(materiel.getTypeMateriel().getId()));
        return dao.save(materiel); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Page<Materiel> findPaginated(Long typeMaterielId, String code, String nom, boolean deleted, int page, Integer size)
    {
        System.out.println("debut find");
        if (-1 == typeMaterielId) {
            System.out.println("find-1");
            return dao.searchLike('%' + code + '%', '%' + nom + '%', deleted, new PageRequest(page, size, Sort.Direction.ASC, "code"));
        }
        else {
            System.out.println("find-2");
            return dao.searchLikeWithTypeMateriel(typeMaterielId, '%' + code + '%', '%' + nom + '%', deleted, new PageRequest(page, size, Sort.Direction.ASC, "code"));
        }

    }

    @Override
    public void disableEntity(final Materiel entity)
    {
        final Materiel materielToDisable = dao.findOne(entity.getId());
        if (materielToDisable.isDeleted() == true) {
            materielToDisable.setDeleted(false);
        }
        else {
            materielToDisable.setDeleted(true);
        }
        dao.save(materielToDisable);
    }

}
