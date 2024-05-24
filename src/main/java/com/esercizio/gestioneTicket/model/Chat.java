package com.esercizio.gestioneTicket.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Builder
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "chatId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ticketId")
    private Tickets ticket;
    @ManyToOne
    @JoinColumn(name = "teamId")
    private Teams team;

    @Column(name = "messaggio")
    private String messaggio;
    @Column(name = "dataMessaggio")
    private LocalDate dataMessaggio;
}
