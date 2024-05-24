package com.esercizio.gestioneTicket.repositories;

import com.esercizio.gestioneTicket.model.Utenti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UtentiRepository extends JpaRepository<Utenti,Long>, JpaSpecificationExecutor<Utenti> {
    Utenti findByCognome(String cognome);

    Utenti findByEmail(String email);


}
