package com.esercizio.gestioneTicket.controllers;

import com.esercizio.gestioneTicket.errors.RispostaErrore;
import com.esercizio.gestioneTicket.model.DTO.CategorieDTO;
import com.esercizio.gestioneTicket.services.CategorieService;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins ="http://localhost:4200")
@RequestMapping("/categoria")
public class CategoriaController {
    @Autowired
    CategorieService service;

    @PostMapping("/new")
    public ResponseEntity crea(@RequestBody CategorieDTO parametroCategoriaDTO){
        try {
            CategorieDTO categoriaDTO = service.crea(parametroCategoriaDTO);
            return ResponseEntity.ok(categoriaDTO);
        } catch (IllegalArgumentException i){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella creazione della categoria");
            RE.setDettaglio(i.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        } catch (EntityExistsException en){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella creazione della categoria");
            RE.setDettaglio(en.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/edit")
    public ResponseEntity modifica(@RequestParam Long id, @RequestBody CategorieDTO categoriaDTO){
        try {
            CategorieDTO categoriaModificataDTO = service.modifica(id,categoriaDTO);
            return ResponseEntity.ok(categoriaModificataDTO);

        }  catch (IllegalArgumentException i){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella modifica della categoria");
            RE.setDettaglio(i.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);

        }   catch (EntityExistsException en){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella modifica della categoria");
            RE.setDettaglio(en.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }
    }

    @GetMapping("/findAllOrdineId")
    public ResponseEntity<List<CategorieDTO>>  getAllOrdineId(){
        List<CategorieDTO> lista = service.getAllOrdineId();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/findAllOrdineDescrizione")
    public ResponseEntity<List<CategorieDTO>>  getAllOrdineDescrizione(){
        List<CategorieDTO> lista = service.getAllOrdineDescrizione();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        try {
            CategorieDTO categoria = service.getById(id);
            return ResponseEntity.ok(categoria);
        } catch (NumberFormatException num){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca della categoria tramite ID");
            RE.setDettaglio(num.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        } catch (EntityExistsException en){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca della categoria tramite ID");
            RE.setDettaglio(en.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }
    }

    @GetMapping("/findAllTrue")
    public ResponseEntity getAllTrue(){
        try {
            List<CategorieDTO> lista = service.getAllTrue();
            return new ResponseEntity<>(lista, HttpStatus.OK);
        }catch (EntityExistsException en){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca delle categorie con validazione attiva");
            RE.setDettaglio(en.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }
    }

    @PostMapping("/switchValidazione/{id}")
    public ResponseEntity switchValidazione(@PathVariable Long id){

        try{
            Boolean nuovoStatoValidita = service.switchValidazione(id);
            CategorieDTO categoria = service.getById(id);
            return ResponseEntity.ok(categoria);
        } catch (EntityExistsException en){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca della categoria tramite ID");
            RE.setDettaglio(en.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }
    }

    @GetMapping("/findByString/{stringa}")
    public ResponseEntity findByString(@PathVariable String stringa) {
        try {
            List<CategorieDTO> lista = service.findByString(stringa);
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
