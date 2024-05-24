package com.esercizio.gestioneTicket.repositories;

import com.esercizio.gestioneTicket.model.Priorita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrioritaRepository extends JpaRepository<Priorita,Long>,GenericRepository<Priorita,Long> {
    Priorita findByDescrizione(String descrizione);
    Priorita findByOrdinamento(Integer ordinamento);
}
