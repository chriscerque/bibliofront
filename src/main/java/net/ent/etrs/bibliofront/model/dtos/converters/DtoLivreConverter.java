package net.ent.etrs.bibliofront.model.dtos.converters;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.ent.etrs.bibliofront.model.dtos.DtoIllustrateur;
import net.ent.etrs.bibliofront.model.dtos.DtoLivre;
import net.ent.etrs.bibliofront.model.entities.Auteur;
import net.ent.etrs.bibliofront.model.entities.Illustrateur;
import net.ent.etrs.bibliofront.model.entities.Livre;
import net.ent.etrs.bibliofront.model.facades.FacadeClientAuteur;
import net.ent.etrs.bibliofront.model.facades.FacadeClientIllustrateur;
import net.ent.etrs.bibliofront.utils.CDIUtils;

import java.util.Base64;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DtoLivreConverter {
    
    private static FacadeClientIllustrateur facadeClientIllustrateur;
    private static FacadeClientAuteur facadeClientAuteur;
    
    static  {
        DtoLivreConverter.facadeClientIllustrateur = CDIUtils.getBean(FacadeClientIllustrateur.class);
        DtoLivreConverter.facadeClientAuteur = CDIUtils.getBean(FacadeClientAuteur.class);
    }
    
    public static Livre fromDtoWithIllustrateurs(DtoLivre dtoLivre) {
        Auteur auteur = DtoAuteurConverter.fromDto(DtoLivreConverter.facadeClientAuteur.findById(dtoLivre.getId()).orElse(null));
        Set<Illustrateur> illustrateurs = DtoLivreConverter.facadeClientIllustrateur.findByLivre(dtoLivre.getId()).stream().map(DtoIllustrateurConverter::fromDto).collect(Collectors.toSet());
        
        return Livre.builder()
                .id(dtoLivre.getId())
                .nom(dtoLivre.getNom())
                .isbn(dtoLivre.getIsbn())
                .genre(dtoLivre.getGenre())
                .nbPages(dtoLivre.getNbPages())
                .dateParution(dtoLivre.getDateParution())
                .image(dtoLivre.getImage() != null ? Base64.getDecoder().decode(dtoLivre.getImage()) : null)
                .auteur(auteur)
                .illustrateurs(illustrateurs)
                .build();
                
    }
    
    public static Livre fromDto(DtoLivre dtoLivre) {
        Auteur auteur = DtoAuteurConverter.fromDto(DtoLivreConverter.facadeClientAuteur.findById(dtoLivre.getAuteur()).orElse(null));
        
        return Livre.builder()
                .id(dtoLivre.getId())
                .nom(dtoLivre.getNom())
                .isbn(dtoLivre.getIsbn())
                .genre(dtoLivre.getGenre())
                .nbPages(dtoLivre.getNbPages())
                .dateParution(dtoLivre.getDateParution())
                .image(dtoLivre.getImage() != null ? Base64.getDecoder().decode(dtoLivre.getImage()) : null)
                .auteur(auteur)
                .build();
        
    }
    
    public static DtoLivre fromEntity(Livre livre) {
        Set<Long> illustrateurs = livre.getIllustrateurs().stream().map(Illustrateur::getId).collect(Collectors.toSet());
        
        return DtoLivre.builder()
                .id(livre.getId())
                .nom(livre.getNom())
                .isbn(livre.getIsbn())
                .genre(livre.getGenre())
                .nbPages(livre.getNbPages())
                .dateParution(livre.getDateParution())
                .image(livre.getImage() != null ? Base64.getEncoder().encodeToString(livre.getImage()) : null)
                .auteur(livre.getAuteur().getId())
                .illustrateurs(illustrateurs)
                .build();
                
    }

}
