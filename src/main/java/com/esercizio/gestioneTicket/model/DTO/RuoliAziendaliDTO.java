package com.esercizio.gestioneTicket.model.DTO;

import com.esercizio.gestioneTicket.model.EntitaGenerale;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RuoliAziendaliDTO implements EntitaGeneraleDTO{
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String descrizione;
    private Boolean valido;
    public RuoliAziendaliDTO(EntitaGenerale entitaGenerale){
        this.id=entitaGenerale.getId();
        this.descrizione=entitaGenerale.getDescrizione();
        this.valido=entitaGenerale.getValido();
    }
}
