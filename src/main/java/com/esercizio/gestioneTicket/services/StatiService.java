package com.esercizio.gestioneTicket.services;

import com.esercizio.gestioneTicket.model.DTO.StatiDTO;
import java.util.List;

public interface StatiService {
    StatiDTO crea(StatiDTO statoDTO);

    StatiDTO modifica(Long id, StatiDTO statoDTO);

    List<StatiDTO> getAllOrdineId();

    List<StatiDTO> getAllOrdineDescrizione();

    List<StatiDTO> getAllTrue();

    Boolean switchValidazione(Long id);

    StatiDTO getById(Long id);

    List<StatiDTO> findByString(String stringa);
}
