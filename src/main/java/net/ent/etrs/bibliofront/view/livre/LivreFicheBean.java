package net.ent.etrs.bibliofront.view.livre;


import lombok.Getter;
import lombok.Setter;
import net.ent.etrs.bibliofront.model.dtos.DtoLivre;
import net.ent.etrs.bibliofront.model.dtos.converters.DtoAuteurConverter;
import net.ent.etrs.bibliofront.model.dtos.converters.DtoIllustrateurConverter;
import net.ent.etrs.bibliofront.model.dtos.converters.DtoLivreConverter;
import net.ent.etrs.bibliofront.model.entities.Auteur;
import net.ent.etrs.bibliofront.model.entities.Illustrateur;
import net.ent.etrs.bibliofront.model.entities.Livre;
import net.ent.etrs.bibliofront.model.entities.references.Genre;
import net.ent.etrs.bibliofront.model.facades.FacadeClientAuteur;
import net.ent.etrs.bibliofront.model.facades.FacadeClientIllustrateur;
import net.ent.etrs.bibliofront.model.facades.FacadeClientLivre;
import net.ent.etrs.bibliofront.model.facades.exceptions.ClientRestException;
import net.ent.etrs.bibliofront.utils.JsfUtils;
import org.primefaces.model.DualListModel;
import org.primefaces.model.file.UploadedFile;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.spi.CDI;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class LivreFicheBean implements Serializable {
    
    @Inject
    private FacadeClientAuteur facadeClientAuteur;
    
    @Inject
    private FacadeClientIllustrateur facadeClientIllustrateur;
    
    @Inject
    private FacadeClientLivre facadeClientLivre;
    
    @Inject
    @Getter
    @Setter
    private Livre livre;
    
    @Getter
    @Setter
    private UploadedFile file;
    
    @Getter
    private final List<Genre> genreList = Arrays.asList(Genre.values());
    
    @Getter
    private List<Auteur> auteurList;
    
    @Getter
    @Setter
    private DualListModel<Illustrateur> illustrateurDualListModel;
    
    @PostConstruct
    public void init() {
        try {
            this.auteurList = this.facadeClientAuteur.findAll()
                    .stream().map(DtoAuteurConverter::fromDto)
                    .collect(Collectors.toList());
        } catch (ClientRestException e) {
            JsfUtils.sendMessage(null, FacesMessage.SEVERITY_ERROR, e.getMessage());
        }
    
        List<Illustrateur> illustrateurs = this.facadeClientIllustrateur.findAll()
                    .stream().map(DtoIllustrateurConverter::fromDto)
                    .collect(Collectors.toList());


            Livre livreModif = (Livre) FacesContext.getCurrentInstance().getExternalContext().getFlash().get(LivreListeBean.FLASH_LIVRE);
            if (Objects.nonNull(livreModif)) {
                this.livre = livreModif;
                this.livre.setIllustrateurs(
                        this.facadeClientIllustrateur.findByLivre(this.livre.getId())
                                .stream().map(DtoIllustrateurConverter::fromDto)
                                .collect(Collectors.toSet())
                                           );
            }
            illustrateurs.removeAll(this.livre.getIllustrateurs());
            this.illustrateurDualListModel = new DualListModel(illustrateurs, new ArrayList(this.livre.getIllustrateurs()));
    }
    
    public void valider() {
        if (this.file != null && this.file.getFileName() != null) {
            this.livre.setImage(this.file.getContent());
        }
        this.livre.setIllustrateurs(new HashSet<>(this.illustrateurDualListModel.getTarget()));
        if (this.livre.getId() != null) {
            this.facadeClientLivre.update(DtoLivreConverter.fromEntity(this.livre));
        } else {
            this.facadeClientLivre.create(DtoLivreConverter.fromEntity(this.livre));
        }
        this.livre = CDI.current().select(Livre.class).get();
        JsfUtils.sendMessage(null, FacesMessage.SEVERITY_INFO, "Le livre a bien été créé !");
    }
}
