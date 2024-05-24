package com.esercizio.gestioneTicket.services.impl;

import com.esercizio.gestioneTicket.model.*;
import com.esercizio.gestioneTicket.model.DTO.TicketsDTO;
import com.esercizio.gestioneTicket.model.DTO.UtentiDTO;
import com.esercizio.gestioneTicket.repositories.*;
import com.esercizio.gestioneTicket.services.PrioritaService;
import com.esercizio.gestioneTicket.services.TicketsService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketsServiceImpl implements TicketsService {
    @Autowired
    TicketsRepository ticketsRepository;
    @Autowired
    ProgettiRepository progettiRepository;

    @Autowired
    TeamsRepository teamsRepository;

    @Autowired
    PrioritaRepository prioritaRepository;

    @Autowired
    CategorieRepository categorieRepository;

    @Autowired
    StatiRepository statiRepository;

    private void validazioneCampi(Progetti progetto,
                                  String titolo,
                                  String descrizione,
                                  Utenti utenteCreatore,
                                  Utenti utenteAssegnato,
                                  Priorita priorita,
                                  Stati stato){

        if(!progettiRepository.existsById(progetto.getId())||progetto==null){
            throw new EntityExistsException("Progetto ID non valido");
        }

        if(titolo==null||titolo.isEmpty()){
            throw new IllegalArgumentException("Titolo mancante");
        }

        if(descrizione==null||descrizione.isEmpty()){
            throw new IllegalArgumentException("Descrizione mancante");
        }

        if(!teamsRepository.existsById(utenteCreatore.getId())||utenteCreatore==null){
            throw new EntityExistsException("Utente Creatore ID non valido");
        }

        if(!teamsRepository.existsById(utenteAssegnato.getId())||utenteAssegnato==null){
            throw new EntityExistsException("Utente Asegnato ID non valido");
        }

        if(!prioritaRepository.existsById(priorita.getId())||priorita==null){
            throw new EntityExistsException("Priorità ID non valido");
        }

        if(!statiRepository.existsById(stato.getId())||stato==null){
            throw new EntityExistsException("Stato ID non valido");
        }

    }

    private Tickets saveObject(TicketsDTO ticketsDTO){
        Progetti progetto = ticketsDTO.getProgetto()!=null ?
                progettiRepository.findById(ticketsDTO.getProgetto().getId()).orElseThrow((
                )->new EntityNotFoundException("Progetto non trovato")):null;

        Teams utenteCreatore = ticketsDTO.getUtenteCreatore()!=null ?
                teamsRepository.findById(ticketsDTO.getUtenteCreatore().getId()).orElseThrow((
                )->new EntityNotFoundException("Utente Creatore non trovato")):null;

        Teams utenteAssegnato = ticketsDTO.getUtenteAssegnato()!=null ?
                teamsRepository.findById(ticketsDTO.getUtenteAssegnato().getId()).orElseThrow((
                )->new EntityNotFoundException("Utente Assegnato non trovato")):null;

        Priorita priorita = ticketsDTO.getPriorita()!=null ?
                prioritaRepository.findById(ticketsDTO.getPriorita().getId()).orElseThrow((
                )->new EntityNotFoundException("Priorità non trovata")):null;

        Categorie categoria = ticketsDTO.getCategoria()!=null ?
                categorieRepository.findById(ticketsDTO.getCategoria().getId()).orElseThrow((
                )->new EntityNotFoundException("Categoria non trovata")):null;

        Stati stato = ticketsDTO.getStato()!=null ?
                statiRepository.findById(ticketsDTO.getStato().getId()).orElseThrow((
                )->new EntityNotFoundException("Stato non trovato")):null;

        LocalDate dataCreazione = LocalDate.now();

        Tickets nuovoTicket = new Tickets();

        nuovoTicket.setProgetto(progetto);
        nuovoTicket.setTitoloTicket(ticketsDTO.getTitoloTicket());
        nuovoTicket.setDescTicket(ticketsDTO.getDescTicket());
        nuovoTicket.setUtenteCreatore(utenteCreatore);
        nuovoTicket.setUtenteAssegnato(utenteAssegnato);
        nuovoTicket.setPriorita(priorita);
        nuovoTicket.setCategoria(categoria);
        nuovoTicket.setStato(stato);
        nuovoTicket.setDataCreazione(dataCreazione);
        nuovoTicket.setDataScadenza(ticketsDTO.getDataScadenza());
        return ticketsRepository.save(nuovoTicket);
    }

    private Tickets checkIdAndTake(Long id){
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID non valido");
        }

        if (!ticketsRepository.existsById(id)) {
            throw new IllegalArgumentException("ID non presente");
        }

        if (!String.valueOf(id).matches("[0-9]+")) {
            throw new NumberFormatException("ID non valido con caratteri alfanumerici");
        }

        return ticketsRepository.findById(id).orElseThrow();
    }

    private Tickets editObject(Long id,TicketsDTO ticketToEdit){
        Progetti progetto = ticketToEdit.getProgetto()!=null ?
                progettiRepository.findById(ticketToEdit.getProgetto().getId()).orElseThrow((
                )->new EntityNotFoundException("Progetto non trovato")):null;

        Teams utenteCreatore = ticketToEdit.getUtenteCreatore()!=null ?
                teamsRepository.findById(ticketToEdit.getUtenteCreatore().getId()).orElseThrow((
                )->new EntityNotFoundException("Utente Creatore non trovato")):null;

        Teams utenteAssegnato = ticketToEdit.getUtenteAssegnato()!=null ?
                teamsRepository.findById(ticketToEdit.getUtenteAssegnato().getId()).orElseThrow((
                )->new EntityNotFoundException("Utente Assegnato non trovato")):null;

        Priorita priorita = ticketToEdit.getPriorita()!=null ?
                prioritaRepository.findById(ticketToEdit.getPriorita().getId()).orElseThrow((
                )->new EntityNotFoundException("Priorità non trovata")):null;

        Categorie categoria = ticketToEdit.getCategoria()!=null ?
                categorieRepository.findById(ticketToEdit.getCategoria().getId()).orElseThrow((
                )->new EntityNotFoundException("Categoria non trovata")):null;

        Stati stato = ticketToEdit.getStato()!=null ?
                statiRepository.findById(ticketToEdit.getStato().getId()).orElseThrow((
                )->new EntityNotFoundException("Stato non trovato")):null;

        Tickets editTicket = checkIdAndTake(id);

        editTicket.setProgetto(progetto);
        editTicket.setTitoloTicket(ticketToEdit.getTitoloTicket());
        editTicket.setDescTicket(ticketToEdit.getDescTicket());
        editTicket.setUtenteCreatore(utenteCreatore);
        editTicket.setUtenteAssegnato(utenteAssegnato);
        editTicket.setPriorita(priorita);
        editTicket.setCategoria(categoria);
        editTicket.setStato(stato);
        editTicket.setDataCreazione(ticketToEdit.getDataCreazione());
        editTicket.setDataScadenza(ticketToEdit.getDataScadenza());
        editTicket.setDataAggiornamento(ticketToEdit.getDataAggiornamento());
        editTicket.setDataPresaInCarico(ticketToEdit.getDataPresaInCarico());
        editTicket.setDataChiusura(ticketToEdit.getDataChiusura());

        return ticketsRepository.save(editTicket);
    }

    private List<TicketsDTO> lista(){
        if(ticketsRepository.findAll().isEmpty()){
            throw new EntityExistsException("Non sono presenti dati");
        } else {
            List<Tickets> list =ticketsRepository.findAll();
            List<TicketsDTO> listDTO = new ArrayList<>();
            for(Tickets ticket : list){
                listDTO.add(new TicketsDTO(ticket));
            }
            return listDTO;
        }
    }

    private Page<TicketsDTO> listaPaginata(Pageable pageable){
        if(ticketsRepository.findAll().isEmpty()){
            throw new EntityExistsException("Non sono presenti ticket");
        } else {
            Page<Tickets> pagina = ticketsRepository.findAll(pageable);
            return pagina.map(TicketsDTO::new);
        }

    }

    @Override
    public TicketsDTO crea(TicketsDTO ticketsDTO) {
        return new TicketsDTO(saveObject(ticketsDTO));
    }

    @Override
    public TicketsDTO modifica(Long id, TicketsDTO ticketsDTO) {
        return new TicketsDTO(editObject(id,ticketsDTO));
    }

    @Override
    public Page<TicketsDTO> getAllOrdineId(Pageable pageable) {
        return listaPaginata(pageable);
    }


    @Override
    public TicketsDTO getById(Long id) {
        TicketsDTO ticketDTO = new TicketsDTO(checkIdAndTake(id));
        return ticketDTO;
    }

    @Override
    public Page<TicketsDTO> getAllOrdineProgetto(Pageable pageable) {
        Pageable sortedByProgetto = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by("progetto.id").ascending().and(Sort.by("titoloTicket").ascending()));
        return listaPaginata(sortedByProgetto);
    }


    @Override
    public Page<TicketsDTO> getAllOrdineTitolo(Pageable pageable) {
        Pageable sortedByTitolo = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by("titoloTicket").ascending().and(Sort.by("id").ascending()));
        return listaPaginata(sortedByTitolo);
    }


    @Override
    public Page<TicketsDTO> getAllOrdineDescrizione(Pageable pageable) {
        Pageable sortedByDescrizione = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by("descTicket").ascending().and(Sort.by("titoloTicket").ascending()));
        return listaPaginata(sortedByDescrizione);
    }



}
