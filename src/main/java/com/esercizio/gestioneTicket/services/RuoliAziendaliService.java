package com.esercizio.gestioneTicket.services;

import com.esercizio.gestioneTicket.model.DTO.RuoliAziendaliDTO;
import java.util.List;

public interface RuoliAziendaliService {
    RuoliAziendaliDTO crea(RuoliAziendaliDTO ruoloDTO);

    RuoliAziendaliDTO modifica(Long id, RuoliAziendaliDTO ruoloDTO);

    List<RuoliAziendaliDTO> getAllOrdineId();

    List<RuoliAziendaliDTO> getAllOrdineDescrizione();

    List<RuoliAziendaliDTO> getAllTrue();

    Boolean switchValidazione(Long id);

    RuoliAziendaliDTO getById(Long id);

    List<RuoliAziendaliDTO> findByString(String stringa);
}
