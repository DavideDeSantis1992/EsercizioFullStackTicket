package com.esercizio.gestioneTicket.controllers;

import com.esercizio.gestioneTicket.errors.RispostaErrore;
import com.esercizio.gestioneTicket.model.DTO.UtentiDTO;
import com.esercizio.gestioneTicket.services.UtentiService;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins ="http://localhost:4200")
@RequestMapping("/utente")
public class UtentiController {
    @Autowired
    UtentiService service;

    @PostMapping("/new")
    public ResponseEntity crea(@RequestBody UtentiDTO utenteToSave){
        try {

            return ResponseEntity.ok(service.crea(utenteToSave));
        }  catch (IllegalArgumentException i){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella creazione dell' utente");
            RE.setDettaglio(i.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }   catch (EntityExistsException en){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella creazione dell' utente");
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
    public ResponseEntity modifica(@RequestParam Long id, @RequestBody UtentiDTO utenteDTO){
        try {

            return ResponseEntity.ok(service.modifica(id,utenteDTO));

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

    @GetMapping("/findAllOrdineCognome")
    public ResponseEntity getAllOrdineCognome(){
        try{
            List<UtentiDTO> lista = service.getAllOrdineCognome();
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

    @GetMapping("/findById/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        try {
            UtentiDTO utenteDTO= service.getById(id);
            return ResponseEntity.ok(utenteDTO);
        } catch (NumberFormatException num){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca dell' utente");
            RE.setDettaglio(num.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        } catch (EntityExistsException en){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca dell' utente");
            RE.setDettaglio(en.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        } catch (IllegalArgumentException ill){
            RispostaErrore RE = new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca dell' utente");
            RE.setDettaglio(ill.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        } catch (Exception e){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("ERRORE NELLA CHIAMATA");
            RE.setDettaglio(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }
    }

    @GetMapping("/findByCognomeString/{stringa}")
    public ResponseEntity findByCognomeString(@PathVariable String stringa) {
        try {
            List<UtentiDTO> lista = service.findByCognomeString(stringa);
            return ResponseEntity.ok(lista);
        } catch (EntityExistsException en) {
            RispostaErrore RE = new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca dell'utente tramite un parametro stringa");
            RE.setDettaglio(en.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        } catch (Exception e){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("ERRORE NELLA CHIAMATA");
            RE.setDettaglio(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }
    }

    @GetMapping("/findByNomeString/{stringa}")
    public ResponseEntity findByNomeString(@PathVariable String stringa) {
        try {
            List<UtentiDTO> lista = service.findByNomeString(stringa);
            return ResponseEntity.ok(lista);
        } catch (EntityExistsException en) {
            RispostaErrore RE = new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca dell'utente tramite un parametro stringa");
            RE.setDettaglio(en.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        } catch (Exception e){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("ERRORE NELLA CHIAMATA");
            RE.setDettaglio(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }
    }
}
