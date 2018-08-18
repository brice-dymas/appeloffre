package com.cami.persistence.service;

import com.cami.persistence.IOperations;
import com.cami.persistence.model.CautionDouane;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

public interface ICautionDouaneService
        extends IOperations<CautionDouane> {

    public Page<CautionDouane> findPaginated(String query, int i, Integer size);

    public Page<CautionDouane> findPaginated(final String dateDebut,
            final String dateFin, final String banque, final int page,
            final Integer size);

    public Page<CautionDouane> filterByBank(final String banque, final int page,
            final Integer size);

    public Page<CautionDouane> filter(final long banque, final int montant,
            final Date debutEcheance, final Date finEcheance, final int page,
            final Integer size);

    List<Object[]> totalCautionParBanqueParMois(int year);

    @Transactional
    public CautionDouane updateFiles(CautionDouane cautionDouane);

    public CautionDouane deleteFiles(Long idCautionDouane, String file);
}
