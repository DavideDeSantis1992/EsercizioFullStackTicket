package com.esercizio.gestioneTicket.repositories;

import com.esercizio.gestioneTicket.model.RuoliTeams;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuoliTeamsRepository extends JpaRepository<RuoliTeams,Long>, GenericRepository<RuoliTeams, Long> {
}
