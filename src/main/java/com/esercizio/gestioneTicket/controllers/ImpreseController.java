package com.esercizio.gestioneTicket.controllers;

import com.esercizio.gestioneTicket.errors.RispostaErrore;
import com.esercizio.gestioneTicket.model.DTO.ImpreseDTO;
import com.esercizio.gestioneTicket.services.ImpreseService;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins ="http://localhost:4200")
@RequestMapping("/impresa")
public class ImpreseController {
    @Autowired
    ImpreseService service;

    @PostMapping("/new")
    public ResponseEntity crea(@RequestBody ImpreseDTO impresaToSave){
        try {

            return ResponseEntity.ok(service.crea(impresaToSave));
        }  catch (IllegalArgumentException i){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella creazione dell' impresa'");
            RE.setDettaglio(i.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }   catch (EntityExistsException en){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella creazione dell' impresa'");
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
    public ResponseEntity modifica(@RequestParam Long id, @RequestBody ImpreseDTO impresaDTO){
        try {

            return ResponseEntity.ok(service.modifica(id,impresaDTO));

        }  catch (IllegalArgumentException i){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella modifica dell' impresa'");
            RE.setDettaglio(i.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);

        }   catch (EntityExistsException en) {
            RispostaErrore RE = new RispostaErrore();
            RE.setMessaggio("Errore nella modifica dell' impresa'");
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
            List<ImpreseDTO> lista = service.getAllOrdineId();
            return ResponseEntity.ok(lista);
        } catch (EntityExistsException en) {
            RispostaErrore RE = new RispostaErrore();
            RE.setMessaggio("Errore nella stampa delle imprese");
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

    @GetMapping("/findAllOrdineRagioneSociale")
    public ResponseEntity getAllOrdineRagioneSociale(){
        try{
            List<ImpreseDTO> lista = service.getAllOrdineRagioneSociale();
            return ResponseEntity.ok(lista);
        } catch (EntityExistsException en) {
            RispostaErrore RE = new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca dell'impresa");
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
            ImpreseDTO impresaDTO= service.getById(id);
            return ResponseEntity.ok(impresaDTO);
        } catch (NumberFormatException num){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca dell'impresa tramite ID");
            RE.setDettaglio(num.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        } catch (EntityExistsException en){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca dell'impresa tramite ID");
            RE.setDettaglio(en.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        } catch (IllegalArgumentException ill){
            RispostaErrore RE = new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca dell'impresa tramite ID");
            RE.setDettaglio(ill.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        } catch (Exception e){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("ERRORE NELLA CHIAMATA");
            RE.setDettaglio(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }
    }

    @GetMapping("/findByRagioneSocialeString/{stringa}")
    public ResponseEntity findByRagioneSocialeString(@PathVariable String stringa) {
        try {
            List<ImpreseDTO> lista = service.findByRagioneSocialeString(stringa);
            return ResponseEntity.ok(lista);
        } catch (EntityExistsException en) {
            RispostaErrore RE = new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca dell'impresa' tramite un parametro stringa");
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
