package com.esercizio.gestioneTicket.services;

import com.esercizio.gestioneTicket.model.Contratti;
import com.esercizio.gestioneTicket.model.DTO.ContrattiDTO;

import java.util.List;

public interface ContrattiService {
    ContrattiDTO crea (ContrattiDTO contrattoDTO);

    ContrattiDTO modifica(Long id, ContrattiDTO contrattoDTO);

    List<ContrattiDTO> getAllOrdineDataInizio();

    ContrattiDTO getById(Long id);

    List<ContrattiDTO> getContrattiByImpresaClienteId(Long impresaClienteId);

    List<ContrattiDTO> getContrattiByImpresaFornitoreId(Long impresaFornitoreId);
}
