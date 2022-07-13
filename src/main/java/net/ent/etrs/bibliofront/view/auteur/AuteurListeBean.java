package net.ent.etrs.bibliofront.view.auteur;

import lombok.Getter;
import net.ent.etrs.bibliofront.model.dtos.converters.DtoAuteurConverter;
import net.ent.etrs.bibliofront.model.entities.Auteur;
import net.ent.etrs.bibliofront.model.facades.FacadeClientAuteur;
import net.ent.etrs.bibliofront.model.facades.exceptions.ClientRestException;
import net.ent.etrs.bibliofront.utils.JsfUtils;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class AuteurListeBean implements Serializable {
    
    public static final String FLASH_AUTEUR = "FLASH_AUTEUR";
    
    @Inject
    private FacadeClientAuteur facadeClientAuteur;
    
    @Inject
    @Getter
    private LazyDataModelAuteur auteurList;
    
    @PostConstruct
    public void init() {
    }
    
    public void modifier(Auteur auteur) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put(AuteurListeBean.FLASH_AUTEUR, auteur);
    }
    
    public void supprimer(Auteur auteur) {
        try {
            this.facadeClientAuteur.delete(auteur.getId());
        } catch (ClientRestException e) {
            JsfUtils.sendMessage(null, FacesMessage.SEVERITY_ERROR, e.getMessage());
        }
        this.init();
        JsfUtils.sendMessage(null, FacesMessage.SEVERITY_INFO, "L'auteur a bien été supprimé !");
    }
}
