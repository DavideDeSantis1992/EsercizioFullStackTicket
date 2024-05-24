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
public class Utenti {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "utenteId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "impresaId",nullable = false)
    private Imprese impresa;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @ManyToOne
    @JoinColumn(name = "ruoloAziendaleId", nullable = false)
    private RuoliAziendali ruoloAziendale;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

}
