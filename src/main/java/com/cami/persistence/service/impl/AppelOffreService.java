package com.cami.persistence.service.impl;

import com.cami.persistence.dao.IAppelOffreDao;
import com.cami.persistence.dao.IBanqueDao;
import com.cami.persistence.dao.ICautionDao;
import com.cami.persistence.dao.IFilialeDao;
import com.cami.persistence.dao.ILigneAppelDao;
import com.cami.persistence.dao.IMaterielDao;
import com.cami.persistence.dao.IRoleDao;
import com.cami.persistence.dao.ITypeCautionDao;
import com.cami.persistence.model.AppelOffre;
import com.cami.persistence.model.Caution;
import com.cami.persistence.model.LigneAppel;
import com.cami.persistence.model.Role;
import com.cami.persistence.service.IAppelOffreService;
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

@Service("appelOffreService")
@Transactional
public class AppelOffreService
        extends AbstractService<AppelOffre>
        implements IAppelOffreService
{

    @Autowired
    private IAppelOffreDao dao;

    @Autowired
    private IBanqueDao banqueDao;

    @Autowired
    private ILigneAppelDao ligneAppelDao;

    @Autowired
    private ICautionDao cautionDao;

    @Autowired
    private IMaterielDao materielDao;

    @Autowired
    private ITypeCautionDao typeCautionDao;

    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private IFilialeDao filialeDao;

    public AppelOffreService()
    {
        super();
    }

    // API
    @Override
    protected PagingAndSortingRepository<AppelOffre, Long> getDao()
    {
        return dao;
    }

    @Override
    public AppelOffre retrieveByName(final String name)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<AppelOffre> filterByNom(final String nom)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Page<AppelOffre> findPaginated(final String query, final int i,
            final Integer size)
    {
        return super.findPaginated(i, size);
    }

    @Override
    @Transactional
    public AppelOffre create(AppelOffre appelOffre)
    {
        System.out.println("DEBUT SAVE");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final Role userConnected = roleDao.retrieveAUser(auth.getName()); // get the current logged user
        appelOffre.setFiliale(filialeDao.findOne(appelOffre.getFiliale().getId()));
        appelOffre.setDateModification(new Date());
        appelOffre.setUser(userConnected);
        appelOffre.setDeleted(false);
        appelOffre = dao.save(appelOffre);

        System.out.println("Debut Caution - size : " + appelOffre.getCautions().size());
        for (final Caution caution : appelOffre.getCautions()) {
            System.out.println("tour caution 1");
            caution.setAppelOffre(appelOffre);
            caution.setCommercial(roleDao.findOne(caution.getCommercial().getId()));
            caution.setBanque(banqueDao.findOne(caution.getBanque().getId()));
            caution.setTypeCaution(typeCautionDao.findOne(caution.getTypeCaution().getId()));
            cautionDao.save(caution);
        }

        System.out.println("Debut LigneAppel - size : " + appelOffre.getLigneAppels().size());
        for (final LigneAppel ligneAppel : appelOffre.getLigneAppels()) {
            System.out.println("tour ligne 1");
            if (ligneAppel == null) {
                System.out.println("Ligne Appel null");
            }
            else {
                System.out.println("Ligne Appel correct");
                ligneAppel.setAppelOffre(appelOffre);
                ligneAppel.setMateriel(materielDao.findOne(ligneAppel.getMateriel().getId()));
                ligneAppelDao.save(ligneAppel);
            }
        }
        System.out.println("FIN SAVE");
        return appelOffre;
    }

    @Override
    @Transactional
    public AppelOffre update(final AppelOffre appelOffre)
    {

        System.out.println("DEBUT UPDATE");
        AppelOffre editAppelOffre = dao.findOne(appelOffre.getId());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final Role userConnected = roleDao.retrieveAUser(auth.getName()); // get the current logged user

        editAppelOffre.setFiliale(filialeDao.findOne(appelOffre.getFiliale().getId()));
        editAppelOffre.setIntitule(appelOffre.getIntitule());
        editAppelOffre.setEtat("En cours");
        editAppelOffre.setDateModification(new Date());
        editAppelOffre.setDeleted(appelOffre.isDeleted());
        editAppelOffre.setDelaiDeValidite(appelOffre.getDelaiDeValidite());
        editAppelOffre.setNumero(appelOffre.getNumero());
        editAppelOffre.setMaitreDouvrage(appelOffre.getMaitreDouvrage());
        editAppelOffre.setDateDepot(appelOffre.getDateDepot());
        editAppelOffre.setUser(userConnected);
        editAppelOffre = dao.save(editAppelOffre);

        cautionDao.deleteInBatch(cautionDao.filterByAppelOffre(editAppelOffre
                .getId()));

        for (final Caution caution : appelOffre.getCautions()) {
            Caution editCaution = new Caution();

            editCaution.setAppelOffre(editAppelOffre);
            editCaution.setBanque(banqueDao.findOne(caution.getBanque().getId()));
            editCaution.setCommercial(roleDao.findOne(caution.getCommercial().getId()));
            editCaution.setDateDebut(caution.getDateDebut());
            editCaution.setDateFin(caution.getDateFin());
            editCaution.setMontant(caution.getMontant());
            editCaution.setNumero(caution.getNumero());
            editCaution.setReferenceMarche(caution.getReferenceMarche());
            editCaution.setMontantMarche(caution.getMontantMarche());
            editCaution.setTypeCaution(typeCautionDao.findOne(caution.getTypeCaution().getId()));

            System.out.println(editCaution.getReferenceMarche() + "-" + editCaution.getMontantMarche());
            System.out.println("caution saving ... ");
            cautionDao.save(editCaution);
            System.out.println("caution saved ");
        }

        System.out.println("Debut LigneAppel - size : "
                + appelOffre.getLigneAppels().size());
        ligneAppelDao.deleteInBatch(ligneAppelDao
                .filterByAppelOffre(editAppelOffre.getId()));
        for (final LigneAppel ligneAppel : appelOffre.getLigneAppels()) {
            System.out.println("tour ligne 1");
            if (ligneAppel == null) {
                System.out.println("Ligne Appel null");
            }
            else {
                System.out.println("Ligne Appel correct ");
                LigneAppel lg = new LigneAppel();
                lg.setAppelOffre(editAppelOffre);
                System.out.println("Ligne Appel a son appelDoffre");
                lg.setMateriel(materielDao.findOne(ligneAppel.getMateriel().getId()));
                System.out.println("Ligne Appel a son materiel");
                lg.setPrixUnitaire(ligneAppel.getPrixUnitaire());
                lg.setQuantite(ligneAppel.getQuantite());
                System.out.println("Ligne Appel correct= " + lg.getMateriel().getNom() + "-"
                        + lg.getPrixUnitaire() + "-" + lg.getQuantite() + "-"
                        + lg.getAppelOffre().getId());
                ligneAppelDao.save(lg);
                System.out.println("Ligne Appel est enregistré");
            }
        }

        System.out.println("FIN SAVE");
        return editAppelOffre;
    }

    @Override
    public Page<AppelOffre> findPaginated(final Long filialeId,
            final String numero, final String intitule,
            final String maitreDouvrage, Date debutPeriodeDepot, Date finPeriodeDepot, final boolean deleted, final int page, final Integer size)
    {
        if (-1 == filialeId) {
            System.out.println("find-1 et deleted=" + deleted);
            return dao.searchLike('%' + numero + '%', '%' + maitreDouvrage + '%',
                    '%' + intitule + '%', deleted, debutPeriodeDepot, finPeriodeDepot,
                    new PageRequest(page, size, Sort.Direction.ASC, "numero"));
        }
        else {
            System.out.println("find-2 et deleted=" + deleted);
            return dao.searchLikeWithFiliale(filialeId, '%' + numero + '%',
                    '%' + maitreDouvrage + '%', '%' + intitule + '%', deleted, debutPeriodeDepot, finPeriodeDepot,
                    new PageRequest(page, size, Sort.Direction.ASC, "numero"));
        }
    }

    @Override
    @Transactional
    public AppelOffre updateFiles(AppelOffre appelOffre)
    {
        System.out.println("updateFile");
        AppelOffre editAppelOffre = dao.findOne(appelOffre.getId());
        List<String> files = editAppelOffre.getFiles();
        for (String file : appelOffre.getFiles()) {
            if (!files.contains(file)) {
                System.out.println("File not inside");
                editAppelOffre.addFile(file);
            }
            else {
                System.out.println("File not inside");
            }

        }

        return dao.save(editAppelOffre);

    }

    @Override

    public AppelOffre deleteFiles(Long idAppelOffre, String file)
    {
        System.out.println("removeFile");
        AppelOffre editAppelOffre = dao.findOne(idAppelOffre);
        List<String> files = editAppelOffre.getFiles();
        List<String> savedfiles = new ArrayList<>();
        for (String fi : files) {
            if (!fi.contains(file)) {

                savedfiles.add(fi);
            }

        }
        editAppelOffre.setFiles(savedfiles);
        return dao.save(editAppelOffre);
    }

    public void disableEntity(AppelOffre entity)
    {
        System.out.println("DEBUT UPDATE SIMPLE service  where deleted=" + entity.isDeleted());
        AppelOffre editAppelOffre = dao.findOne(entity.getId());
        System.out.println("after findOne method service  where deleted=" + entity.isDeleted());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final Role userConnected = roleDao.retrieveAUser(auth.getName()); // get the current logged user
        editAppelOffre.setDateModification(new Date());
        editAppelOffre.setDeleted(entity.isDeleted());
        editAppelOffre.setUser(userConnected);
        dao.save(editAppelOffre);
        System.out.println("update simple appeloffre finished and delketed=" + editAppelOffre.isDeleted());

    }

}
