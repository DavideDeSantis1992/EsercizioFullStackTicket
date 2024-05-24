package com.esercizio.gestioneTicket.services;

import com.esercizio.gestioneTicket.model.DTO.UtentiDTO;

import java.util.List;

public interface UtentiService {

    UtentiDTO crea (UtentiDTO utenteDTO);

    UtentiDTO modifica(Long id, UtentiDTO utenteDTO);

    List<UtentiDTO> getAllOrdineCognome();

    UtentiDTO getById(Long id);

    List<UtentiDTO> findByCognomeString(String cognome);

    List<UtentiDTO> findByNomeString(String nome);
}
