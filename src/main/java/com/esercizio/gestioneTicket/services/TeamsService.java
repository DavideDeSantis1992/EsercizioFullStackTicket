package com.esercizio.gestioneTicket.services;

import com.esercizio.gestioneTicket.model.DTO.ProgettiDTO;
import com.esercizio.gestioneTicket.model.DTO.TeamsDTO;

import java.util.List;

public interface TeamsService {

    TeamsDTO crea(TeamsDTO teamsDTO);

    TeamsDTO modifica(Long id, TeamsDTO teamsDTO);

    List<TeamsDTO> listaAllTeam();

    List<TeamsDTO> listaTeamByProgetto(Long id);


}
