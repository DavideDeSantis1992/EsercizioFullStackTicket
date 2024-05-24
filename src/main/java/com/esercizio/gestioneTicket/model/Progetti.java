package com.esercizio.gestioneTicket.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Builder
public class Progetti {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "progettoId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "contrattoId",nullable = false)
    private Contratti contratto;

    @Column(nullable = false)
    private String titolo;

    @Column(nullable = false)
    private String descrizione;
}
