package com.esercizio.gestioneTicket.services.impl;

import com.esercizio.gestioneTicket.model.DTO.RuoliTeamsDTO;
import com.esercizio.gestioneTicket.model.RuoliTeams;
import com.esercizio.gestioneTicket.repositories.RuoliTeamsRepository;
import com.esercizio.gestioneTicket.services.RuoliTeamsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BiFunction;

@Service
public class RuoliTeamsServiceImpl implements RuoliTeamsService {

    @Autowired
    private RuoliTeamsRepository repository;

    // CREAZIONE RUOLO TEAM
    @Override
    public RuoliTeamsDTO crea(RuoliTeamsDTO ruoloDTO) {
        MetodiToDo.validaDescrizione(ruoloDTO.getDescrizione());
        MetodiToDo.existingObject(repository, ruoloDTO.getDescrizione());
        RuoliTeams ruoloToSave = MetodiToDo.saveNewDbObject(ruoloDTO.getDescrizione(), repository, RuoliTeams::new);
        return new RuoliTeamsDTO(ruoloToSave);
    }

    // MODIFICA RUOLO TEAM
    @Override
    public RuoliTeamsDTO modifica(Long id, RuoliTeamsDTO ruoloDTO) {
        MetodiToDo.checkId(id,repository);
        MetodiToDo.validaDescrizione(ruoloDTO.getDescrizione());
        MetodiToDo.existingObject(repository,ruoloDTO.getDescrizione());
        BiFunction<RuoliTeams, String, RuoliTeams> updateFunction = (ruoloToUpdate, newDescription) -> {
            ruoloToUpdate.setDescrizione(newDescription);
            return ruoloToUpdate;
        };
        RuoliTeams ruoloUpdated = MetodiToDo.editDbObject(ruoloDTO.getDescrizione(), id, repository, updateFunction);
        return new RuoliTeamsDTO(ruoloUpdated);
    }

    // LISTA RUOLI TEAM ORDINE ID
    @Override
    public List<RuoliTeamsDTO> getAllOrdineId() {return MetodiToDo.listEntities(repository, RuoliTeamsDTO::new);
    }

    // LISTA RUOLI TEAM ORDINE ALFABETICA DESCRIZIONE
    @Override
    public List<RuoliTeamsDTO> getAllOrdineDescrizione() {
        List<RuoliTeamsDTO> listaDTO = MetodiToDo.listEntities(repository, RuoliTeamsDTO::new);
        Collections.sort(listaDTO, Comparator.comparing(dto -> dto.getDescrizione(), String.CASE_INSENSITIVE_ORDER));
        return listaDTO;
    }



    // LISTA RUOLI TEAM CON VALIDAZIONE = TRUE
    @Override
    public List<RuoliTeamsDTO> getAllTrue() {
        List<RuoliTeamsDTO> ruoliValidiDTO = MetodiToDo.listValidEntities(
                repository, RuoliTeams::isValido, RuoliTeamsDTO::new, "Nessun ruolo attivo presente"
        );
        return ruoliValidiDTO;
    }

    // CAMBIO STATO VALIDAZIONE RUOLO SINGOLO
    @Override
    public Boolean switchValidazione(Long id) {
        return MetodiToDo.switchValid(id, repository);
    }

    // FIND RUOLO TEAM BY ID
    @Override
    public RuoliTeamsDTO getById(Long id) {
        MetodiToDo.checkId(id,repository);
        RuoliTeamsDTO ruoloDTO = new RuoliTeamsDTO(repository.findById(id).get());
        return ruoloDTO;
    }

    // FIND RUOLO TEAM BY DESC (ANCHE IN PARTE)
    @Override
    public List<RuoliTeamsDTO> findByString(String stringa) {
        return MetodiToDo.listEntitiesByString(
                repository, stringa, RuoliTeamsDTO::new, entity -> true, "Nessun ruolo corrisponde alla ricerca"
        );
    }
}
