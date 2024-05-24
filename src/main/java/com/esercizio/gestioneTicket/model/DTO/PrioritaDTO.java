package com.esercizio.gestioneTicket.model.DTO;

import com.esercizio.gestioneTicket.model.Priorita;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PrioritaDTO {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String descrizione;
    private Integer ordinamento;
    private Boolean valido;

    public PrioritaDTO(Priorita priorita){
        this.id=priorita.getId();
        this.descrizione=priorita.getDescrizione();
        this.ordinamento=priorita.getOrdinamento();
        this.valido=priorita.getValido();
    }



}
