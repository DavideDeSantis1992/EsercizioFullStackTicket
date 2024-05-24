package com.esercizio.gestioneTicket.services;

import com.esercizio.gestioneTicket.model.DTO.TasksDTO;

import java.util.List;

public interface TasksService {
    TasksDTO crea (TasksDTO tasksDTO);

    TasksDTO chiusuraTasks (Long id);

    List<TasksDTO> getAllTaskByTicket(Long ticketId);

}
