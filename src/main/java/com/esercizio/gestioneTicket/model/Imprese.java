package com.esercizio.gestioneTicket.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Builder
public class Imprese {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "impresaId")
    private Long id;

    @Column(name = "ragioneSociale", nullable = false)
    private String ragioneSociale;

    @Column(name = "partitaIva",nullable = false)
    private String partitaIva;

    @Column(name = "codiceFiscale", nullable = false)
    private String codiceFiscale;

    @Column(nullable = false)
    private String indirizzo;

    @Column(nullable = false)
    private String comune;

    @Column(nullable = false)
    private String provincia;

    @Column(nullable = false)
    private String cap;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String pec;

    @Column(nullable = false)
    private String sdi;

}
