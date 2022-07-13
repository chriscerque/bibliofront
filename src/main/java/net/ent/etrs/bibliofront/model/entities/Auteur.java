package net.ent.etrs.bibliofront.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.io.Serializable;
import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Auteur implements Serializable {
    
    private Long id;
    
    private LocalDate dateDeNaissance;
    
    private String nom;
    
    private String prenom;
}
