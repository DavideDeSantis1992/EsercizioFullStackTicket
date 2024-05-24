package com.esercizio.gestioneTicket.repositories;

import com.esercizio.gestioneTicket.model.Imprese;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImpreseRepository extends JpaRepository<Imprese,Long> {
    Imprese findByRagioneSociale(String ragioneSociale);


}
