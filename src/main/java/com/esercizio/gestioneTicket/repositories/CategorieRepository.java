package com.esercizio.gestioneTicket.repositories;

import com.esercizio.gestioneTicket.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie,Long>, GenericRepository<Categorie, Long> {
}
