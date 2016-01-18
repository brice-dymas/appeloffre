
package com.cami.web.form;

import com.cami.persistence.model.Materiel;
import com.cami.persistence.model.TypeMateriel;
import org.springframework.util.AutoPopulatingList;


public class MaterielForm {
    
    Materiel materiel;
    
    AutoPopulatingList<TypeMateriel> typeMateriels;
    

    public MaterielForm() {
    }

    public Materiel getMateriel() {
        return materiel;
    }

    public void setMateriel(Materiel materiel) {
        this.materiel = materiel;
    }

    public AutoPopulatingList<TypeMateriel> getTypeMateriels() {
        return typeMateriels;
    }

    public void setTypeMateriels(AutoPopulatingList<TypeMateriel> typeMateriels) {
        this.typeMateriels = typeMateriels;
    }

    
    
    
}
