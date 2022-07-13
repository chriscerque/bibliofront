package net.ent.etrs.bibliofront.model.dtos.converters;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.ent.etrs.bibliofront.model.dtos.DtoAuteur;
import net.ent.etrs.bibliofront.model.entities.Auteur;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DtoAuteurConverter {
    
    public static Auteur fromDto(DtoAuteur dtoAuteur) {
        return Auteur.builder()
                .id(dtoAuteur.getId())
                .nom(dtoAuteur.getNom())
                .prenom(dtoAuteur.getPrenom())
                .dateDeNaissance(dtoAuteur.getDateDeNaissance())
                .build();
    }
    
    public static DtoAuteur fromEntity(Auteur auteur) {
        return DtoAuteur.builder()
                .id(auteur.getId())
                .nom(auteur.getNom())
                .prenom(auteur.getPrenom())
                .dateDeNaissance(auteur.getDateDeNaissance())
                .build();
    }

}
