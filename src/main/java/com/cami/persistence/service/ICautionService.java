package com.cami.persistence.service;

import com.cami.persistence.IOperations;
import com.cami.persistence.model.AppelOffre;
import com.cami.persistence.model.Caution;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;

public interface ICautionService
        extends IOperations<Caution>
{

    public List<Caution> filterByAppelOffre(Long appelOffreId);

    public Page<Caution> findPaginated(String query, int i, Integer size);

    public Page<Caution> findPaginated(final String dateDebut,
            final String dateFin, final String banque, final int page,
            final Integer size);

    public Page<Caution> filterByBank(final String banque, final int page,
            final Integer size);
    
    public Page<Caution> filter(final long banque,final long typeCaution, final Date debutEcheance, final Date finEcheance, final int page,
            final Integer size);

    public List<AppelOffre> getThemComplete();
    
    List<Object[]> totalCautionParBanqueParMois(int year);

	// public Page<Caution> findPaginated(Date dateDebut, Date dateFin,
    // String banque, int page, Integer size);
}
