package com.esercizio.gestioneTicket.model.DTO;

import com.esercizio.gestioneTicket.model.Contratti;
import com.esercizio.gestioneTicket.model.Imprese;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
// @AllArgsConstructor
@ToString
public class ContrattiDTO {
    private Long id;
    private ImpreseDTO impresaFornitore;
    private ImpreseDTO impresaCliente;
    private Double importo;
    private LocalDate dataInizio;
    private LocalDate dataFine;

    public ContrattiDTO(Contratti contratti){
        if(contratti != null){
            this.id=contratti.getId();
            this.impresaFornitore=contratti.getImpresaFornitore() != null ?
                    new ImpreseDTO(contratti.getImpresaFornitore()):null;
            this.impresaCliente=contratti.getImpresaCliente() != null ?
                    new ImpreseDTO(contratti.getImpresaCliente()):null;
            this.importo= contratti.getImporto();
            this.dataInizio=contratti.getDataInizio();
            this.dataFine=contratti.getDataFine();
        }
    }
}
