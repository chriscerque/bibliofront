package net.ent.etrs.bibliofront.view.livre;

import lombok.Getter;
import net.ent.etrs.bibliofront.model.dtos.converters.DtoLivreConverter;
import net.ent.etrs.bibliofront.model.entities.Livre;
import net.ent.etrs.bibliofront.model.entities.references.Genre;
import net.ent.etrs.bibliofront.model.facades.FacadeClientLivre;
import net.ent.etrs.bibliofront.utils.JsfUtils;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class LivreListeBean implements Serializable {
    
    public static final String FLASH_LIVRE = "FLASH_LIVRE";
    
    @Inject
    private FacadeClientLivre facadeClientLivre;
    
    @Inject
    @Getter
    private LazyDataModelLivre livreList;
    
    @PostConstruct
    public void init() {
    }
    
    public void modifier(Livre livre) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put(LivreListeBean.FLASH_LIVRE, livre);
    }
    
    public void supprimer(Livre livre) {
        this.facadeClientLivre.delete(livre.getId());
        this.init();
        JsfUtils.sendMessage(null, FacesMessage.SEVERITY_INFO, "Le livre a bien été supprimé !");
    }
    
    public List<Genre> getGenreList() {
        return Arrays.asList(Genre.values());
    }
}
