package net.ent.etrs.bibliofront.view.auteur;

import net.ent.etrs.bibliofront.model.dtos.converters.DtoAuteurConverter;
import net.ent.etrs.bibliofront.model.entities.Auteur;
import net.ent.etrs.bibliofront.model.facades.FacadeClientAuteur;
import net.ent.etrs.bibliofront.model.facades.exceptions.ClientRestException;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LazyDataModelAuteur extends LazyDataModel<Auteur> {
    
    @Inject
    private FacadeClientAuteur facadeAuteur;
    
    @Override
    public List<Auteur> load(int first, int pageSize, Map<String, SortMeta> sortedBy, Map<String, FilterMeta> filterBy) {
        try {
            return this.facadeAuteur.findAll(first, pageSize, sortedBy, filterBy).stream().map(DtoAuteurConverter::fromDto).collect(Collectors.toList());
        } catch (ClientRestException e) {
            return new ArrayList<>();
        }
    }
    
    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return this.facadeAuteur.count(filterBy);
    }
}
