package net.ent.etrs.bibliofront.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ent.etrs.bibliofront.model.entities.references.Genre;

import java.time.LocalDate;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoLivre {
    
    private Long id;
    
    private String nom;
    
    private String isbn;
    
    private LocalDate dateParution;
    
    private Integer nbPages;
    
    private Long auteur;
    
    private Genre genre;
    
    private String image;
    
    private Set<Long> illustrateurs;
}
