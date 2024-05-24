package com.esercizio.gestioneTicket.services.impl;

import com.esercizio.gestioneTicket.model.DTO.StatiDTO;
import com.esercizio.gestioneTicket.model.Stati;
import com.esercizio.gestioneTicket.repositories.StatiRepository;
import com.esercizio.gestioneTicket.services.StatiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;

@Service
public class StatiServiceImpl implements StatiService {
    @Autowired
    private StatiRepository repository;

    @Override
    public StatiDTO crea(StatiDTO statoDTO) {
        MetodiToDo.validaDescrizione(statoDTO.getDescrizione());
        MetodiToDo.existingObject(repository, statoDTO.getDescrizione());
        Stati statoToSave = MetodiToDo.saveNewDbObject(statoDTO.getDescrizione(), repository, Stati::new);
        return new StatiDTO(statoToSave);
    }

    @Override
    public StatiDTO modifica(Long id, StatiDTO statoDTO) {
        MetodiToDo.checkId(id,repository);
        MetodiToDo.validaDescrizione(statoDTO.getDescrizione());
        MetodiToDo.existingObject(repository,statoDTO.getDescrizione());
        BiFunction<Stati, String, Stati> updateFunction = (statoToUpdate, newDescription) -> {
            statoToUpdate.setDescrizione(newDescription);
            return statoToUpdate;
        };
        Stati statoUpdate = MetodiToDo.editDbObject(statoDTO.getDescrizione(), id, repository, updateFunction);
        return new StatiDTO(statoUpdate);
    }

    @Override
    public List<StatiDTO> getAllOrdineId() {
        return MetodiToDo.listEntities(repository, StatiDTO::new);
    }

    @Override
    public List<StatiDTO> getAllOrdineDescrizione() {
        List<StatiDTO> listaDTO = MetodiToDo.listEntities(repository, StatiDTO::new);
        Collections.sort(listaDTO, Comparator.comparing(dto->dto.getDescrizione(),String.CASE_INSENSITIVE_ORDER));
        return listaDTO;
    }

    @Override
    public List<StatiDTO> getAllTrue() {
        List<StatiDTO> statiValidiDTO = MetodiToDo.listValidEntities(
                repository, Stati::isValido, StatiDTO::new, "Nessun stato attivo presente"
        );
        return statiValidiDTO;
    }

    @Override
    public Boolean switchValidazione(Long id) {
        return MetodiToDo.switchValid(id, repository);
    }

    @Override
    public StatiDTO getById(Long id) {
        MetodiToDo.checkId(id,repository);
        StatiDTO statoDTO = new StatiDTO(repository.findById(id).get());
        return statoDTO;
    }

    @Override
    public List<StatiDTO> findByString(String stringa) {
        return MetodiToDo.listEntitiesByString(
                repository, stringa, StatiDTO::new, entity -> true, "Nessun stato corrisponde alla ricerca"
        );
    }
}
