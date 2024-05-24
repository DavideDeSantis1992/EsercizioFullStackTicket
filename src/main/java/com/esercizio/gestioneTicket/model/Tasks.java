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
public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "taskId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ticketId")
    private Tickets ticket;

    @Column(name = "descTask")
    private String descrizione;

    @Column(name = "statoTask")
    private Boolean statoTask;
}
