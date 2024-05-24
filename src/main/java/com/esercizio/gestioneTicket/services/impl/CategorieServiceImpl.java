package com.esercizio.gestioneTicket.services.impl;

import com.esercizio.gestioneTicket.model.Categorie;
import com.esercizio.gestioneTicket.model.DTO.CategorieDTO;
import com.esercizio.gestioneTicket.repositories.CategorieRepository;
import com.esercizio.gestioneTicket.services.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BiFunction;

@Service
public class CategorieServiceImpl implements CategorieService {
    @Autowired
    private CategorieRepository repository;
    @Override
    public CategorieDTO crea(CategorieDTO categoriaDTO) {
        MetodiToDo.validaDescrizione(categoriaDTO.getDescrizione());
        MetodiToDo.existingObject(repository, categoriaDTO.getDescrizione());
        Categorie categoriaToSave = MetodiToDo.saveNewDbObject(categoriaDTO.getDescrizione(), repository, Categorie::new);
        return new CategorieDTO(categoriaToSave);
    }

    // MODIFICA RUOLO TEAM
    @Override
    public CategorieDTO modifica(Long id, CategorieDTO categoriaDTO) {
        MetodiToDo.checkId(id,repository);
        MetodiToDo.validaDescrizione(categoriaDTO.getDescrizione());
        MetodiToDo.existingObject(repository,categoriaDTO.getDescrizione());
        BiFunction<Categorie, String, Categorie> updateFunction = (categoriaToUpdate, newDescription) -> {
            categoriaToUpdate.setDescrizione(newDescription);
            return categoriaToUpdate;
        };
        Categorie categoriaUpdate = MetodiToDo.editDbObject(categoriaDTO.getDescrizione(), id, repository, updateFunction);
        return new CategorieDTO(categoriaUpdate);
    }

    // LISTA RUOLI TEAM ORDINE ID
    @Override
    public List<CategorieDTO> getAllOrdineId() {return MetodiToDo.listEntities(repository, CategorieDTO::new);
    }

    // LISTA RUOLI TEAM ORDINE ALFABETICA DESCRIZIONE
    @Override
    public List<CategorieDTO> getAllOrdineDescrizione() {
        List<CategorieDTO> listaDTO = MetodiToDo.listEntities(repository, CategorieDTO::new);
        Collections.sort(listaDTO, Comparator.comparing(dto->dto.getDescrizione(),String.CASE_INSENSITIVE_ORDER));
        return listaDTO;
    }

    // LISTA RUOLI TEAM CON VALIDAZIONE = TRUE
    @Override
    public List<CategorieDTO> getAllTrue() {
        List<CategorieDTO> categorieValideDTO = MetodiToDo.listValidEntities(
                repository, Categorie::isValido, CategorieDTO::new, "Nessuna categoria attiva presente"
        );
        return categorieValideDTO;
    }

    // CAMBIO STATO VALIDAZIONE RUOLO SINGOLO
    @Override
    public Boolean switchValidazione(Long id) {
        return MetodiToDo.switchValid(id, repository);
    }

    // FIND RUOLO TEAM BY ID
    @Override
    public CategorieDTO getById(Long id) {
        MetodiToDo.checkId(id,repository);
        CategorieDTO categoriaDTO = new CategorieDTO(repository.findById(id).get());
        return categoriaDTO;
    }

    // FIND RUOLO TEAM BY DESC (ANCHE IN PARTE)
    @Override
    public List<CategorieDTO> findByString(String stringa) {
        return MetodiToDo.listEntitiesByString(
                repository, stringa, CategorieDTO::new, entity -> true, "Nessuna categoria corrisponde alla ricerca"
        );
    }
}
