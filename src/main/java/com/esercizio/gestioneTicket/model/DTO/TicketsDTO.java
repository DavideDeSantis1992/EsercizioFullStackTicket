package com.esercizio.gestioneTicket.model.DTO;

import com.esercizio.gestioneTicket.model.Teams;
import com.esercizio.gestioneTicket.model.Tickets;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TicketsDTO {
    private Long id;
    private ProgettiDTO progetto;
    private String titoloTicket;
    private String descTicket;
    private TeamsDTO utenteCreatore;
    private TeamsDTO utenteAssegnato;
    private PrioritaDTO priorita;
    private CategorieDTO categoria;
    private StatiDTO stato;
    private LocalDate dataCreazione;
    private LocalDate dataScadenza;
    private LocalDate dataAggiornamento;
    private LocalDate dataPresaInCarico;
    private LocalDate dataChiusura;

    public TicketsDTO(Tickets tickets) {
        this.id = tickets.getId();
        this.progetto = tickets.getProgetto()!=null?new ProgettiDTO(tickets.getProgetto()):null;
        this.titoloTicket = tickets.getTitoloTicket();
        this.descTicket = tickets.getDescTicket();
        this.utenteCreatore = tickets.getUtenteCreatore()!=null?new TeamsDTO(tickets.getUtenteCreatore()):null;
        this.utenteAssegnato = tickets.getUtenteAssegnato()!=null?new TeamsDTO(tickets.getUtenteAssegnato()):null;
        this.priorita = tickets.getPriorita()!=null?new PrioritaDTO(tickets.getPriorita()):null;
        this.categoria = tickets.getCategoria()!=null?new CategorieDTO(tickets.getCategoria()):null;
        this.stato = tickets.getStato()!=null?new StatiDTO(tickets.getStato()):null;
        this.dataCreazione = tickets.getDataCreazione();
        this.dataScadenza = tickets.getDataScadenza();
        this.dataAggiornamento= tickets.getDataAggiornamento();
        this.dataPresaInCarico = tickets.getDataPresaInCarico();
        this.dataChiusura = tickets.getDataChiusura();
    }
}
