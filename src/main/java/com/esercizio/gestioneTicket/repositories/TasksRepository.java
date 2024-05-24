package com.esercizio.gestioneTicket.repositories;

import com.esercizio.gestioneTicket.model.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TasksRepository extends JpaRepository<Tasks,Long>{
    List<Tasks> findByTicketId(Long ticketId);
}
