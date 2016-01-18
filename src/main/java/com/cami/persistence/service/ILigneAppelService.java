package com.cami.persistence.service;

import com.cami.persistence.IOperations;
import com.cami.persistence.model.LigneAppel;
import java.util.List;

public interface ILigneAppelService extends IOperations<LigneAppel> {

    public List<LigneAppel> filterByAppelOffre(Long appelOffreId);
    

}
