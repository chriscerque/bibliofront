package net.ent.etrs.bibliofront.model.dtos.converters;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.ent.etrs.bibliofront.model.dtos.DtoAuteur;
import net.ent.etrs.bibliofront.model.dtos.DtoIllustrateur;
import net.ent.etrs.bibliofront.model.entities.Auteur;
import net.ent.etrs.bibliofront.model.entities.Illustrateur;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DtoIllustrateurConverter {
    
    public static Illustrateur fromDto(DtoIllustrateur dtoIllustrateur) {
        return Illustrateur.builder()
                .id(dtoIllustrateur.getId())
                .nom(dtoIllustrateur.getNom())
                .prenom(dtoIllustrateur.getPrenom())
                .build();
    }
    
    public static DtoIllustrateur fromEntity(Illustrateur illustrateur) {
        return DtoIllustrateur.builder()
                .id(illustrateur.getId())
                .nom(illustrateur.getNom())
                .prenom(illustrateur.getPrenom())
                .build();
    }

}
