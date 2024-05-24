package com.esercizio.gestioneTicket.repositories;

import com.esercizio.gestioneTicket.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat,Long> {
}
