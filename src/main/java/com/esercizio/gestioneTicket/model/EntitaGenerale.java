package com.esercizio.gestioneTicket.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntitaGenerale {
    private Long id;
    private String descrizione;
    private Boolean valido;


}

