package com.esercizio.gestioneTicket.services;

import com.esercizio.gestioneTicket.model.DTO.ImpreseDTO;

import java.util.List;

public interface ImpreseService {
    ImpreseDTO crea(ImpreseDTO impresaDTO);

    ImpreseDTO modifica(Long Id, ImpreseDTO impresaDTO);

    List<ImpreseDTO> getAllOrdineId();

    List<ImpreseDTO> getAllOrdineRagioneSociale();

    ImpreseDTO getById(Long id);

    List<ImpreseDTO> findByRagioneSocialeString(String stringa);

}
