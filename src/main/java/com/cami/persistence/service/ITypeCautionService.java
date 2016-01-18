package com.cami.persistence.service;

import com.cami.persistence.IOperations;
import com.cami.persistence.model.TypeCaution;
import java.util.List;
import org.springframework.data.domain.Page;

public interface ITypeCautionService extends IOperations<TypeCaution>
{

    public List<TypeCaution> filterByNom(String nom);

    public Page<TypeCaution> findPaginated(String query, int i, Integer size);

    public Page<TypeCaution> findPaginated(String query_code, String query_nom, boolean deleted, int i, Integer size);
}
