package net.ent.etrs.bibliofront.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoUserLogin {
    
    private String login;
    
    private String password;
    
}
