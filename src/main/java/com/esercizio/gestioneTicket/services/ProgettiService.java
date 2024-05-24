package com.esercizio.gestioneTicket.services;

import com.esercizio.gestioneTicket.model.DTO.ProgettiDTO;

import java.util.List;

public interface ProgettiService {
    ProgettiDTO crea (ProgettiDTO progettoDTO);

    ProgettiDTO modifica (Long id, ProgettiDTO progettoDTO);

    List<ProgettiDTO> listaProgettiOrdineTitolo();

    ProgettiDTO getById(Long id);

    List<ProgettiDTO> findByTitolo(String titolo);

    List<ProgettiDTO> findByDescrizione(String descrizione);
}
