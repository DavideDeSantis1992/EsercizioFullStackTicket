package com.esercizio.gestioneTicket.model.DTO;

import com.esercizio.gestioneTicket.model.Progetti;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProgettiDTO {
    private Long id;
    private ContrattiDTO contratto;
    private String titolo;
    private String descrizione;

    public ProgettiDTO(Progetti progetto){
        this.id = progetto.getId();
        this.contratto = progetto.getContratto()!=null ? new ContrattiDTO(progetto.getContratto()):null;
        this.titolo = progetto.getTitolo();
        this.descrizione = progetto.getDescrizione();
    }
}
