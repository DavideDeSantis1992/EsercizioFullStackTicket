package com.esercizio.gestioneTicket.controllers;

import com.esercizio.gestioneTicket.errors.RispostaErrore;
import com.esercizio.gestioneTicket.model.DTO.TeamsDTO;
import com.esercizio.gestioneTicket.model.DTO.UtentiDTO;
import com.esercizio.gestioneTicket.services.TeamsService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins ="http://localhost:4200")
@RequestMapping("/team")
public class TeamsController {
    @Autowired
    TeamsService service;

    @PostMapping("/new")
    public ResponseEntity crea(@RequestBody TeamsDTO team){
        try {

            return ResponseEntity.ok(service.crea(team));
        }  catch (IllegalArgumentException i){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella creazione del team");
            RE.setDettaglio(i.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }   catch (EntityExistsException en){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella creazione del team");
            RE.setDettaglio(en.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }
        catch (Exception e){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("SALVATAGGIO FALLITO, ERRORE NELLA CHIAMATA");
            RE.setDettaglio(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }
    }

    @PutMapping("/edit")
    public ResponseEntity modifica(@RequestParam Long id, @RequestBody TeamsDTO team){
        try {

            return ResponseEntity.ok(service.modifica(id,team));

        }  catch (IllegalArgumentException i){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella modifica dell' utente");
            RE.setDettaglio(i.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);

        }   catch (EntityExistsException en) {
            RispostaErrore RE = new RispostaErrore();
            RE.setMessaggio("Errore nella modifica dell' utente");
            RE.setDettaglio(en.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }
        catch (Exception e){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("MODIFICA FALLITA, ERRORE NELLA CHIAMATA");
            RE.setDettaglio(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }
    }

    @GetMapping("/listaTeamByProgetto")
    public ResponseEntity listaTeamByProgetto(@RequestParam Long id){
        try{
            List<TeamsDTO> lista = service.listaTeamByProgetto(id);
            return ResponseEntity.ok(lista);
        } catch (EntityNotFoundException en) {
            RispostaErrore RE = new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca dell' utente");
            RE.setDettaglio(en.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }
        catch (Exception e){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("ERRORE NELLA CHIAMATA");
            RE.setDettaglio(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }
    }

    @GetMapping("/listaAllTeam")
    public ResponseEntity listaTeamByProgetto(){
        try{
            List<TeamsDTO> lista = service.listaAllTeam();
            return ResponseEntity.ok(lista);
        } catch (EntityExistsException en) {
            RispostaErrore RE = new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca dell' utente");
            RE.setDettaglio(en.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }
        catch (Exception e){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("ERRORE NELLA CHIAMATA");
            RE.setDettaglio(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }
    }
}
