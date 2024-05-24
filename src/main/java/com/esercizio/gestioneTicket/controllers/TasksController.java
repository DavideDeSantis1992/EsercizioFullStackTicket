package com.esercizio.gestioneTicket.controllers;

import com.esercizio.gestioneTicket.errors.RispostaErrore;
import com.esercizio.gestioneTicket.model.DTO.TasksDTO;
import com.esercizio.gestioneTicket.model.DTO.UtentiDTO;
import com.esercizio.gestioneTicket.services.TasksService;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins ="http://localhost:4200")
@RequestMapping("/task")
public class TasksController{
    @Autowired
    TasksService service;

    @PostMapping("/new")
    public ResponseEntity crea(@RequestBody TasksDTO task){
        try {

            return ResponseEntity.ok(service.crea(task));
        }  catch (IllegalArgumentException i){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella creazione del task");
            RE.setDettaglio(i.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }   catch (EntityExistsException en){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella creazione del task");
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

    @PostMapping("/chiusuraTask/{id}")
    public ResponseEntity chiusuraTask(@PathVariable Long id){
        try {

            return ResponseEntity.ok(service.chiusuraTasks(id));
        }  catch (IllegalArgumentException i){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca del task");
            RE.setDettaglio(i.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }   catch (EntityExistsException en){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca del task");
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

    @GetMapping("/getAllTaskByTicket/{id}")
    public ResponseEntity getAllTaskByTicket(@PathVariable Long id){
        try {

            return ResponseEntity.ok(service.getAllTaskByTicket(id));
        }  catch (IllegalArgumentException i){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca del task");
            RE.setDettaglio(i.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }   catch (EntityExistsException en){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca del task");
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
}
