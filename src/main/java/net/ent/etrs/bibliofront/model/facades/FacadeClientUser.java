package net.ent.etrs.bibliofront.model.facades;

import lombok.Getter;
import net.ent.etrs.bibliofront.model.dtos.DtoUserLogin;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.Objects;

public class FacadeClientUser implements Serializable {

    private static final String REQUEST_URL_USER = "http://localhost:8080/biblioback-1.0-SNAPSHOT/api/users/";

    @Getter
    private Client client = ClientBuilder.newClient();
    
    public String login(DtoUserLogin dto) {
        String token = this.client.target(REQUEST_URL_USER).path("login").request(MediaType.APPLICATION_JSON).post(Entity.json(dto)).getHeaderString(HttpHeaders.AUTHORIZATION);
        
        if (Objects.isNull(token)) {
            return null;
        }
        return token;
    }
}
