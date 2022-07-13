package net.ent.etrs.bibliofront.model.facades;

import lombok.Getter;
import net.ent.etrs.bibliofront.model.dtos.DtoLivre;
import net.ent.etrs.bibliofront.utils.LazyDataTableUtil;
import net.ent.etrs.bibliofront.view.SessionBean;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class FacadeClientLivre implements Serializable {

    private static final String REQUEST_URL_LIVRE = "http://localhost:8080/biblioback-1.0-SNAPSHOT/api/livres/";
    
    @Inject
    private SessionBean sessionBean;

    @Getter
    private Client client = ClientBuilder.newClient();
    
    public List<DtoLivre> findAll(int first, int pageSize, Map<String, SortMeta> sortedBy, Map<String, FilterMeta> filterBy) {
        String sorted = LazyDataTableUtil.sortedByMapToStr(sortedBy);
        String filter = LazyDataTableUtil.filterByMapToStr(filterBy);
        
        return this.client.target(REQUEST_URL_LIVRE)
                .queryParam("first", first)
                .queryParam("pageSize", pageSize)
                .queryParam("sortedBy", sorted)
                .queryParam("filterBy", filter)
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken()).get(new GenericType<List<DtoLivre>>(){});
    }
    
    public Optional<DtoLivre> findById(Long id) {
        DtoLivre livre = this.client.target(REQUEST_URL_LIVRE).path(String.valueOf(id)).request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken()).get(DtoLivre.class);
        if (Objects.nonNull(livre)) {
            return Optional.of(livre);
        }
        return Optional.empty();
    }
    
    public DtoLivre create(DtoLivre dtoLivre) {
        return this.client.target(REQUEST_URL_LIVRE).request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken()).post(Entity.json(dtoLivre)).readEntity(DtoLivre.class);
    }
    
    public DtoLivre update(DtoLivre dtoLivre) {
        return this.client.target(REQUEST_URL_LIVRE).path(String.valueOf(dtoLivre.getId())).request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken()).put(Entity.json(dtoLivre)).readEntity(DtoLivre.class);
    }
    
    public void delete(Long id) {
        this.client.target(REQUEST_URL_LIVRE).path(String.valueOf(id)).request()
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken()).delete();
    }
    
    public int count(Map<String, FilterMeta> filterBy) {
        String filter = LazyDataTableUtil.filterByMapToStr(filterBy);
        return this.client.target(REQUEST_URL_LIVRE).path("count").queryParam("filterBy", filter).request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, this.sessionBean.getUser().getToken()).get(Integer.class);
    }
}
