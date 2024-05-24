package com.esercizio.gestioneTicket.model.DTO;

import com.esercizio.gestioneTicket.model.Imprese;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ImpreseDTO {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ragioneSociale;
    private String partitaIva;
    private String codiceFiscale;
    private String indirizzo;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String comune;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String provincia;
    private String cap;
    private String email;
    private String pec;
    private String sdi;

    public ImpreseDTO(Imprese impresa){
        this.id=impresa.getId();
        this.ragioneSociale=impresa.getRagioneSociale();
        this.partitaIva= impresa.getPartitaIva();
        this.codiceFiscale=impresa.getCodiceFiscale();
        this.indirizzo=impresa.getIndirizzo();
        this.comune=impresa.getComune();
        this.provincia=impresa.getProvincia();
        this.cap=impresa.getCap();
        this.email=impresa.getEmail();
        this.pec=impresa.getPec();
        this.sdi=impresa.getSdi();
    }
}
