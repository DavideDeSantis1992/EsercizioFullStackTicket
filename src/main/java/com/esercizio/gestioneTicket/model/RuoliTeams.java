package com.esercizio.gestioneTicket.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
@Builder
public class RuoliTeams extends EntitaGenerale{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ruoloTeamId")
    private Long id;
    @Column(name = "descRuoloTeam", nullable = false, unique = true)
    private String descrizione;
    private Boolean valido;

    public boolean isValido() {
        return this.valido != null && this.valido;
    }
}
