package com.esercizio.gestioneTicket.services;

import com.esercizio.gestioneTicket.model.DTO.PrioritaDTO;

import java.util.List;

public interface PrioritaService {

    PrioritaDTO crea(PrioritaDTO prioritaDTO);

    PrioritaDTO modifica(Long id, PrioritaDTO prioritaDTO);

    List<PrioritaDTO> getAllOrdineId();

    List<PrioritaDTO> getAllOrdineDescrizione();

    List<PrioritaDTO> getAllTrue();

    Boolean switchValidazione(Long id);

    PrioritaDTO getById(Long id);

    List<PrioritaDTO> findByString(String stringa);

    List <PrioritaDTO> switchOrdinamento(Long id, Integer ord);
}
