package com.cami.persistence.service;

import com.cami.persistence.IOperations;
import com.cami.persistence.model.AppelOffre;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

public interface IAppelOffreService extends IOperations<AppelOffre>
{

    AppelOffre retrieveByName(String name);

    public List<AppelOffre> filterByNom(String nom);

    public Page<AppelOffre> findPaginated(String query, int page, Integer size);

    public Page<AppelOffre> findPaginated(Long filialeId,
            String numero, String intitule, String maitreDouvrage, Date debutPeriodeDepot,
            Date finPeriodeDepot, final boolean deleted, int page, Integer size);

    @Transactional
    public AppelOffre updateFiles(AppelOffre appelOffre);

    public AppelOffre deleteFiles(Long idAppelOffre, String file);

}
