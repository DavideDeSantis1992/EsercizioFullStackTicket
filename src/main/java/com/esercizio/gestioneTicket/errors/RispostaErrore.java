package com.esercizio.gestioneTicket.errors;

import lombok.Data;

@Data
public class RispostaErrore {
    private String messaggio;
    private String dettaglio;
}
