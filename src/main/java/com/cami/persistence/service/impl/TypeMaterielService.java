package com.cami.persistence.service.impl;

import com.cami.persistence.dao.ITypeMaterielDao;
import com.cami.persistence.model.TypeMateriel;
import com.cami.persistence.service.ITypeMaterielService;
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

@Service("typeMaterielService")
@Transactional
public class TypeMaterielService extends AbstractService<TypeMateriel> implements ITypeMaterielService
{

    @Autowired
    private ITypeMaterielDao dao;

    public TypeMaterielService()
    {
        super();
    }

    // API
    @Override
    protected PagingAndSortingRepository<TypeMateriel, Long> getDao()
    {
        return dao;
    }

    // custom methods
    @Override
    public TypeMateriel retrieveByName(final String name)
    {
        return dao.retrieveByName(name);
    }

    // overridden to be secured
    @Override
    //    @Transactional(readOnly = true)
    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<TypeMateriel> findAll()
    {
        return Lists.newArrayList(getDao().findAll());
    }

    @Override
    public TypeMateriel update(final TypeMateriel entity)
    {
        System.out.println("in the update method for entityID "
                + entity.getId());
        final TypeMateriel temporalEntiy = dao.findOne(entity.getId());
        System.out.println("materiel retrieved = " + temporalEntiy);
        temporalEntiy.setCode(entity.getCode());
        temporalEntiy.setNom(entity.getNom());
        System.out.println("field ready to be updated");
        // return getDao().save(temporalEntiy);
        return dao.save(temporalEntiy);

    }

    @Override
    public List<TypeMateriel> filterByNom(final String nom)
    {
        System.out.println("ici = " + nom);
        return null;
    }

    @Override
    public Page<TypeMateriel> findPaginated(final String query, final int i, final Integer size)
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
    public Page<TypeMateriel> findPaginated(final String query_code, final String query_nom, boolean deleted, final int i, final Integer size)
    {

        return dao.searchLike('%' + query_code + '%', '%' + query_nom + '%', deleted, new PageRequest(i, size, Sort.Direction.ASC, "code"));

    }

    @Override
    public void disableEntity(final TypeMateriel entity)
    {
        final TypeMateriel tmToDisable = dao.findOne(entity.getId());
        if (tmToDisable.isDeleted() == true) {
            tmToDisable.setDeleted(false);
        }
        else {
            tmToDisable.setDeleted(true);
        }
        dao.save(tmToDisable);
    }

}
