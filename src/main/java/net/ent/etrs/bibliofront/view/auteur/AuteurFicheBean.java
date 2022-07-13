package net.ent.etrs.bibliofront.view.auteur;


import lombok.Getter;
import lombok.Setter;
import net.ent.etrs.bibliofront.model.dtos.converters.DtoAuteurConverter;
import net.ent.etrs.bibliofront.model.entities.Auteur;
import net.ent.etrs.bibliofront.model.facades.FacadeClientAuteur;
import net.ent.etrs.bibliofront.model.facades.exceptions.ClientRestException;
import net.ent.etrs.bibliofront.utils.JsfUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.spi.CDI;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Objects;

@Named
@ViewScoped
public class AuteurFicheBean implements Serializable {
    
    @Inject
    @Getter
    @Setter
    private Auteur auteur;
    
    @Inject
    private FacadeClientAuteur facadeClientAuteur;
    
    @PostConstruct
    public void init() {
        Auteur auteurModif = (Auteur) FacesContext.getCurrentInstance().getExternalContext().getFlash().get(AuteurListeBean.FLASH_AUTEUR);
        if (Objects.nonNull(auteurModif)) {
            this.auteur = auteurModif;
        }
    }
    
    public void valider() {
            try {
                if (this.auteur.getId() != null) {
                this.facadeClientAuteur.update(DtoAuteurConverter.fromEntity(this.auteur));
                } else {
                    this.facadeClientAuteur.create(DtoAuteurConverter.fromEntity(this.auteur));
                }
                this.auteur = CDI.current().select(Auteur.class).get();
                JsfUtils.sendMessage(null, FacesMessage.SEVERITY_INFO, "Le livre a bien été créé !");
            } catch (ClientRestException e) {
                JsfUtils.sendMessage(null, FacesMessage.SEVERITY_ERROR, e.getMessage());
            }
    }
}
