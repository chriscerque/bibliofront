package net.ent.etrs.bibliofront.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ent.etrs.bibliofront.model.entities.references.Genre;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Livre implements Serializable {
    
    private Long id;
    
    
    private String nom;
    
    private String isbn;
    
    private LocalDate dateParution;
    
    private Integer nbPages;
    
    private Auteur auteur;
    
    private Genre genre;
    
    private byte[] image;
    
    private Set<Illustrateur> illustrateurs = new HashSet<>();
}
