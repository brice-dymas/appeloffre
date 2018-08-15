package com.cami.persistence.service;

import com.cami.persistence.IOperations;
import com.cami.persistence.model.TypeMateriel;
import java.util.List;
import org.springframework.data.domain.Page;

public interface ITypeMaterielService extends IOperations<TypeMateriel>
{

    TypeMateriel retrieveByName(String name);

    public List<TypeMateriel> filterByNom(String nom);

    public Page<TypeMateriel> findPaginated(String query, int i, Integer size);

    public Page<TypeMateriel> findPaginated(String query_code, String query_nom, boolean deleted, int i, Integer size);

}
