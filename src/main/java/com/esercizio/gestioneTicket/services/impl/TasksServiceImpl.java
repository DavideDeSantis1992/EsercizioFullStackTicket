package com.esercizio.gestioneTicket.services.impl;

import com.esercizio.gestioneTicket.model.DTO.TasksDTO;
import com.esercizio.gestioneTicket.model.Tasks;
import com.esercizio.gestioneTicket.repositories.TasksRepository;
import com.esercizio.gestioneTicket.repositories.TicketsRepository;
import com.esercizio.gestioneTicket.services.TasksService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TasksServiceImpl implements TasksService {

    @Autowired
    TasksRepository tasksRepository;

    @Autowired
    TicketsRepository ticketsRepository;

    private Tasks checkIdAndTake (Long id){
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID non valido");
        }

        if (!tasksRepository.existsById(id)) {
            throw new IllegalArgumentException("ID non presente");
        }

        if (!String.valueOf(id).matches("[0-9]+")) {
            throw new NumberFormatException("ID non valido con caratteri alfanumerici");
        }

        return tasksRepository.findById(id).orElseThrow();
    }

    @Override
    public TasksDTO crea(TasksDTO tasksDTO) {

        Tasks nuovoTask = new Tasks();

        nuovoTask.setTicket(tasksDTO.getTicket()!=null ?
                ticketsRepository.findById(tasksDTO.getTicket().getId()).orElseThrow((
                )->new EntityNotFoundException("Ticket non trovato")):null);
        nuovoTask.setDescrizione(tasksDTO.getDescrizione());
        nuovoTask.setStatoTask(Boolean.TRUE);

        return new TasksDTO(tasksRepository.save(nuovoTask));
    }

    @Override
    public TasksDTO chiusuraTasks(Long id) {
        Tasks task = checkIdAndTake(id);
        task.setStatoTask(!task.getStatoTask());
        tasksRepository.save(task);
        return new TasksDTO(task);

    }

    @Override
    public List<TasksDTO> getAllTaskByTicket(Long ticketId) {
        if (ticketId == null || ticketId <= 0) {
            throw new IllegalArgumentException("ID del ticket non valido");
        }

        List<Tasks> tasks = tasksRepository.findByTicketId(ticketId);
        if (tasks.isEmpty()) {
            throw new EntityNotFoundException("Nessun task trovato per il ticket con ID " + ticketId);
        }

        return tasks.stream()
                .sorted(Comparator.comparing(Tasks::getId))
                .map(TasksDTO::new)
                .collect(Collectors.toList());
    }
}
