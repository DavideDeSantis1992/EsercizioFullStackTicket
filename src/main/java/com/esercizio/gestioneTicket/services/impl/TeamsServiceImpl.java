package com.esercizio.gestioneTicket.services.impl;

import com.esercizio.gestioneTicket.model.DTO.ProgettiDTO;
import com.esercizio.gestioneTicket.model.DTO.RuoliTeamsDTO;
import com.esercizio.gestioneTicket.model.DTO.TeamsDTO;
import com.esercizio.gestioneTicket.model.DTO.UtentiDTO;
import com.esercizio.gestioneTicket.model.Progetti;
import com.esercizio.gestioneTicket.model.RuoliTeams;
import com.esercizio.gestioneTicket.model.Teams;
import com.esercizio.gestioneTicket.model.Utenti;
import com.esercizio.gestioneTicket.repositories.ProgettiRepository;
import com.esercizio.gestioneTicket.repositories.RuoliTeamsRepository;
import com.esercizio.gestioneTicket.repositories.TeamsRepository;
import com.esercizio.gestioneTicket.repositories.UtentiRepository;
import com.esercizio.gestioneTicket.services.TeamsService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamsServiceImpl implements TeamsService {

    @Autowired
    TeamsRepository teamsRepository;
    @Autowired
    UtentiRepository utentiRepository;
    @Autowired
    RuoliTeamsRepository ruoliTeamsRepository;
    @Autowired
    ProgettiRepository progettiRepository;

    private void validazioneCampi(Utenti utente, RuoliTeams ruoloTeam, Progetti progetto){
        if(!utentiRepository.existsById(utente.getId())||utente==null){
            throw new EntityExistsException("Utente ID non valido");
        }

        if(!ruoliTeamsRepository.existsById(ruoloTeam.getId())||ruoloTeam==null){
            throw new EntityExistsException("Ruolo Team ID non valido");
        }

        if(!progettiRepository.existsById(progetto.getId())||progetto==null){
            throw new EntityExistsException("Progetto ID non valido");
        }
    }

    private Teams saveObject(TeamsDTO teamsDTO){
        Utenti utenteToSave = teamsDTO.getUtente()!=null?
                utentiRepository.findById(teamsDTO.getUtente().getId()).orElseThrow((
                )->new EntityNotFoundException("Utente non trovato")):null;

        RuoliTeams ruoloToSave = teamsDTO.getRuoloTeam()!=null?
                ruoliTeamsRepository.findById(teamsDTO.getRuoloTeam().getId()).orElseThrow((
                )->new EntityNotFoundException("Ruolo Team non trovato")):null;

        Progetti progettoToSave = teamsDTO.getProgetto()!=null?
                progettiRepository.findById(teamsDTO.getProgetto().getId()).orElseThrow((
                )->new EntityNotFoundException("Progetto non trovato")):null;

        validazioneCampi(
                utenteToSave,
                ruoloToSave,
                progettoToSave
        );

        Teams nuovoTeams = new Teams();

        nuovoTeams.setUtente(utenteToSave);
        nuovoTeams.setRuoloTeam(ruoloToSave);
        nuovoTeams.setProgetto(progettoToSave);

        return teamsRepository.save(nuovoTeams);

    }

    private Teams checkIdAndTake(Long id){
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID non valido");
        }

        if (!teamsRepository.existsById(id)) {
            throw new IllegalArgumentException("ID non presente");
        }

        if (!String.valueOf(id).matches("[0-9]+")) {
            throw new NumberFormatException("ID non valido con caratteri alfanumerici");
        }

        return teamsRepository.findById(id).orElseThrow();
    }

    private Teams editObject(Long id, TeamsDTO teamToEdit){
        Utenti utenteToSave = teamToEdit.getUtente()!=null?
                utentiRepository.findById(teamToEdit.getUtente().getId()).orElseThrow((
                )->new EntityNotFoundException("Utente non trovato")):null;

        RuoliTeams ruoloToSave = teamToEdit.getRuoloTeam()!=null?
                ruoliTeamsRepository.findById(teamToEdit.getRuoloTeam().getId()).orElseThrow((
                )->new EntityNotFoundException("Ruolo Team non trovato")):null;

        Progetti progettoToSave = teamToEdit.getProgetto()!=null?
                progettiRepository.findById(teamToEdit.getProgetto().getId()).orElseThrow((
                )->new EntityNotFoundException("Progetto non trovato")):null;

        validazioneCampi(
                utenteToSave,
                ruoloToSave,
                progettoToSave
        );

        Teams editTeam = checkIdAndTake(id);

        editTeam.setUtente(utenteToSave);
        editTeam.setRuoloTeam(ruoloToSave);
        editTeam.setProgetto(progettoToSave);
        editTeam.setTempoImpiegato(editTeam.getTempoImpiegato());

        return teamsRepository.save(editTeam);
    }

    private List<TeamsDTO> lista(){
        if(teamsRepository.findAll().isEmpty()){
            throw new EntityExistsException("Non sono presenti dati");
        } else {
            List<Teams> list = teamsRepository.findAll();
            List<TeamsDTO> listDTO = new ArrayList<>();
            for(Teams team : list){
                listDTO.add(new TeamsDTO(team));
            }
            return listDTO;
        }
    }

    @Override
    public TeamsDTO crea(TeamsDTO teamsDTO) {
        return new TeamsDTO(saveObject(teamsDTO));
    }

    @Override
    public TeamsDTO modifica(Long id, TeamsDTO teamsDTO) {
        return new TeamsDTO(editObject(id,teamsDTO));
    }

    @Override
    public List<TeamsDTO> listaAllTeam() {
        return lista();
    }

    @Override
    public List<TeamsDTO> listaTeamByProgetto(Long id) {
        List<Teams>lista=teamsRepository.findByProgettoId(id);
        if(lista.isEmpty()){
            throw new EntityNotFoundException("Non esiste alcun team abbinato al progetto");
        }
        List<TeamsDTO> listaDTO = lista.stream().map(TeamsDTO::new).collect(Collectors.toList());
        return listaDTO;
    }

    /*
    private void checkListByProgetto(List lista){
        lista==null?  new EntityNotFoundException("Non esiste alcun team abbinato al progetto");
    }
     */
}
