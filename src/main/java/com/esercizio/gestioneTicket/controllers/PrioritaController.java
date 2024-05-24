package com.esercizio.gestioneTicket.controllers;

import com.esercizio.gestioneTicket.errors.RispostaErrore;
import com.esercizio.gestioneTicket.model.DTO.PrioritaDTO;
import com.esercizio.gestioneTicket.services.PrioritaService;

import jakarta.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins ="http://localhost:4200")
@RequestMapping("/priorita")
public class PrioritaController {
    @Autowired
    PrioritaService service;

    @PostMapping("/new")
    public ResponseEntity crea(@RequestBody PrioritaDTO prioritaToSave){
        try {

            return ResponseEntity.ok(service.crea(prioritaToSave));
        }  catch (IllegalArgumentException i){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella creazione della priorità");
            RE.setDettaglio(i.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }   catch (EntityExistsException en){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella creazione della priorità");
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
    public ResponseEntity modifica(@RequestParam Long id, @RequestBody PrioritaDTO prioritaDTO){
        try {

            return ResponseEntity.ok(service.modifica(id,prioritaDTO));

        }  catch (IllegalArgumentException i){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella modifica della priorità");
            RE.setDettaglio(i.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);

        }   catch (EntityExistsException en) {
            RispostaErrore RE = new RispostaErrore();
            RE.setMessaggio("Errore nella modifica della priorità");
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

    @PostMapping("/switchOrdinamento/id:{id}/numOrdinamento:{ord}")
    public ResponseEntity switchOrdinamento (@PathVariable Long id, @PathVariable Integer ord){
        try {
            List<PrioritaDTO> lista = service.switchOrdinamento(id, ord);
            return ResponseEntity.ok(lista);
        } catch (IllegalArgumentException i){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella modifica della priorità");
            RE.setDettaglio(i.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);

        }   catch (EntityExistsException en) {
            RispostaErrore RE = new RispostaErrore();
            RE.setMessaggio("Errore nella modifica della priorità");
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

    @GetMapping("/findAllOrdineId")
    public ResponseEntity getAllOrdineId(){
        try{
            List<PrioritaDTO> lista = service.getAllOrdineId();
            return ResponseEntity.ok(lista);
        } catch (EntityExistsException en) {
            RispostaErrore RE = new RispostaErrore();
            RE.setMessaggio("Errore nella modifica della priorità");
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

    @GetMapping("/findAllOrdineDescrizione")
    public ResponseEntity getAllOrdineDescrizione(){
        try{
            List<PrioritaDTO> lista = service.getAllOrdineDescrizione();
            return ResponseEntity.ok(lista);
        } catch (EntityExistsException en) {
            RispostaErrore RE = new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca delle priorità");
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

    @PostMapping("/switchValidazione/{id}")
    public ResponseEntity switchValidazione(@PathVariable Long id){

        try{
            service.switchValidazione(id);
            PrioritaDTO prioritaDTO = service.getById(id);
            return ResponseEntity.ok(prioritaDTO);
        } catch (EntityExistsException en){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca del ruolo aziendale tramite ID");
            RE.setDettaglio(en.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        } catch (Exception e){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("ERRORE NELLA CHIAMATA");
            RE.setDettaglio(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }
    }

    @GetMapping("/findByString/{stringa}")
    public ResponseEntity findByString(@PathVariable String stringa) {
        try {
            List<PrioritaDTO> lista = service.findByString(stringa);
            return ResponseEntity.ok(lista);
        } catch (EntityExistsException en) {
            RispostaErrore RE = new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca della priorità tramite un parametro stringa");
            RE.setDettaglio(en.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        } catch (Exception e){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("ERRORE NELLA CHIAMATA");
            RE.setDettaglio(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        try {
            PrioritaDTO prioritaDTO= service.getById(id);
            return ResponseEntity.ok(prioritaDTO);
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
        } catch (Exception e){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("ERRORE NELLA CHIAMATA");
            RE.setDettaglio(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }
    }

    @GetMapping("/findAllTrue")
    public ResponseEntity getAllTrue(){
        try {
            List<PrioritaDTO> lista = service.getAllTrue();
            return ResponseEntity.ok(lista);
        }catch (EntityExistsException en){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca del ruolo aziendale con validazione attiva");
            RE.setDettaglio(en.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }
    }
}
