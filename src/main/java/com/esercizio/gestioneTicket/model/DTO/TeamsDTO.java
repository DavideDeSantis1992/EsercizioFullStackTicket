package com.esercizio.gestioneTicket.model.DTO;

import com.esercizio.gestioneTicket.model.Teams;
import com.esercizio.gestioneTicket.model.Utenti;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Duration;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TeamsDTO {
    private Long id;
    private UtentiDTO utente;
    private RuoliTeamsDTO ruoloTeam;
    private ProgettiDTO progetto;
    private Duration tempoImpiegato;

    public TeamsDTO(Teams teams) {
        if(teams != null){
            this.id=teams.getId();
            this.utente=teams.getUtente() != null ? new UtentiDTO(teams.getUtente()) : null;
            this.ruoloTeam=teams.getRuoloTeam() != null ? new RuoliTeamsDTO(teams.getRuoloTeam()):null;
            this.progetto=teams.getProgetto()!=null?new ProgettiDTO(teams.getProgetto()):null;
            this.tempoImpiegato=teams.getTempoImpiegato();
        }
    }

    public TeamsDTO(TeamsDTO teamsDTO) {
    }
}
