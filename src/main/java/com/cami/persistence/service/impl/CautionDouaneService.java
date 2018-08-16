/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.service.impl;

import com.cami.persistence.dao.IBanqueDao;
import com.cami.persistence.dao.ICautionDouaneDao;
import com.cami.persistence.dao.IRoleDao;
import com.cami.persistence.model.CautionDouane;
import com.cami.persistence.model.Role;
import com.cami.persistence.service.ICautionDouaneService;
import com.cami.persistence.service.common.AbstractService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author gervais
 */
@Service("cautionDouaneService")
@Transactional
public class CautionDouaneService
        extends AbstractService<CautionDouane>
        implements
        ICautionDouaneService {

    @Autowired
    private ICautionDouaneDao dao;

    @Autowired
    private IBanqueDao banqueDao;

    @Autowired
    IRoleDao roleDao;

    @Override
    protected PagingAndSortingRepository<CautionDouane, Long> getDao() {
        return dao;
    }

    @Override
    public Page<CautionDouane> findPaginated(final String query, final int i,
            final Integer size) {
        return super.findPaginated(i, size);
    }

    @Override
    public Page<CautionDouane> findPaginated(final String dateDebut,
            final String dateFin, final String banque, final int page,
            final Integer size) {
        return dao.filterByPeriod('%' + dateDebut + '%', '%' + dateFin + '%',
                '%' + banque + '%', new PageRequest(page, size,
                        Sort.Direction.ASC, "numero"));
    }

    @Override
    public Page<CautionDouane> filterByBank(final String banque, final int page,
            final Integer size) {
        return null;
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        final Role userConnected = roleDao.retrieveAUser(auth.getName()); // get the current logged user
//        if (userConnected.getRole().equals("ROLE_COMMERCIAL")) {
//            return dao.filterByBankAndUser('%' + banque + '%', userConnected.getId(), new PageRequest(page, size,
//                    Sort.Direction.DESC, "dateFin"));
//        }
//        else {
//            return dao.filterByBank('%' + banque + '%', new PageRequest(page, size,
//                    Sort.Direction.DESC, "dateFin"));
//        }
    }

    @Override
    public CautionDouane findOne(final long id) {
        return dao.findOne(id);
    }

    @Override
    public List<CautionDouane> findAll() {
        return dao.findAll();
    }

    @Override
    @Transactional
    public CautionDouane create(CautionDouane cautionDouane) {
        System.out.println("AUCUNE ERREUR DETECTEE DANS createAction COUCHE SERVICE");
        cautionDouane.setBanque(banqueDao.findOne(cautionDouane.getBanque().getId()));
        System.out.println("cautionDouane = " + cautionDouane);
        return dao.save(cautionDouane); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object[]> totalCautionParBanqueParMois(int year) {
        System.out.println("HomeController Service " + year);
        return dao.totalCautionParBanqueParMois(year);
    }

    @Override
    public void disableEntity(final CautionDouane entity) {
        final CautionDouane cautionToDisable = dao.findOne(entity.getId());
        if (cautionToDisable.isDeleted() == true) {
            cautionToDisable.setDeleted(false);
        } else {
            cautionToDisable.setDeleted(false);
        }
        dao.save(cautionToDisable);
    }

    @Override
    public Page<CautionDouane> filter(long banque, int montant, Date debutEcheance, Date finEcheance, int page, Integer size) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final Role userConnected = roleDao.retrieveAUser(auth.getName()); // get the current logged user
        if (userConnected.getRole().equals("ROLE_COMMERCIAL")) {
            if (banque == -1 && montant > -1) {
                return dao.filterByMontant(montant, debutEcheance, finEcheance, new PageRequest(page, size,
                        Sort.Direction.DESC, "dateFin"));

            }

            if (banque > -1 && montant == -1) {
                return dao.filterByBanque(banque, debutEcheance, finEcheance, new PageRequest(page, size,
                        Sort.Direction.DESC, "dateFin"));
            }

            if (banque > -1 && montant > -1) {

                return dao.filterByMontantAndBanque(banque, montant, debutEcheance, finEcheance, new PageRequest(page, size,
                        Sort.Direction.DESC, "dateFin"));
            }

            if (banque == -1 && montant == -1) {
                return dao.filter(debutEcheance, finEcheance, new PageRequest(page, size,
                        Sort.Direction.DESC, "dateFin"));

            }
        } else {
            if (banque == -1 && montant > -1) {
                return dao.filterByMontant(montant, debutEcheance, finEcheance, new PageRequest(page, size,
                        Sort.Direction.DESC, "dateFin"));

            }

            if (banque > -1 && montant == -1) {
                return dao.filterByBanque(banque, debutEcheance, finEcheance, new PageRequest(page, size,
                        Sort.Direction.DESC, "dateFin"));
            }

            if (banque > -1 && montant > -1) {

                return dao.filterByMontantAndBanque(banque, montant, debutEcheance, finEcheance, new PageRequest(page, size,
                        Sort.Direction.DESC, "dateFin"));
            }

            if (banque == -1 && montant == -1) {
                return dao.filter(debutEcheance, finEcheance, new PageRequest(page, size,
                        Sort.Direction.DESC, "dateFin"));

            }
        }
        return null;

    }
}
