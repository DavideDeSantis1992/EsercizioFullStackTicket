package com.esercizio.gestioneTicket.repositories;

import com.esercizio.gestioneTicket.model.RuoliTeams;

import java.util.List;

public interface GenericRepository<T, ID> {
    T findByDescrizione(String descrizione);
    List<T> findByValidoTrue();

    List<T> findByDescrizioneContainingIgnoreCase(String descrizione);
}
