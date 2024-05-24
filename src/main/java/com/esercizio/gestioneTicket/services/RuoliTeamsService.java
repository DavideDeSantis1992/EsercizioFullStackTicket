package com.esercizio.gestioneTicket.services;

import com.esercizio.gestioneTicket.model.DTO.RuoliTeamsDTO;
import java.util.List;

public interface RuoliTeamsService {
    RuoliTeamsDTO crea(RuoliTeamsDTO ruoloDTO);

    RuoliTeamsDTO modifica(Long id, RuoliTeamsDTO ruoloDTO);

    List<RuoliTeamsDTO> getAllOrdineId();

    List<RuoliTeamsDTO> getAllOrdineDescrizione();

    List<RuoliTeamsDTO> getAllTrue();

    Boolean switchValidazione(Long id);

    RuoliTeamsDTO getById(Long id);

    List<RuoliTeamsDTO> findByString(String stringa);
}
