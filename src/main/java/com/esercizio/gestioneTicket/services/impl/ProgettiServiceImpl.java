package com.esercizio.gestioneTicket.services.impl;

import com.esercizio.gestioneTicket.model.Contratti;
import com.esercizio.gestioneTicket.model.DTO.ProgettiDTO;
import com.esercizio.gestioneTicket.model.Progetti;
import com.esercizio.gestioneTicket.repositories.ContrattiRepository;
import com.esercizio.gestioneTicket.repositories.ProgettiRepository;
import com.esercizio.gestioneTicket.services.ProgettiService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProgettiServiceImpl implements ProgettiService {

    @Autowired
    ProgettiRepository progettiRepository;

    @Autowired
    ContrattiRepository contrattiRepository;

    private void validazioneCampi(Contratti contratto,String titolo, String descrizione){
        if(!contrattiRepository.existsById(contratto.getId()) || contratto==null ){
            throw new EntityExistsException("Contratto ID non valido");
        }

        if(titolo==null || titolo.isEmpty()){
            throw new IllegalArgumentException("Titolo mancante");
        }

        if(descrizione==null || descrizione.isEmpty()){
            throw new IllegalArgumentException("Descrizione mancante");
        }
    }

    private Progetti saveObject(ProgettiDTO progettoDTO){
        Contratti contratto = progettoDTO.getContratto()!=null ?
                contrattiRepository.findById(progettoDTO.getContratto().getId()).orElseThrow((
                )->new EntityNotFoundException("Contratto non trovato")):null;

        validazioneCampi(
                contratto,
                progettoDTO.getTitolo(),
                progettoDTO.getDescrizione()
        );

        Progetti nuovoProgetto = new Progetti();

        nuovoProgetto.setContratto(contratto);
        nuovoProgetto.setTitolo(progettoDTO.getTitolo());
        nuovoProgetto.setDescrizione(progettoDTO.getDescrizione());

        return progettiRepository.save(nuovoProgetto);
    }

    private Progetti checkIdAndTake(Long id){
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID non valido");
        }

        if (!progettiRepository.existsById(id)) {
            throw new IllegalArgumentException("ID non presente");
        }

        if (!String.valueOf(id).matches("[0-9]+")) {
            throw new NumberFormatException("ID non valido con caratteri alfanumerici");
        }

        return progettiRepository.findById(id).orElseThrow();
    }

    private Progetti editObject(Long id,ProgettiDTO progettoToEdit){
        Contratti contratto = progettoToEdit.getContratto()!=null ?
                contrattiRepository.findById(progettoToEdit.getContratto().getId()).orElseThrow((
                )->new EntityNotFoundException("Contratto non trovato")):null;

        validazioneCampi(
                contratto,
                progettoToEdit.getTitolo(),
                progettoToEdit.getDescrizione()
        );

        Progetti editProgetto = checkIdAndTake(id);

        editProgetto.setContratto(contratto);
        editProgetto.setTitolo(progettoToEdit.getTitolo());
        editProgetto.setDescrizione(progettoToEdit.getDescrizione());

        return progettiRepository.save(editProgetto);
    }

    private List<ProgettiDTO> lista(){
        if(progettiRepository.findAll().isEmpty()){
            throw new EntityExistsException("Non sono presenti dati");
        } else {
            List<Progetti> list = progettiRepository.findAll();
            List<ProgettiDTO> listDTO = new ArrayList<>();
            for(Progetti progetto : list){
                listDTO.add(new ProgettiDTO(progetto));
            }
            return listDTO;
        }
    }

    @Override
    public ProgettiDTO crea(ProgettiDTO progettoDTO) {
        return new ProgettiDTO(saveObject(progettoDTO));
    }

    @Override
    public ProgettiDTO modifica(Long id, ProgettiDTO progettoDTO) {
        return new ProgettiDTO(editObject(id, progettoDTO));
    }

    @Override
    public List<ProgettiDTO> listaProgettiOrdineTitolo() {
        return lista().stream()
                .sorted(Comparator.comparing(ProgettiDTO::getTitolo, String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(ProgettiDTO::getDescrizione,String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    @Override
    public ProgettiDTO getById(Long id) {
        ProgettiDTO progettoDTO = new ProgettiDTO(checkIdAndTake(id));
        return progettoDTO;
    }



    @Override
    public List<ProgettiDTO> findByTitolo(String titolo) {
        List<Progetti> lista = progettiRepository.findAll();
        List<ProgettiDTO> listaDTO = lista.stream()
                .filter(pro -> pro.getTitolo().toLowerCase().contains(titolo.toLowerCase()))
                .map(ProgettiDTO::new)
                .sorted(Comparator.comparing(ProgettiDTO::getTitolo,String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(ProgettiDTO::getDescrizione, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
        return listaDTO;
    }

    @Override
    public List<ProgettiDTO> findByDescrizione(String descrizione) {
        List<Progetti> lista = progettiRepository.findAll();
        List<ProgettiDTO> listaDTO = lista.stream()
                .filter(pro -> pro.getDescrizione().toLowerCase().contains(descrizione.toLowerCase()))
                .map(ProgettiDTO::new)
                .sorted(Comparator.comparing(ProgettiDTO::getDescrizione,String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(ProgettiDTO::getTitolo, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
        return listaDTO;
    }
}
