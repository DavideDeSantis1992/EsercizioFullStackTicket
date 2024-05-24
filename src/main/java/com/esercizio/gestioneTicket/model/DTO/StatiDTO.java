package com.esercizio.gestioneTicket.model.DTO;

import com.esercizio.gestioneTicket.model.EntitaGenerale;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StatiDTO implements EntitaGeneraleDTO{
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String descrizione;
    private Boolean valido;

    public StatiDTO(EntitaGenerale entitaGenerale){
        this.id=entitaGenerale.getId();
        this.descrizione=entitaGenerale.getDescrizione();
        this.valido=entitaGenerale.getValido();
    }
}
