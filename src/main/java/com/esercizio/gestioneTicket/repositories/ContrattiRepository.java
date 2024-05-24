package com.esercizio.gestioneTicket.repositories;

import com.esercizio.gestioneTicket.model.Contratti;
import com.esercizio.gestioneTicket.model.DTO.ContrattiDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContrattiRepository extends JpaRepository<Contratti,Long> {
    List<Contratti> findByImpresaClienteId(Long impresaClienteId);

    List<Contratti> findByImpresaFornitoreId(Long impresaFornitoreId);
}
