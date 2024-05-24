package com.esercizio.gestioneTicket.services.impl;

import com.esercizio.gestioneTicket.model.DTO.RuoliAziendaliDTO;
import com.esercizio.gestioneTicket.model.RuoliAziendali;
import com.esercizio.gestioneTicket.repositories.RuoliAziendaliRepository;
import com.esercizio.gestioneTicket.services.RuoliAziendaliService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BiFunction;

@Service
public class RuoliAziendaliServiceImpl implements RuoliAziendaliService {
    @Autowired
    private RuoliAziendaliRepository repository;

    @Override
    public RuoliAziendaliDTO crea(RuoliAziendaliDTO ruoloDTO) {
        MetodiToDo.validaDescrizione(ruoloDTO.getDescrizione());
        MetodiToDo.existingObject(repository, ruoloDTO.getDescrizione());
        RuoliAziendali ruoloToSave = MetodiToDo.saveNewDbObject(ruoloDTO.getDescrizione(), repository, RuoliAziendali::new);
        return new RuoliAziendaliDTO(ruoloToSave);
    }

    @Override
    public RuoliAziendaliDTO modifica(Long id, RuoliAziendaliDTO ruoloDTO) {
        MetodiToDo.checkId(id,repository);
        MetodiToDo.validaDescrizione(ruoloDTO.getDescrizione());
        MetodiToDo.existingObject(repository,ruoloDTO.getDescrizione());
        BiFunction<RuoliAziendali, String, RuoliAziendali> updateFunction = (ruoloToUpdate, newDescription) -> {
            ruoloToUpdate.setDescrizione(newDescription);
            return ruoloToUpdate;
        };
        RuoliAziendali ruoloUpdated = MetodiToDo.editDbObject(ruoloDTO.getDescrizione(), id, repository, updateFunction);
        return new RuoliAziendaliDTO(ruoloUpdated);
    }

    @Override
    public List<RuoliAziendaliDTO> getAllOrdineId() {
        return MetodiToDo.listEntities(repository, RuoliAziendaliDTO::new);
    }

    @Override
    public List<RuoliAziendaliDTO> getAllOrdineDescrizione() {
        List<RuoliAziendaliDTO> listaDTO = MetodiToDo.listEntities(repository, RuoliAziendaliDTO::new);
        Collections.sort(listaDTO, Comparator.comparing(dto->dto.getDescrizione()));
        return listaDTO;
    }

    @Override
    public List<RuoliAziendaliDTO> getAllTrue() {
        List<RuoliAziendaliDTO> ruoliValidiDTO = MetodiToDo.listValidEntities(
                repository, RuoliAziendali::isValido, RuoliAziendaliDTO::new, "Nessun ruolo attivo presente"
        );
        return ruoliValidiDTO;
    }

    @Override
    public Boolean switchValidazione(Long id) {
        return MetodiToDo.switchValid(id, repository);
    }

    @Override
    public RuoliAziendaliDTO getById(Long id) {
        MetodiToDo.checkId(id,repository);
        RuoliAziendaliDTO ruoloDTO = new RuoliAziendaliDTO(repository.findById(id).get());
        return ruoloDTO;
    }

    @Override
    public List<RuoliAziendaliDTO> findByString(String stringa) {
        return MetodiToDo.listEntitiesByString(
                repository, stringa, RuoliAziendaliDTO::new, entity -> true, "Nessun ruolo corrisponde alla ricerca"
        );
    }
}
