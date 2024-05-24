package com.esercizio.gestioneTicket.model.DTO;

import com.esercizio.gestioneTicket.model.Utenti;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@ToString
public class UtentiDTO {
    private Long id;
    private ImpreseDTO impresa;
    private String nome;
    private String cognome;
    private RuoliAziendaliDTO ruoloAziendale;
    private String email;
    private String password;


    public UtentiDTO(Utenti utenti) {
        if (utenti != null) {
            this.id = utenti.getId();
            this.impresa = utenti.getImpresa() != null ? new ImpreseDTO(utenti.getImpresa()) : null;
            this.nome = utenti.getNome();
            this.cognome = utenti.getCognome();
            this.ruoloAziendale = utenti.getRuoloAziendale() != null ? new RuoliAziendaliDTO(utenti.getRuoloAziendale()) : null;
            this.email = utenti.getEmail();
            this.password = utenti.getPassword();
        }
    }



}
