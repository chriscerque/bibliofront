package net.ent.etrs.bibliofront.model.facades;

import lombok.Getter;
import net.ent.etrs.bibliofront.model.dtos.DtoIllustrateur;
import net.ent.etrs.bibliofront.view.SessionBean;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FacadeClientIllustrateur implements Serializable {

    private static final String REQUEST_URL_ILLUSTRATEUR = "http://localhost:8080/biblioback-1.0-SNAPSHOT/api/illustrateurs/";
    
    @Inject
    private SessionBean sessionBean;

    @Getter
    private Client client = ClientBuilder.newClient();
    
    public List<DtoIllustrateur> findAll() {
        return this.client.target(REQUEST_URL_ILLUSTRATEUR).request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken()).get(new GenericType<List<DtoIllustrateur>>(){});
    }
    
    public List<DtoIllustrateur> findByLivre(Long idLivre) {
        return this.client.target(REQUEST_URL_ILLUSTRATEUR).queryParam("livre", String.valueOf(idLivre)).request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken()).get(new GenericType<List<DtoIllustrateur>>(){});
    }
    
    public Optional<DtoIllustrateur> findById(Long id) {
        DtoIllustrateur illustrateur = this.client.target(REQUEST_URL_ILLUSTRATEUR).path(String.valueOf(id)).request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken()).get(DtoIllustrateur.class);
        if (Objects.nonNull(illustrateur)) {
            return Optional.of(illustrateur);
        }
        return Optional.empty();
    }
    
    public DtoIllustrateur create(DtoIllustrateur dtoIllustrateur) {
        return this.client.target(REQUEST_URL_ILLUSTRATEUR).request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken()).post(Entity.json(dtoIllustrateur)).readEntity(DtoIllustrateur.class);
    }
    
    public DtoIllustrateur update(DtoIllustrateur dtoIllustrateur) {
        return this.client.target(REQUEST_URL_ILLUSTRATEUR).path(String.valueOf(dtoIllustrateur.getId())).request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken()).put(Entity.json(dtoIllustrateur)).readEntity(DtoIllustrateur.class);
    }
    
    public void delete(Long id) {
        this.client.target(REQUEST_URL_ILLUSTRATEUR).path(String.valueOf(id)).request()
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken()).delete();
    }
}
