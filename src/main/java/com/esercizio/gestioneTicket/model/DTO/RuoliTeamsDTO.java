package com.esercizio.gestioneTicket.model.DTO;

import com.esercizio.gestioneTicket.model.EntitaGenerale;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RuoliTeamsDTO implements EntitaGeneraleDTO {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String descrizione;
    private Boolean valido;

    public RuoliTeamsDTO(EntitaGenerale entitaGenerale){
        this.id=entitaGenerale.getId();
        this.descrizione=entitaGenerale.getDescrizione();
        this.valido=entitaGenerale.getValido();
    }
}
