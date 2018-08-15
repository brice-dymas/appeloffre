/**
 *
 * @author fabrice
 */
package com.cami.persistence.service;

import com.cami.persistence.IOperations;
import com.cami.persistence.model.Materiel;
import org.springframework.data.domain.Page;

public interface IMaterielService extends IOperations<Materiel>
{

    Materiel retrieveByName(String name);

    public Page<Materiel> findPaginated(String query, int i, Integer size);

    public Page<Materiel> findPaginated(Long typeMaterielId, String code, String nom, boolean deleted, int page, Integer size);

}
