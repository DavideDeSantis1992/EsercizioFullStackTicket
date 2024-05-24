package com.esercizio.gestioneTicket.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
@Builder
public class Stati extends EntitaGenerale{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "statoId")
    private Long id;
    @Column(name = "descStato", nullable = false)
    private String descrizione;
    private Boolean valido;

    public boolean isValido() {
        return this.valido != null && this.valido;
    }
}
