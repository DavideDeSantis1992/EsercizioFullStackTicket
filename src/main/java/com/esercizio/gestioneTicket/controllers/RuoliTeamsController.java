package com.esercizio.gestioneTicket.controllers;

import com.esercizio.gestioneTicket.errors.RispostaErrore;
import com.esercizio.gestioneTicket.model.DTO.RuoliTeamsDTO;
import com.esercizio.gestioneTicket.services.RuoliTeamsService;

import jakarta.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins ="http://localhost:4200")
@RequestMapping("/ruoloTeam")
public class RuoliTeamsController {
    @Autowired
    RuoliTeamsService service;

    @PostMapping("/new")
    public ResponseEntity crea(@RequestBody RuoliTeamsDTO ruolo){
        try {
            RuoliTeamsDTO ruoloTeam = service.crea(ruolo);
            return ResponseEntity.ok(ruoloTeam);
        }  catch (IllegalArgumentException i){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella creazione del ruolo team");
            RE.setDettaglio(i.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }   catch (EntityExistsException en){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella creazione del ruolo team");
            RE.setDettaglio(en.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/edit")
    public ResponseEntity modifica(@RequestParam Long id, @RequestBody RuoliTeamsDTO ruoloDTO){
        try {
            RuoliTeamsDTO ruoloModificatoDTO = service.modifica(id,ruoloDTO);
            return ResponseEntity.ok(ruoloModificatoDTO);

        }  catch (IllegalArgumentException i){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella modifica del ruolo aziendale");
            RE.setDettaglio(i.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);

        }   catch (EntityExistsException en) {
            RispostaErrore RE = new RispostaErrore();
            RE.setMessaggio("Errore nella modifica del ruolo aziendale");
            RE.setDettaglio(en.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findAllOrdineId")
    public ResponseEntity<List<RuoliTeamsDTO>>  getAllOrdineId(){
        List<RuoliTeamsDTO> lista = service.getAllOrdineId();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/findAllOrdineDescrizione")
    public ResponseEntity<List<RuoliTeamsDTO>>  getAllOrdineDescrizione(){
        List<RuoliTeamsDTO> lista = service.getAllOrdineDescrizione();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        try {
            RuoliTeamsDTO ruolo = service.getById(id);
            return ResponseEntity.ok(ruolo);
        } catch (NumberFormatException num){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca del ruolo aziendale tramite ID");
            RE.setDettaglio(num.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        } catch (EntityExistsException en){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca del ruolo aziendale tramite ID");
            RE.setDettaglio(en.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        } catch (IllegalArgumentException ill){
            RispostaErrore RE = new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca del ruolo aziendale tramite ID");
            RE.setDettaglio(ill.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }
    }

    @GetMapping("/findAllTrue")
    public ResponseEntity getAllTrue(){
        try {
            List<RuoliTeamsDTO> lista = service.getAllTrue();
            return new ResponseEntity<>(lista, HttpStatus.OK);
        }catch (EntityExistsException en){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca del ruolo aziendale con validazione attiva");
            RE.setDettaglio(en.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }
    }

    @PostMapping("/switchValidazione/{id}")
    public ResponseEntity switchValidazione(@PathVariable Long id){

        try{
            Boolean nuovoStatoValidita = service.switchValidazione(id);
            RuoliTeamsDTO ruolo = service.getById(id);
            return ResponseEntity.ok(ruolo);
        } catch (EntityExistsException en){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca del ruolo aziendale tramite ID");
            RE.setDettaglio(en.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }
    }

    @GetMapping("/findByString/{stringa}")
    public ResponseEntity findByString(@PathVariable String stringa) {
        try {
            List<RuoliTeamsDTO> lista = service.findByString(stringa);
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (EntityExistsException en) {
            RispostaErrore RE = new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca del ruolo aziendale tramite un parametro stringa");
            RE.setDettaglio(en.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }
    }
}
