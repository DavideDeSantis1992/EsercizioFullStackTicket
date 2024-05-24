package com.esercizio.gestioneTicket.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Builder
public class Teams {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "teamId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utenteId", nullable = false)
    private Utenti utente;

    @ManyToOne
    @JoinColumn(name = "ruoloTeamId", nullable = false)
    private RuoliTeams ruoloTeam;

    @ManyToOne
    @JoinColumn(name = "progettoId", nullable = false, unique = false)
    private Progetti progetto;

    private Duration tempoImpiegato;
}
