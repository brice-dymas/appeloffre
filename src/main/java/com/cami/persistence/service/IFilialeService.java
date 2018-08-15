package com.cami.persistence.service;

import com.cami.persistence.IOperations;
import com.cami.persistence.model.Filiale;
import java.util.List;
import org.springframework.data.domain.Page;

public interface IFilialeService extends IOperations<Filiale>
{

    public List<Filiale> filterByNom(String nom);

    public Page<Filiale> findPaginated(String query, int i, Integer size);

    public Page<Filiale> findPaginated(String agence, String code, String nom, boolean deleted, int page, Integer size);

}
