package com.esercizio.gestioneTicket.model.DTO;

import com.esercizio.gestioneTicket.model.Tasks;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TasksDTO {
    private Long id;
    private TicketsDTO ticket;
    private String descrizione;
    private Boolean statoTask;

    public TasksDTO(Tasks task) {
        if(task != null) {
            this.id = task.getId();
            this.ticket = task.getTicket() != null ? new TicketsDTO(task.getTicket()) : null;
            this.descrizione = task.getDescrizione();
            this.statoTask = task.getStatoTask();
        }
    }
}
