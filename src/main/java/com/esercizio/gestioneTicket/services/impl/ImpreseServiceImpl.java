package com.esercizio.gestioneTicket.services.impl;

import com.esercizio.gestioneTicket.model.DTO.ImpreseDTO;
import com.esercizio.gestioneTicket.model.Imprese;
import com.esercizio.gestioneTicket.repositories.ImpreseRepository;
import com.esercizio.gestioneTicket.services.ImpreseService;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImpreseServiceImpl implements ImpreseService {

    @Autowired
    ImpreseRepository repository;

    private void validazioneCampi(String ragioneSociale, String partitaIva, String codiceFiscale, String indirizzo, String comune,
                                  String provincia, String cap, String email, String pec, String sdi){
        if(ragioneSociale==null||ragioneSociale.isEmpty()){
            throw new IllegalArgumentException("Ragione sociale mancante");
        }

        if(partitaIva==null||partitaIva.isEmpty()){
            throw new IllegalArgumentException("Partita Iva mancante");
        }

        if (!partitaIva.matches("^[A-Z0-9]{16}$") && !partitaIva.matches("\\d{11}")) {
            throw new IllegalArgumentException("Formato della partita iva non valido");
        }



        if (!codiceFiscale.matches("^[A-Z0-9]{16}$") && !codiceFiscale.matches("\\d{11}")) {
            throw new IllegalArgumentException("Formato del codice fiscale non valido");
        }



        if(indirizzo==null||indirizzo.isEmpty()){
            throw new IllegalArgumentException("Indirizzo mancante");
        }

        if(comune==null||comune.isEmpty()||comune.matches("\\d+")){
            throw new IllegalArgumentException("Comune mancante");
        }

        if(provincia==null||provincia.isEmpty()||provincia.matches("\\d+")){
            throw new IllegalArgumentException("Provincia mancante");
        }

        if (cap == null || cap.isEmpty() || !cap.matches("\\d+")) {
            throw new IllegalArgumentException("Cap mancante o non valido");
        }

        if (email == null || email.isEmpty() || !email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Email non valida");
        }

        if (pec == null || pec.isEmpty() || !pec.contains("@") || !pec.contains(".")) {
            throw new IllegalArgumentException("PEC non valida");
        }

        if(sdi==null||sdi.isEmpty()){
            throw new IllegalArgumentException("Sdi mancante");
        }

    }

    private void existingObject(String ragioneSociale){
        Imprese impresaToFind = repository.findByRagioneSociale(ragioneSociale);
        if(impresaToFind != null){
            throw new EntityExistsException("Impresa già presente nel db");
        }
    }


    private Imprese checkIdAndTake(Long id){
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

    private Imprese saveObject(ImpreseDTO impresaToSave){

        validazioneCampi(impresaToSave.getRagioneSociale(), impresaToSave.getPartitaIva(),
                impresaToSave.getCodiceFiscale(), impresaToSave.getIndirizzo(),
                impresaToSave.getComune(), impresaToSave.getProvincia(),
                impresaToSave.getCap(), impresaToSave.getEmail(), impresaToSave.getPec(),
                impresaToSave.getSdi());

        existingObject(impresaToSave.getRagioneSociale());

        Imprese nuovaImpresa = new Imprese();

        nuovaImpresa.setRagioneSociale(impresaToSave.getRagioneSociale());
        nuovaImpresa.setPartitaIva(impresaToSave.getPartitaIva());
        nuovaImpresa.setCodiceFiscale(impresaToSave.getCodiceFiscale());
        nuovaImpresa.setIndirizzo(impresaToSave.getIndirizzo());
        nuovaImpresa.setComune(impresaToSave.getComune());
        nuovaImpresa.setProvincia(impresaToSave.getProvincia());
        nuovaImpresa.setCap(impresaToSave.getCap());
        nuovaImpresa.setEmail(impresaToSave.getEmail());
        nuovaImpresa.setPec(impresaToSave.getPec());
        nuovaImpresa.setSdi(impresaToSave.getSdi());


        return repository.save(nuovaImpresa);
    }

    private Imprese editObject(Long id, ImpreseDTO impresaToEdit){

        validazioneCampi(impresaToEdit.getRagioneSociale(), impresaToEdit.getPartitaIva(),
                impresaToEdit.getCodiceFiscale(), impresaToEdit.getIndirizzo(),
                impresaToEdit.getComune(), impresaToEdit.getProvincia(),
                impresaToEdit.getCap(), impresaToEdit.getEmail(), impresaToEdit.getPec(),
                impresaToEdit.getSdi());

        existingObject(impresaToEdit.getRagioneSociale());

        Imprese editImpresa = checkIdAndTake(id);

        editImpresa.setRagioneSociale(impresaToEdit.getRagioneSociale());
        editImpresa.setPartitaIva(impresaToEdit.getPartitaIva());
        editImpresa.setCodiceFiscale(impresaToEdit.getCodiceFiscale());
        editImpresa.setIndirizzo(impresaToEdit.getIndirizzo());
        editImpresa.setComune(impresaToEdit.getComune());
        editImpresa.setProvincia(impresaToEdit.getProvincia());
        editImpresa.setCap(impresaToEdit.getCap());
        editImpresa.setEmail(impresaToEdit.getEmail());
        editImpresa.setPec(impresaToEdit.getPec());
        editImpresa.setSdi(impresaToEdit.getSdi());

        return repository.save(editImpresa);
    }

    private List<ImpreseDTO> lista(){
        if(repository.findAll().isEmpty()){
            throw new EntityExistsException("Non sono presenti dati");
        } else {
            List<Imprese> list = repository.findAll();
            List<ImpreseDTO> listDTO = new ArrayList<>();
            for (Imprese impresa : list) {
                listDTO.add(new ImpreseDTO(impresa));
            }
            return listDTO;
        }
    }



    @Override
    public ImpreseDTO crea(ImpreseDTO impresaDTO) {
        return new ImpreseDTO(saveObject(impresaDTO));}

    @Override
    public ImpreseDTO modifica(Long id, ImpreseDTO impresaDTO) {

        return new ImpreseDTO(editObject(id,impresaDTO));
    }

    @Override
    public List<ImpreseDTO> getAllOrdineId() {
        return lista();
    }

    @Override
    public List<ImpreseDTO> getAllOrdineRagioneSociale() {
        return lista().stream()
                .sorted(Comparator.comparing(ImpreseDTO::getRagioneSociale,
                        String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    @Override
    public ImpreseDTO getById(Long id) {
        ImpreseDTO impresaDTO = new ImpreseDTO(checkIdAndTake(id));
        return impresaDTO;
    }

    @Override
    public List<ImpreseDTO> findByRagioneSocialeString(String stringa) {
        List<Imprese> lista = repository.findAll();
        List<ImpreseDTO> listaDTO = lista.stream()
                .filter(impresa->impresa.getRagioneSociale().toLowerCase().contains(stringa.toLowerCase()))
                .map(ImpreseDTO::new)
                .sorted(Comparator.comparing(ImpreseDTO::getRagioneSociale,String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
        return listaDTO;
    }
}
