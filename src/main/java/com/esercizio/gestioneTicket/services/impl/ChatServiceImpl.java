package com.esercizio.gestioneTicket.services.impl;

import com.esercizio.gestioneTicket.model.Chat;
import com.esercizio.gestioneTicket.model.DTO.ChatDTO;
import com.esercizio.gestioneTicket.model.DTO.ProgettiDTO;
import com.esercizio.gestioneTicket.model.DTO.UtentiDTO;
import com.esercizio.gestioneTicket.model.Teams;
import com.esercizio.gestioneTicket.model.Tickets;
import com.esercizio.gestioneTicket.repositories.ChatRepository;
import com.esercizio.gestioneTicket.repositories.TeamsRepository;
import com.esercizio.gestioneTicket.repositories.TicketsRepository;
import com.esercizio.gestioneTicket.services.ChatService;
import com.esercizio.gestioneTicket.services.TicketsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    TicketsRepository ticketsRepository;
    @Autowired
    TeamsRepository teamsRepository;
    @Autowired
    ChatRepository chatRepository;

    private Chat saveObject(ChatDTO chatDTO){
        Tickets tickets = chatDTO.getTicket()!=null ?
                ticketsRepository.findById(chatDTO.getTicket().getId()).orElseThrow((
                )->new EntityNotFoundException("Ticket non trovato")):null;

        Teams team = chatDTO.getTeam()!=null ?
                teamsRepository.findById(chatDTO.getTeam().getId()).orElseThrow((
                )->new EntityNotFoundException("Team non trovato")):null;

        Chat nuovaChat = new Chat();
        LocalDate data = LocalDate.now();

        nuovaChat.setTicket(tickets);
        nuovaChat.setTeam(team);
        nuovaChat.setMessaggio(chatDTO.getMessaggio());
        nuovaChat.setDataMessaggio(data);

        return chatRepository.save(nuovaChat);
    }

    @Override
    public ChatDTO crea(ChatDTO chatDTO) {
        return new ChatDTO(saveObject(chatDTO));
    }


    @Override
    public List<ChatDTO> listaChatProgetto(ProgettiDTO progettoDTO) {
        return null;
    }
}
