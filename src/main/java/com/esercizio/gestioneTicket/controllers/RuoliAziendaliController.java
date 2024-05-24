package com.esercizio.gestioneTicket.controllers;

import com.esercizio.gestioneTicket.errors.RispostaErrore;
import com.esercizio.gestioneTicket.model.DTO.RuoliAziendaliDTO;
import com.esercizio.gestioneTicket.services.RuoliAziendaliService;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins ="http://localhost:4200")
@RequestMapping("/ruoloAziendale")
public class RuoliAziendaliController {

    @Autowired
    RuoliAziendaliService service;

    @PostMapping("/new")
    public ResponseEntity crea(@RequestBody RuoliAziendaliDTO ruolo){
        try {
            RuoliAziendaliDTO ruoloAziendale = service.crea(ruolo);
            return ResponseEntity.ok(ruoloAziendale);
        } catch (IllegalArgumentException i){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella creazione del ruolo aziendale");
            RE.setDettaglio(i.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        } catch (EntityExistsException en){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella creazione del ruolo aziendale");
            RE.setDettaglio(en.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }
    }

    @PutMapping("/edit")
    public ResponseEntity modifica(@RequestParam Long id,@RequestBody RuoliAziendaliDTO ruoloDTO){
        try {
            RuoliAziendaliDTO ruoloModificatoDTO = service.modifica(id,ruoloDTO);
            return ResponseEntity.ok(ruoloModificatoDTO);

        }  catch (IllegalArgumentException i){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella modifica del ruolo aziendale");
            RE.setDettaglio(i.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);

        }   catch (EntityExistsException en){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella modifica del ruolo aziendale");
            RE.setDettaglio(en.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }


        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findAllOrdineId")
    public ResponseEntity<List<RuoliAziendaliDTO>>  getAllOrdineId(){
        List<RuoliAziendaliDTO> lista = service.getAllOrdineId();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/findAllOrdineDescrizione")
    public ResponseEntity<List<RuoliAziendaliDTO>>  getAllOrdineDescrizione(){
        List<RuoliAziendaliDTO> lista = service.getAllOrdineDescrizione();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        try {
            RuoliAziendaliDTO ruolo = service.getById(id);
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
        }
    }

    @GetMapping("/findAllTrue")
    public ResponseEntity getAllTrue(){
        try {
            List<RuoliAziendaliDTO> lista = service.getAllTrue();
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
            RuoliAziendaliDTO ruolo = service.getById(id);
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
            List<RuoliAziendaliDTO> lista = service.findByString(stringa);
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (EntityExistsException en) {
            RispostaErrore RE = new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca del ruolo aziendale tramite un parametro stringa");
            RE.setDettaglio(en.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        } catch (NumberFormatException num){
            RispostaErrore RE = new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca del ruolo aziendale tramite un parametro stringa");
            RE.setDettaglio(num.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }
    }

}
