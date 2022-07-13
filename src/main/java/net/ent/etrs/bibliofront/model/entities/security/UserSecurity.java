package net.ent.etrs.bibliofront.model.entities.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import lombok.*;
import net.ent.etrs.bibliofront.model.entities.references.Role;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserSecurity implements Serializable {
    
    @Getter @Setter
    private String login;
    
    @Getter @Setter
    private Role role;
    
    @Getter @Setter
    private String token;
    
    public UserSecurity(String token) {
        this.setToken(token);
        
        String tkn = token.split(" ")[1];
        tkn = String.format("%s.%s.", tkn.split("\\.")[0], tkn.split("\\.")[1]);
        
        Jwt<Header, Claims> t = Jwts.parserBuilder().build().parseClaimsJwt(tkn);
        
        this.setLogin(t.getBody().getSubject());
        this.setRole(Role.valueOf((String) t.getBody().get("role")));
    }
    
}
