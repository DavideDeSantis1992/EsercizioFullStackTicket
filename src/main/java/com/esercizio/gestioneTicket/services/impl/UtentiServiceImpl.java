package com.esercizio.gestioneTicket.services.impl;

import com.esercizio.gestioneTicket.model.DTO.UtentiDTO;
import com.esercizio.gestioneTicket.model.Imprese;
import com.esercizio.gestioneTicket.model.RuoliAziendali;
import com.esercizio.gestioneTicket.model.Utenti;
import com.esercizio.gestioneTicket.repositories.ImpreseRepository;
import com.esercizio.gestioneTicket.repositories.RuoliAziendaliRepository;
import com.esercizio.gestioneTicket.repositories.UtentiRepository;
import com.esercizio.gestioneTicket.services.UtentiService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UtentiServiceImpl implements UtentiService {
    @Autowired
    UtentiRepository utentiRepository;
    @Autowired
    ImpreseRepository impreseRepository;

    @Autowired
    RuoliAziendaliRepository ruoliAziendaliRepository;

    private void validazioneCampi(Imprese impresa, String nome, String cognome, RuoliAziendali ruoloAziendale, String email,
                                  String password){
        if(!impreseRepository.existsById(impresa.getId())|| impresa==null){
            throw new EntityExistsException("Impresa ID non valida");
        }

        if(nome==null||nome.isEmpty()||nome.matches("\\d+")){
            throw new IllegalArgumentException("Nome mancante");
        }

        if(cognome==null||cognome.isEmpty()||cognome.matches("\\d+")){
            throw new IllegalArgumentException("Cognome mancante");
        }

        if(!ruoliAziendaliRepository.existsById(ruoloAziendale.getId())|| ruoloAziendale==null){
            throw new EntityExistsException("Ruolo Aziendale ID non valida");
        }

        if (email == null || email.isEmpty() || !email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Email non valida");
        }
        if (password==null || password.isEmpty()){
            throw new IllegalArgumentException("Password mancante");
        }
    }

    private void existingObject(String email){
        Utenti utenteToFind = utentiRepository.findByEmail(email);
        if(utenteToFind!=null){
            throw new EntityExistsException("Email già presente");
        }
    }

    private Utenti saveObject(UtentiDTO utenteDTO){
        validazioneCampi(
                utenteDTO.getImpresa()!=null ?
                        impreseRepository.findById(utenteDTO.getImpresa().getId()).orElseThrow((
                        )->new EntityNotFoundException("Impresa non trovata")):null,
                utenteDTO.getNome(),
                utenteDTO.getCognome(),
                utenteDTO.getRuoloAziendale()!=null ?
                        ruoliAziendaliRepository.findById(utenteDTO.getRuoloAziendale().getId()).orElseThrow((
                        )->new EntityNotFoundException("Ruolo Aziendale non trovata")):null,
                utenteDTO.getEmail(),
                utenteDTO.getPassword());

        existingObject(utenteDTO.getEmail());

        Utenti nuovoUtente = new Utenti();

        nuovoUtente.setImpresa(utenteDTO.getImpresa()!=null ?
                impreseRepository.findById(utenteDTO.getImpresa().getId()).orElseThrow((
                )->new EntityNotFoundException("Impresa non trovata")):null);
        nuovoUtente.setNome(utenteDTO.getNome());
        nuovoUtente.setCognome(utenteDTO.getCognome());
        nuovoUtente.setRuoloAziendale(utenteDTO.getRuoloAziendale()!=null ?
                ruoliAziendaliRepository.findById(utenteDTO.getRuoloAziendale().getId()).orElseThrow((
                )->new EntityNotFoundException("Ruolo Aziendale non trovata")):null);
        nuovoUtente.setEmail(utenteDTO.getEmail());
        nuovoUtente.setPassword(utenteDTO.getPassword());

        return utentiRepository.save(nuovoUtente);
    }

    private Utenti checkIdAndTake(Long id){
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID non valido");
        }

        if (!utentiRepository.existsById(id)) {
            throw new IllegalArgumentException("ID non presente");
        }

        if (!String.valueOf(id).matches("[0-9]+")) {
            throw new NumberFormatException("ID non valido con caratteri alfanumerici");
        }

        return utentiRepository.findById(id).orElseThrow();
    }

    private void checkEmailForUser(Long id, String email){
        Utenti utente = utentiRepository.findByEmail(email);
        if (utente != null && !utente.getId().equals(id)){
            throw new EntityExistsException("Email già presente ad un altra utenza");
        }
    }

    private Utenti editObject(Long id, UtentiDTO utenteToEdit){
        validazioneCampi(utenteToEdit.getImpresa()!=null ?
                        impreseRepository.findById(utenteToEdit.getImpresa().getId()).orElseThrow((
                        )->new EntityNotFoundException("Impresa non trovata")):null,
                utenteToEdit.getNome(),
                utenteToEdit.getCognome(),
                utenteToEdit.getRuoloAziendale()!=null ?
                        ruoliAziendaliRepository.findById(utenteToEdit.getRuoloAziendale().getId()).orElseThrow((
                        )->new EntityNotFoundException("Ruolo Aziendale non trovata")):null,
                utenteToEdit.getEmail(),
                utenteToEdit.getPassword());

        checkEmailForUser(id,utenteToEdit.getEmail());

        Utenti editUtente = checkIdAndTake(id);

        editUtente.setImpresa(utenteToEdit.getImpresa()!=null ?
                impreseRepository.findById(utenteToEdit.getImpresa().getId()).orElseThrow((
                )->new EntityNotFoundException("Impresa non trovata")):null);
        editUtente.setNome(utenteToEdit.getNome());
        editUtente.setCognome(utenteToEdit.getCognome());
        editUtente.setRuoloAziendale(utenteToEdit.getRuoloAziendale()!=null ?
                ruoliAziendaliRepository.findById(utenteToEdit.getRuoloAziendale().getId()).orElseThrow((
                )->new EntityNotFoundException("Ruolo Aziendale non trovata")):null);
        editUtente.setEmail(utenteToEdit.getEmail());
        editUtente.setPassword(utenteToEdit.getPassword());

        return utentiRepository.save(editUtente);
    }

    private List<UtentiDTO> lista(){
        if(utentiRepository.findAll().isEmpty()){
            throw new EntityExistsException("Non sono presenti dati");
        } else {
            List<Utenti> list =utentiRepository.findAll();
            List<UtentiDTO> listDTO = new ArrayList<>();
            for(Utenti utente : list){
                listDTO.add(new UtentiDTO(utente));
            }
            return listDTO;
        }
    }

    @Override
    public UtentiDTO crea(UtentiDTO utenteDTO) {

        return new UtentiDTO(saveObject(utenteDTO));
    }

    @Override
    public UtentiDTO modifica(Long id, UtentiDTO utenteDTO) {
        return new UtentiDTO(editObject(id,utenteDTO));
    }

    @Override
    public List<UtentiDTO> getAllOrdineCognome() {
        return lista().stream()
                .sorted(Comparator.comparing(UtentiDTO::getCognome, String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(UtentiDTO::getNome, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }


    @Override
    public UtentiDTO getById(Long id) {
        UtentiDTO utenteDTO = new UtentiDTO(checkIdAndTake(id));
        return utenteDTO;
    }


    @Override
    public List<UtentiDTO> findByCognomeString(String cognome) {
        List<Utenti> lista = utentiRepository.findAll();
        List<UtentiDTO> listaDTO = lista.stream()
                .filter(ut -> ut.getCognome().toLowerCase().contains(cognome.toLowerCase()))
                .map(UtentiDTO::new)
                .sorted(Comparator.comparing(UtentiDTO::getCognome, String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(UtentiDTO::getNome, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
        return listaDTO;
    }


    @Override
    public List<UtentiDTO> findByNomeString(String nome) {
        List<Utenti> lista = utentiRepository.findAll();
        List<UtentiDTO> listaDTO = lista.stream()
                .filter(ut->ut.getNome().toLowerCase().contains(nome.toLowerCase()))
                .map(UtentiDTO::new)
                .sorted(Comparator.comparing(UtentiDTO::getNome,String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
        return listaDTO;
    }
}
