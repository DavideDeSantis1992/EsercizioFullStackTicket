package com.esercizio.gestioneTicket.repositories;

import com.esercizio.gestioneTicket.model.Stati;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatiRepository extends JpaRepository<Stati,Long>, GenericRepository<Stati, Long>{
}
