package com.esercizio.gestioneTicket.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Builder
public class Contratti {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "contrattoId")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "impresaFornitoreId", nullable = false)
    private Imprese impresaFornitore;
    @ManyToOne
    @JoinColumn(name = "impresaClienteId", nullable = false)
    private Imprese impresaCliente;

    private Double importo;

    @Column(name = "dataInizio")
    private LocalDate dataInizio;

    @Column(name = "dataFine")
    private LocalDate dataFine;
}

