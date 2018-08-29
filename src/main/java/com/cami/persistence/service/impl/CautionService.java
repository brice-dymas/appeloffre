/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.service.impl;

import com.cami.persistence.dao.IAppelOffreDao;
import com.cami.persistence.dao.ICautionDao;
import com.cami.persistence.dao.ILigneAppelDao;
import com.cami.persistence.dao.IRoleDao;
import com.cami.persistence.model.AppelOffre;
import com.cami.persistence.model.Caution;
import com.cami.persistence.model.Role;
import com.cami.persistence.service.ICautionService;
import com.cami.persistence.service.common.AbstractService;
import java.util.ArrayList;
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
@Service("cautionService")
@Transactional
public class CautionService
        extends AbstractService<Caution>
        implements
        ICautionService {

    @Autowired
    private ICautionDao dao;

    @Autowired
    private IAppelOffreDao offreDao;

    @Autowired
    private ILigneAppelDao ligneAppelDao;

    @Autowired
    IRoleDao roleDao;

    @Override
    protected PagingAndSortingRepository<Caution, Long> getDao() {
        return dao;
    }

    // This method returns a list of appeloffre complete with their cautions and ligneAppel
//    this will be used to generate the final report for excel and pdf format
    @Override
    public List<AppelOffre> getThemComplete() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final Role userConnected = roleDao.retrieveAUser(auth.getName()); // get the current logged user
        List<Caution> cautions = new ArrayList<>();
        List<AppelOffre> appelOffres = offreDao.findAll();
        if (userConnected.getRole().equals("ROLE_COMMERCIAL")) {
            for (AppelOffre appelOffre : appelOffres) {
                appelOffre.setCautions(dao.filterByAppelOffreAndUser(appelOffre.getId(), userConnected.getId()));
                appelOffre.setLigneAppels(ligneAppelDao.filterByAppelOffre(appelOffre.getId()));
            }
            return appelOffres;
        } else {
            for (AppelOffre appelOffre : appelOffres) {
                appelOffre.setCautions(dao.filterByAppelOffre(appelOffre.getId()));
                appelOffre.setLigneAppels(ligneAppelDao.filterByAppelOffre(appelOffre.getId()));
            }
            return appelOffres;
        }
    }

    @Override
    public List<Caution> filterByAppelOffre(final Long appelOffreId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final Role userConnected = roleDao.retrieveAUser(auth.getName()); // get the current logged user
        if (userConnected.getRole().equals("ROLE_COMMERCIAL")) {
            return dao.filterByAppelOffreAndUser(appelOffreId, userConnected.getId());
        } else {
            return dao.filterByAppelOffre(appelOffreId);
        }
    }

    @Override
    public Page<Caution> findPaginated(final String query, final int i,
            final Integer size) {
        return super.findPaginated(i, size);
    }

    @Override
    public Page<Caution> findPaginated(final String dateDebut,
            final String dateFin, final String banque, final int page,
            final Integer size) {
        return dao.filterByPeriod('%' + dateDebut + '%', '%' + dateFin + '%',
                '%' + banque + '%', new PageRequest(page, size,
                        Sort.Direction.ASC, "numero"));
    }

    @Override
    public Page<Caution> filterByBank(final String banque, final int page,
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
    public Caution findOne(final long id) {
        return dao.findOne(id);
    }

    @Override
    public List<Caution> findAll() {
        return dao.findAll();
    }

    @Override
    public List<Object[]> totalCautionParBanqueParMois(int year) {
        System.out.println("HomeController Service " + year);
        return dao.totalCautionParBanqueParMois(year);
    }

    @Override
    public void disableEntity(final Caution entity) {
        final Caution cautionToDisable = dao.findOne(entity.getId());
        if (cautionToDisable.isDeleted() == true) {
            cautionToDisable.setDeleted(false);
        } else {
            cautionToDisable.setDeleted(false);
        }
        dao.save(cautionToDisable);
    }

    @Override
    public Page<Caution> filter(long banque, long typeCaution, Date debutEcheance, Date finEcheance, int page, Integer size) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final Role userConnected = roleDao.retrieveAUser(auth.getName()); // get the current logged user
        if (userConnected.getRole().equals("ROLE_COMMERCIAL")) {
            System.out.println("SEARCHING CAUTIONS FOR ROLE COMMERCIAL");
            if (banque == -1 && typeCaution > -1) {
                System.out.println("filtre sur typeCaution");
                return dao.filterByTypeCautionAndUser(typeCaution, debutEcheance, finEcheance, userConnected.getId(), new PageRequest(page, size,
                        Sort.Direction.DESC, "dateFin"));

            }

            if (banque > 1 && typeCaution == -1) {
                System.out.println("filtre sur banque");
                return dao.filterByBanqueAndUser(banque, debutEcheance, finEcheance, userConnected.getId(), new PageRequest(page, size,
                        Sort.Direction.DESC, "dateFin"));
            }

            if (banque > -1 && typeCaution > -1) {
                System.out.println("filtre sur typeCaution et banque");
                return dao.filterByTypeCautionBanqueAndUser(banque, typeCaution, debutEcheance, finEcheance, userConnected.getId(), new PageRequest(page, size,
                        Sort.Direction.DESC, "dateFin"));
            }

            if (banque == -1 && typeCaution == -1) {
                System.out.println("filtre sur rien");
                return dao.filterByUser(debutEcheance, finEcheance, userConnected.getId(), new PageRequest(page, size,
                        Sort.Direction.DESC, "dateFin"));

            }
        } else {
            System.out.println("SEARCHING CAUTIONS FOR NOT ROLE COMMERCIAL");
            if (banque == -1 && typeCaution > -1) {
                System.out.println("filtre sur typeCaution");
                return dao.filterByTypeCaution(typeCaution, debutEcheance, finEcheance, new PageRequest(page, size,
                        Sort.Direction.DESC, "dateFin"));
            }

            if (banque > -1 && typeCaution == -1) {
                System.out.println("filtre sur banque");
                return dao.filterByBanque(banque, debutEcheance, finEcheance, new PageRequest(page, size,
                        Sort.Direction.DESC, "dateFin"));
            }

            if (banque > -1 && typeCaution > -1) {
                System.out.println("filtre sur typeCaution et banque");
                return dao.filterByTypeCautionAndBanque(banque, typeCaution, debutEcheance, finEcheance, new PageRequest(page, size,
                        Sort.Direction.DESC, "dateFin"));
            }

            if (banque == -1 && typeCaution == -1) {
                System.out.println("filtre sur rien");
                return dao.filter(debutEcheance, finEcheance, new PageRequest(page, size,
                        Sort.Direction.DESC, "dateFin"));
            }

        }
        return null;

    }
}
