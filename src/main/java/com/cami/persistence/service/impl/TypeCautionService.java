package com.cami.persistence.service.impl;

import com.cami.persistence.dao.ITypeCautionDao;
import com.cami.persistence.model.TypeCaution;
import com.cami.persistence.service.ITypeCautionService;
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

@Service("typeCautionService")
@Transactional
public class TypeCautionService
        extends AbstractService<TypeCaution>
        implements ITypeCautionService
{

    @Autowired
    private ITypeCautionDao dao;

    public TypeCautionService()
    {
        super();
    }

    // API
    @Override
    protected PagingAndSortingRepository<TypeCaution, Long> getDao()
    {
        return dao;
    }

    // overridden to be secured
    @Override
//    @Transactional(readOnly = true)
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<TypeCaution> findAll()
    {
        return Lists.newArrayList(getDao().findAll());
    }

    @Override
    public TypeCaution update(final TypeCaution entity)
    {
        System.out.println("update methos reached ");
        System.out.println("Setting up entity to update which id=" + entity.getId());
        TypeCaution toUpdate = dao.findOne(entity.getId());
        System.out.println("entity has it dao");
        toUpdate.setCode(entity.getCode());
        System.out.println("entity has it code");
        toUpdate.setNom(entity.getNom());
        System.out.println("entity has it nom");
        toUpdate.setPourcentage(entity.getPourcentage());
        System.out.println("entity has it percentage");
        System.out.println("entity final is " + toUpdate.getCode() + "-" + toUpdate.getNom() + "-" + toUpdate.getPourcentage());
        System.out.println("entity to update hab been set up, now saving it ...");
        return dao.save(toUpdate);
    }

    @Override
    public List<TypeCaution> filterByNom(String nom)
    {
        System.out.println("ici = " + nom);
        return dao.findByNomLike('%' + nom + '%');
    }

    @Override
    public Page<TypeCaution> findPaginated(String query, int i, Integer size)
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
    public Page<TypeCaution> findPaginated(String query_code, String query_nom, boolean deleted, int i, Integer size)
    {
        return dao.searchLike('%' + query_code + '%', '%' + query_nom + '%', deleted, new PageRequest(i, size, Sort.Direction.ASC, "code"));
    }

    @Override
    public void disableEntity(final TypeCaution entity)
    {
        final TypeCaution tcToDisable = dao.findOne(entity.getId());
        if (tcToDisable.isDeleted() == true) {
            tcToDisable.setDeleted(false);
        }
        else {
            tcToDisable.setDeleted(true);
        }
        dao.save(tcToDisable);
    }

}
