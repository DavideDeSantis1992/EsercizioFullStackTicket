package com.esercizio.gestioneTicket.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Builder
public class Tickets {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ticketId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "progettoId", nullable = false)
    private Progetti progetto;

    @Column(name = "titoloTicket", nullable = false)
    private String titoloTicket;

    @Column(name = "descTicket", nullable = false)
    private String descTicket;

    @ManyToOne
    @JoinColumn(name = "utenteCreatoreId",nullable = false)
    private Teams utenteCreatore;

    @ManyToOne
    @JoinColumn(name = "utenteAssegnatoId", nullable = false)
    private Teams utenteAssegnato;

    @ManyToOne
    @JoinColumn(name = "prioritaId", nullable = false)
    private Priorita priorita;

    @ManyToOne
    @JoinColumn(name = "categoriaId", nullable = false)
    private Categorie categoria;

    @ManyToOne
    @JoinColumn(name = "statoId", nullable = false)
    private Stati stato;

    @Column(name = "dataCreazione", nullable = false)
    private LocalDate dataCreazione;

    @Column(name = "dataScadenza")
    private LocalDate dataScadenza;

    @Column(name = "dataAggiornamento")
    private LocalDate dataAggiornamento;

    @Column(name = "dataPresaInCarico")
    private LocalDate dataPresaInCarico;

    @Column(name = "dataChiusura")
    private LocalDate dataChiusura;
}
