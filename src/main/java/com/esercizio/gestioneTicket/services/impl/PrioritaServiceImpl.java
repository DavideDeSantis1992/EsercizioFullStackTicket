package com.esercizio.gestioneTicket.services.impl;

import com.esercizio.gestioneTicket.model.DTO.PrioritaDTO;
import com.esercizio.gestioneTicket.model.Priorita;
import com.esercizio.gestioneTicket.repositories.PrioritaRepository;
import com.esercizio.gestioneTicket.services.PrioritaService;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrioritaServiceImpl  implements PrioritaService {
    @Autowired
    private PrioritaRepository repository;

    private void validaDescrizione(String descrizione){
        if (descrizione == null || descrizione.isEmpty()) {
            throw new IllegalArgumentException("Valore di descrizione priorità mancante o vuoto");
        }

        try {
            Double.parseDouble(descrizione);
            throw new IllegalArgumentException("Il valore della priorità non può essere un numero");
        } catch (NumberFormatException ignored) {}
    }

    private void existingObject(String descrizione){
        Priorita prioritaToFind = repository.findByDescrizione(descrizione);
        if(prioritaToFind != null){
            throw new EntityExistsException("Priorità già presente nel db");
        }
    }

    private Priorita saveObject(PrioritaDTO prioritaToSave) {
        validaDescrizione(prioritaToSave.getDescrizione());
        existingObject(prioritaToSave.getDescrizione());
        Priorita nuovaPriorita = new Priorita();
        nuovaPriorita.setDescrizione(prioritaToSave.getDescrizione());
        nuovaPriorita.setValido(true);
        nuovaPriorita.setOrdinamento(null);
        return repository.save(nuovaPriorita);
    }

    private Priorita checkIdAndTake(Long id){
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID non valido");
        }

        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("ID non presente");
        }

        if (!String.valueOf(id).matches("[0-9]+")) {
            throw new NumberFormatException("ID non valido con caratteri alfanumerici");
        }

        return repository.findById(id).orElseThrow();
    }

    private Priorita editObject(String descrizione, Long id){

        validaDescrizione(descrizione);
        existingObject(descrizione);
        Priorita editPriorita = checkIdAndTake(id);
        editPriorita.setDescrizione(descrizione);
        return repository.save(editPriorita);
    }

    private List<PrioritaDTO> lista(){
        if(repository.findAll().isEmpty()){
            throw new EntityExistsException("Non sono presenti dati");
        } else {
            List<Priorita> prioritaList = repository.findAll();
            List<PrioritaDTO> prioritaDTOList = new ArrayList<>();
            for (Priorita priorita : prioritaList) {
                prioritaDTOList.add(new PrioritaDTO(priorita));
            }
            return prioritaDTOList;
        }
    }


    private List<PrioritaDTO> listaOrdinamento(){
        List<Priorita> list = repository.findAll();
        if(list.isEmpty()){
            throw new EntityExistsException("Non sono presenti dati");
        } else {
            List<PrioritaDTO> result = list.stream()
                    .filter(p -> p.getOrdinamento() != null && p.getOrdinamento() != 0)
                    .map(PrioritaDTO::new)
                    .collect(Collectors.toList());
            Collections.sort(result, Comparator.comparing(dto->dto.getOrdinamento()));
            return result;
        }
    }

    private void switchValidate(Long id) {
        Priorita priorita = checkIdAndTake(id);
        priorita.setValido(!priorita.getValido());
        repository.save(priorita);
    }

    public List<PrioritaDTO> listaPrioritàFindString(String stringa) {
        List<Priorita> listaMadre = repository.findAll();
        List<PrioritaDTO> secondLista= listaMadre.stream()
                .filter(priorita -> priorita.getDescrizione().toLowerCase().contains(stringa.toLowerCase()))
                .map(PrioritaDTO::new)
                .sorted(Comparator.comparing(dto -> dto.getOrdinamento() != null ? dto.getOrdinamento() : Integer.MAX_VALUE))
                .collect(Collectors.toList());
        if (secondLista.isEmpty()){
            throw new EntityExistsException("Priorità non compatibili con la ricerca");
        } else{
            return secondLista;
        }
    }

    public List<PrioritaDTO> listaPrioritaTrue(){
        List<Priorita> listaMadre = repository.findAll();
        List<PrioritaDTO> secondLista= listaMadre.stream()
                .filter(priorita -> priorita.getValido()==true)
                .map(PrioritaDTO::new)
                .sorted(Comparator.comparing(dto -> dto.getOrdinamento() != null ? dto.getOrdinamento() : Integer.MAX_VALUE))
                .collect(Collectors.toList());
        if (secondLista.isEmpty()){
            throw new EntityExistsException("Entità tutte non valide");
        } else{
            return secondLista;
        }
    }


    @Override
    public PrioritaDTO crea(PrioritaDTO prioritaDTO) {
        return new PrioritaDTO(saveObject(prioritaDTO));
    }

    @Override
    public PrioritaDTO modifica(Long id, PrioritaDTO prioritaDTO) {
        return new PrioritaDTO(editObject(prioritaDTO.getDescrizione(), id));
    }

    @Override
    public List<PrioritaDTO> switchOrdinamento(Long id, Integer ord1) {
        Priorita priorita1 = checkIdAndTake(id);
        Priorita priorita2 = repository.findByOrdinamento(ord1);

        if (priorita1 != null && priorita2 != null) {
            Integer ord2 = priorita1.getOrdinamento();

            Integer tempOrdinamento = -1;

            priorita1.setOrdinamento(tempOrdinamento);
            repository.save(priorita1);

            priorita2.setOrdinamento(ord2);
            repository.save(priorita2);

            priorita1.setOrdinamento(ord1);
            repository.save(priorita1);
        }
        if (priorita1 != null && priorita2 == null) {
            priorita1.setOrdinamento(ord1);
            repository.save(priorita1);
        }

        return listaOrdinamento();
    }




    @Override
    public List<PrioritaDTO> getAllOrdineId() {
        return lista();
    }

    @Override
    public List<PrioritaDTO> getAllOrdineDescrizione() {
        return lista().stream()
                .sorted(Comparator.comparing(PrioritaDTO::getDescrizione,
                        String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    @Override
    public List<PrioritaDTO> getAllTrue() {
        return listaPrioritaTrue();
    }

    @Override
    public Boolean switchValidazione(Long id) {
        switchValidate(id);

        return repository.findById(id).get().getValido();
    }

    @Override
    public PrioritaDTO getById(Long id) {

        PrioritaDTO prioritaDTO = new PrioritaDTO(checkIdAndTake(id));
        return prioritaDTO;
    }

    @Override
    public List<PrioritaDTO> findByString(String stringa) {
        return listaPrioritàFindString(stringa);
    }


}
