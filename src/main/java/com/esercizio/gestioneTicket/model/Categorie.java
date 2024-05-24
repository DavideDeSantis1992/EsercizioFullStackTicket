package com.esercizio.gestioneTicket.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
@Builder
public class Categorie extends EntitaGenerale{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "categoriaId")
    private Long id;
    @Column(name = "descCategoria", nullable = false, unique = true)
    private String descrizione;
    private Boolean valido;

    public boolean isValido() {
        return this.valido != null && this.valido;
    }
}
