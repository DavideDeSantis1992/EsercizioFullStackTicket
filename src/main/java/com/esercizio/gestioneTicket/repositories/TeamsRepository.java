package com.esercizio.gestioneTicket.repositories;

import com.esercizio.gestioneTicket.model.Teams;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamsRepository extends JpaRepository<Teams,Long> {
    List<Teams> findByProgettoId(Long progettoId);
}
