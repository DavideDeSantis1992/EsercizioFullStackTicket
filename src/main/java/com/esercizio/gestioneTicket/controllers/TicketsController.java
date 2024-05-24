package com.esercizio.gestioneTicket.controllers;

import com.esercizio.gestioneTicket.errors.RispostaErrore;
import com.esercizio.gestioneTicket.model.DTO.TicketsDTO;
import com.esercizio.gestioneTicket.model.DTO.UtentiDTO;
import com.esercizio.gestioneTicket.services.TicketsService;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins ="http://localhost:4200")
@RequestMapping("/ticket")
public class TicketsController {
    @Autowired
    TicketsService service;

    @PostMapping("/new")
    public ResponseEntity crea(@RequestBody TicketsDTO ticket){
        try {

            return ResponseEntity.ok(service.crea(ticket));
        }  catch (IllegalArgumentException i){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella creazione del ticket");
            RE.setDettaglio(i.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }   catch (EntityExistsException en){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella creazione del ticket");
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
    public ResponseEntity modifica(@RequestParam Long id, @RequestBody TicketsDTO ticket){
        try {

            return ResponseEntity.ok(service.modifica(id,ticket));

        }  catch (IllegalArgumentException i){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella modifica del ticket");
            RE.setDettaglio(i.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);

        }   catch (EntityExistsException en) {
            RispostaErrore RE = new RispostaErrore();
            RE.setMessaggio("Errore nella modifica del ticket");
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

    @GetMapping("/getById/{id}")
    public ResponseEntity getById(@PathVariable Long id){
        try{
            TicketsDTO ticket = service.getById(id);
            return ResponseEntity.ok(ticket);
        } catch (NumberFormatException num){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca del ticket");
            RE.setDettaglio(num.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        } catch (EntityExistsException en){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca del ticket");
            RE.setDettaglio(en.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        } catch (IllegalArgumentException ill){
            RispostaErrore RE = new RispostaErrore();
            RE.setMessaggio("Errore nella ricerca del ticket");
            RE.setDettaglio(ill.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        } catch (Exception e){
            RispostaErrore RE =  new RispostaErrore();
            RE.setMessaggio("ERRORE NELLA CHIAMATA");
            RE.setDettaglio(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RE);
        }
    }

    @GetMapping("/getAllOrdineId")
    public ResponseEntity<Page<TicketsDTO>> getAllOrdineId(@RequestParam (required = false) Optional<Integer> page ,
                                                           @RequestParam (required = false) Optional<Integer> size) {
            Pageable paginata = PageRequest.of(page.get(), size.get());
            Page<TicketsDTO> pagina = service.getAllOrdineId(paginata);
            return ResponseEntity.ok(pagina);
    }

    @GetMapping("/getAllOrdineProgetto")
    public ResponseEntity<Page<TicketsDTO>> getAllOrdineProgetto(@RequestParam (required = false) Optional<Integer> page ,
                                                                 @RequestParam (required = false) Optional<Integer> size){
        Pageable paginata = PageRequest.of(page.get(), size.get());
        Page<TicketsDTO> pagina = service.getAllOrdineProgetto(paginata);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/getAllOrdineTitolo")
    public ResponseEntity<Page<TicketsDTO>> getAllOrdineTitolo(@RequestParam (required = false) Optional<Integer> page ,
                                                               @RequestParam (required = false) Optional<Integer> size){
        Pageable paginata = PageRequest.of(page.get(), size.get());
        Page<TicketsDTO> pagina = service.getAllOrdineTitolo(paginata);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/getAllOrdineDescrizione")
    public ResponseEntity<Page<TicketsDTO>> getAllOrdineDescrizione(@RequestParam (required = false) Optional<Integer> page ,
                                                                    @RequestParam (required = false) Optional<Integer> size){
        Pageable paginata = PageRequest.of(page.get(), size.get());
        Page<TicketsDTO> pagina = service.getAllOrdineDescrizione(paginata);
        return ResponseEntity.ok(pagina);
    }
}
