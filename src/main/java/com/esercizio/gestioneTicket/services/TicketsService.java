package com.esercizio.gestioneTicket.services;

import com.esercizio.gestioneTicket.model.DTO.TicketsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TicketsService {
    TicketsDTO crea (TicketsDTO ticketsDTO);

    TicketsDTO modifica (Long id, TicketsDTO ticketsDTO);

    Page<TicketsDTO> getAllOrdineId(Pageable pageable);



    TicketsDTO getById(Long id);

    Page <TicketsDTO> getAllOrdineProgetto(Pageable pageable);

    Page<TicketsDTO> getAllOrdineTitolo(Pageable pageable);

    Page<TicketsDTO> getAllOrdineDescrizione(Pageable pageable);


}
