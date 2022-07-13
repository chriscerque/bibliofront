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
public class Illustrateur implements Serializable {
    
    private Long id;
    
    @NotNull
    @Length(min = 1, max = 50)
    private String nom;
    
    @NotNull
    @Length(min = 1, max = 50)
    private String prenom;
}
