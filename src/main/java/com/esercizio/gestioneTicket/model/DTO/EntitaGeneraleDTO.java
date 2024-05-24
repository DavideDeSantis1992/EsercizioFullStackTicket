package com.esercizio.gestioneTicket.model.DTO;

import com.esercizio.gestioneTicket.model.EntitaGenerale;
import lombok.*;


public interface EntitaGeneraleDTO {
    Long getId();
    void setId(Long id);

    String getDescrizione();
    void setDescrizione(String descrizione);

    Boolean getValido();
    void setValido(Boolean valido);


}
