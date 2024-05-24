package com.esercizio.gestioneTicket.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Builder
public class Priorita {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "prioritaId")
    private Long id;

    @Column(name = "descPriorità", nullable = false, unique = true)
    private String descrizione;
    @Column(unique = true, nullable = true)
    private Integer ordinamento;

    private Boolean valido;

    public boolean isValido(){
        return this.valido!= null && this.valido;
    }
}
