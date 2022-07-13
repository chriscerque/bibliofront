package net.ent.etrs.bibliofront.view.livre;

import net.ent.etrs.bibliofront.model.dtos.converters.DtoAuteurConverter;
import net.ent.etrs.bibliofront.model.dtos.converters.DtoLivreConverter;
import net.ent.etrs.bibliofront.model.entities.Auteur;
import net.ent.etrs.bibliofront.model.entities.Livre;
import net.ent.etrs.bibliofront.model.facades.FacadeClientLivre;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LazyDataModelLivre extends LazyDataModel<Livre> {
    
    @Inject
    private FacadeClientLivre facadeLivre;
    
    @Override
    public List<Livre> load(int first, int pageSize, Map<String, SortMeta> sortedBy, Map<String, FilterMeta> filterBy) {
        return this.facadeLivre.findAll(first, pageSize, sortedBy, filterBy).stream().map(DtoLivreConverter::fromDto).collect(Collectors.toList());
    }
    
    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return this.facadeLivre.count(filterBy);
    }
}
