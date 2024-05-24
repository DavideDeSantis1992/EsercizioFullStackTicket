package com.esercizio.gestioneTicket.controllers;

import com.esercizio.gestioneTicket.errors.RispostaErrore;
import com.esercizio.gestioneTicket.model.DTO.StatiDTO;
import com.esercizio.gestioneTicket.services.StatiService;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins ="http://localhost:4200")
@RequestMapping("/stato")
public class StatiController {
    @Autowired
    StatiService service;

    @PostMapping("/new")
    public ResponseEntity crea(@RequestBody StatiDTO parametroStatoDTO){
        try {
            StatiDTO statoDTO = service.crea(parametroStatoDTO);
            return ResponseEntity.ok(statoDTO);
        } catch (IllegalArgumentException i){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella creazione dello stato");
            RE.setDettaglio(i.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        } catch (EntityExistsException en){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella creazione dello stato");
            RE.setDettaglio(en.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/edit")
    public ResponseEntity modifica(@RequestParam Long id, @RequestBody StatiDTO statoDTO){
        try {
            StatiDTO statoModificataDTO = service.modifica(id,statoDTO);
            return ResponseEntity.ok(statoModificataDTO);

        }  catch (IllegalArgumentException i){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella modifica dello stato");
            RE.setDettaglio(i.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);

        }   catch (EntityExistsException en){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella modifica dello stato");
            RE.setDettaglio(en.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }
    }

    @GetMapping("/findAllOrdineId")
    public ResponseEntity<List<StatiDTO>> getAllOrdineId(){
        List<StatiDTO> lista = service.getAllOrdineId();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/findAllOrdineDescrizione")
    public ResponseEntity<List<StatiDTO>> getAllOrdineDescrizione(){
        List<StatiDTO> lista = service.getAllOrdineDescrizione();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        try {
            StatiDTO stato = service.getById(id);
            return ResponseEntity.ok(stato);
        } catch (NumberFormatException num){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca dello stato tramite ID");
            RE.setDettaglio(num.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        } catch (EntityExistsException en){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca dello stato tramite ID");
            RE.setDettaglio(en.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }
    }

    @GetMapping("/findAllTrue")
    public ResponseEntity getAllTrue(){
        try {
            List<StatiDTO> lista = service.getAllTrue();
            return new ResponseEntity<>(lista, HttpStatus.OK);
        }catch (EntityExistsException en){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca dello stato con validazione attiva");
            RE.setDettaglio(en.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }
    }

    @PostMapping("/switchValidazione/{id}")
    public ResponseEntity switchValidazione(@PathVariable Long id){

        try{
            Boolean nuovoStatoValidita = service.switchValidazione(id);
            StatiDTO stato = service.getById(id);
            return ResponseEntity.ok(stato);
        } catch (EntityExistsException en){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca dello stato tramite ID");
            RE.setDettaglio(en.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }
    }

    @GetMapping("/findByString/{stringa}")
    public ResponseEntity findByString(@PathVariable String stringa) {
        try {
            List<StatiDTO> lista = service.findByString(stringa);
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (EntityExistsException en) {
            RispostaErrore RE = new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca dello stato tramite un parametro stringa");
            RE.setDettaglio(en.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        } catch (NumberFormatException num){
            RispostaErrore RE = new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca dello stato tramite un parametro stringa");
            RE.setDettaglio(num.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }
    }
}
