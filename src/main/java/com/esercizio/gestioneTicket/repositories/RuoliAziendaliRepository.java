package com.esercizio.gestioneTicket.repositories;

import com.esercizio.gestioneTicket.model.RuoliAziendali;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuoliAziendaliRepository extends JpaRepository<RuoliAziendali,Long>, GenericRepository<RuoliAziendali, Long>  {
}
