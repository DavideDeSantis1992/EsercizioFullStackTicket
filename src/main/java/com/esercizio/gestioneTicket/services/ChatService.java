package com.esercizio.gestioneTicket.services;

import com.esercizio.gestioneTicket.model.DTO.ChatDTO;
import com.esercizio.gestioneTicket.model.DTO.ProgettiDTO;

import java.util.List;

public interface ChatService {
    ChatDTO crea (ChatDTO chatDTO);
    List<ChatDTO> listaChatProgetto (ProgettiDTO progettoDTO);


}
