package com.esercizio.gestioneTicket.repositories;

import com.esercizio.gestioneTicket.model.Tickets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketsRepository extends JpaRepository<Tickets,Long> {
}
