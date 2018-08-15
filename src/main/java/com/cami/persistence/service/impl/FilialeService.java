package com.cami.persistence.service.impl;

import com.cami.persistence.dao.IFilialeDao;
import com.cami.persistence.model.Filiale;
import com.cami.persistence.service.IFilialeService;
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

@Service("filialeService")
@Transactional
public class FilialeService extends AbstractService<Filiale> implements IFilialeService
{

    @Autowired
    private IFilialeDao dao;

    public FilialeService()
    {
        super();
    }

    // API
    @Override
    protected PagingAndSortingRepository<Filiale, Long> getDao()
    {
        return dao;
    }

    // overridden to be secured
    @Override
    //    @Transactional(readOnly = true)
    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Filiale> findAll()
    {
        return Lists.newArrayList(getDao().findAll());
    }

    @Override
    public Filiale update(final Filiale entity)
    {
        System.out.println("update method for ID " + entity.getId());
        final Filiale temporalEntiy = dao.findOne(entity.getId());
        temporalEntiy.setCode(entity.getCode());
        temporalEntiy.setNom(entity.getNom());
        temporalEntiy.setAgence(entity.getAgence());
        return getDao().save(temporalEntiy);
    }

    @Override
    public List<Filiale> filterByNom(final String nom)
    {
        System.out.println("ici = " + nom);
        return dao.findByNomLike('%' + nom + '%');
    }

    @Override
    public Page<Filiale> findPaginated(final String query, final int i, final Integer size)
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
    public Page<Filiale> findPaginated(final String agence, final String code, final String nom, boolean deleted, final int page, final Integer size)
    {
        return dao.searchLike('%' + agence + '%', '%' + code + '%', '%' + nom + '%', deleted, new PageRequest(page, size, Sort.Direction.ASC, "code"));
    }

    @Override
    public void disableEntity(final Filiale entity)
    {
        final Filiale filialeToDisable = dao.findOne(entity.getId());
//        System.out.println("before saving filialeID="+filialeToDisable.getId()+" deleted = "+filialeToDisable.isDeleted());
        if (filialeToDisable.isDeleted() == true) {
            filialeToDisable.setDeleted(false);
        }
        else {
            filialeToDisable.setDeleted(true);
        }
        dao.save(filialeToDisable);
    }

}
