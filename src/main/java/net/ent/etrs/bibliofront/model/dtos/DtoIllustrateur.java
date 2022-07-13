package net.ent.etrs.bibliofront.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoIllustrateur {
    
    private Long id;
    
    private String nom;
    
    private String prenom;
}
