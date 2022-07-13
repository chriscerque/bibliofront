package net.ent.etrs.bibliofront.model.facades;

import lombok.Getter;
import net.ent.etrs.bibliofront.model.dtos.DtoAuteur;
import net.ent.etrs.bibliofront.model.facades.exceptions.ClientRestException;
import net.ent.etrs.bibliofront.utils.LazyDataTableUtil;
import net.ent.etrs.bibliofront.view.SessionBean;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class FacadeClientAuteur implements Serializable {

    private static final String REQUEST_URL_AUTEUR = "http://localhost:8080/biblioback-1.0-SNAPSHOT/api/auteurs/";
    
    @Inject
    private SessionBean sessionBean;

    @Getter
    private Client client = ClientBuilder.newClient();
    
    public List<DtoAuteur> findAll() throws ClientRestException {
        try {
            return this.client.target(REQUEST_URL_AUTEUR)
                    .queryParam("first", 1)
                    .queryParam("pageSize", 10000)
                    .request(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken())
                    .get(new GenericType<List<DtoAuteur>>() {});
        } catch (ServerErrorException e) {
            throw new ClientRestException("Erreur lors du chargement des auteurs", e);
        }
    }
    
    public List<DtoAuteur> findAll(int first, int pageSize, Map<String, SortMeta> sortedBy, Map<String, FilterMeta> filterBy) throws ClientRestException {
        String sorted = LazyDataTableUtil.sortedByMapToStr(sortedBy);
        String filter = LazyDataTableUtil.filterByMapToStr(filterBy);
    
        try {
            return this.client.target(REQUEST_URL_AUTEUR)
                    .queryParam("first", first)
                    .queryParam("pageSize", pageSize)
                    .queryParam("sortedBy", sorted)
                    .queryParam("filterBy", filter)
                    .request(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken())
                    .get(new GenericType<List<DtoAuteur>>() {
                    });
        } catch (ServerErrorException e) {
            throw new ClientRestException("Erreur lors du chargement des auteurs", e);
        }
    }
    
    
    
    public Optional<DtoAuteur> findById(Long id) {
        try {
            DtoAuteur auteur = this.client.target(REQUEST_URL_AUTEUR).path(String.valueOf(id)).request(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken()).get(DtoAuteur.class);
            return Optional.of(auteur);
        } catch (NotFoundException e) {
            return Optional.empty();
        }
    }
    
    public DtoAuteur create(DtoAuteur dtoAuteur) throws ClientRestException {
        Response resp = this.client.target(REQUEST_URL_AUTEUR).request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken()).post(Entity.json(dtoAuteur));
        
        if (resp.getStatus() == Response.Status.NOT_ACCEPTABLE.getStatusCode()) {
            Map<String, List<String>> map = resp.readEntity(new GenericType<Map<String, List<String>>>(){});
            StringBuilder sb = new StringBuilder();
            for (String key: map.keySet()){
                sb.append(key).append(": ");
                for (String e : map.get(key)){
                    sb.append(e).append(", ");
                }
            }
            throw new ClientRestException(sb.toString());
        }
        
        if (resp.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            throw new ClientRestException("Erreur lors de la création de l'auteur");
        }
        
        return resp.readEntity(DtoAuteur.class);
    }
    
    public DtoAuteur update(DtoAuteur dtoAuteur) throws ClientRestException {
        Response resp = this.client.target(REQUEST_URL_AUTEUR).path(String.valueOf(dtoAuteur.getId())).request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken()).put(Entity.json(dtoAuteur));
    
        if (resp.getStatus() == Response.Status.NOT_ACCEPTABLE.getStatusCode()) {
            Map<String, List<String>> map = resp.readEntity(new GenericType<Map<String, List<String>>>(){});
            StringBuilder sb = new StringBuilder();
            for (String key: map.keySet()){
                sb.append(key).append(": ");
                for (String e : map.get(key)){
                    sb.append(e).append(", ");
                }
            }
            throw new ClientRestException(sb.toString());
        }
    
        if (resp.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            throw new ClientRestException("Erreur lors de la modification de l'auteur");
        }
    
        if (resp.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            throw new ClientRestException("Erreur lors de la modification de l'auteur");
        }
    
        return resp.readEntity(DtoAuteur.class);
    }
    
    public void delete(Long id) throws ClientRestException {
        Response resp = this.client.target(REQUEST_URL_AUTEUR).path(String.valueOf(id)).request()
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken()).delete();
        
        if (resp.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            throw new ClientRestException("Erreur lors de la modification de l'auteur");
        }
        
    }
    
    public int count(Map<String, FilterMeta> filterBy) {
        String filter = LazyDataTableUtil.filterByMapToStr(filterBy);
        return this.client.target(REQUEST_URL_AUTEUR).path("count").queryParam("filterBy", filter).request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken()).get(Integer.class);
    }
}
