package com.esercizio.gestioneTicket.model.DTO;

import com.esercizio.gestioneTicket.model.Chat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ChatDTO {
    private Long id;
    private TicketsDTO ticket;
    private TeamsDTO team;
    private String messaggio;
    private LocalDate datamessaggio;

    public ChatDTO(Chat chat) {
        if(chat != null){
            this.id = chat.getId();
            this.ticket = chat.getTicket()!=null?new TicketsDTO(chat.getTicket()):null;
            this.team = chat.getTeam()!=null?new TeamsDTO(chat.getTeam()):null;
            this.messaggio = chat.getMessaggio();
            this.datamessaggio = chat.getDataMessaggio();
        }
    }
}
