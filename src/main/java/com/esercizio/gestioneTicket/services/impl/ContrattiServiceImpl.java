package com.esercizio.gestioneTicket.services.impl;

import com.esercizio.gestioneTicket.model.Contratti;
import com.esercizio.gestioneTicket.model.DTO.ContrattiDTO;
import com.esercizio.gestioneTicket.model.Imprese;
import com.esercizio.gestioneTicket.model.Utenti;
import com.esercizio.gestioneTicket.repositories.ContrattiRepository;
import com.esercizio.gestioneTicket.repositories.ImpreseRepository;
import com.esercizio.gestioneTicket.services.ContrattiService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContrattiServiceImpl implements ContrattiService {
    @Autowired
    ContrattiRepository contrattiRepository;
    @Autowired
    ImpreseRepository impreseRepository;

    private void validazioneCampi(Imprese impresaFornitore, Imprese impresaCliente,
                                  Double importo, LocalDate dataInizio, LocalDate dataFine){
        if(impresaFornitore==null||!impreseRepository.existsById(impresaFornitore.getId())){
            throw new EntityExistsException("Impresa Fornitore ID non valida");
        }

        if(impresaCliente==null||!impreseRepository.existsById(impresaCliente.getId())){
            throw new EntityExistsException("Impresa Cliente ID non valida");
        }

        if(importo!=null){
            try {
                String imp=importo.toString();
                Double.parseDouble(imp);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Dato non valido o mancante", e);
            }
        }

    }
    private Contratti saveObject(ContrattiDTO contrattoDTO){
        Imprese impresaFornitore = impreseRepository.findById(contrattoDTO.getImpresaFornitore().getId())
                .orElseThrow(() -> new EntityNotFoundException("Impresa Fornitore non trovata"));

        Imprese impresaCliente = impreseRepository.findById(contrattoDTO.getImpresaCliente().getId())
                .orElseThrow(() -> new EntityNotFoundException("Impresa Cliente non trovata"));

        validazioneCampi(
                impresaFornitore,
                impresaCliente,
                contrattoDTO.getImporto(),
                contrattoDTO.getDataInizio(),
                contrattoDTO.getDataFine()
        );

        Contratti nuovoContratto = new Contratti();
        nuovoContratto.setImpresaFornitore(impresaFornitore);
        nuovoContratto.setImpresaCliente(impresaCliente);
        nuovoContratto.setImporto(contrattoDTO.getImporto());
        nuovoContratto.setDataInizio(contrattoDTO.getDataInizio());
        nuovoContratto.setDataFine(contrattoDTO.getDataFine());

        return contrattiRepository.save(nuovoContratto);
    }

    private Contratti checkIdAndTake(Long id){
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID non valido");
        }

        if (!contrattiRepository.existsById(id)) {
            throw new IllegalArgumentException("ID non presente");
        }

        if (!String.valueOf(id).matches("[0-9]+")) {
            throw new NumberFormatException("ID non valido con caratteri alfanumerici");
        }

        return contrattiRepository.findById(id).orElseThrow();
    }

    private Imprese checkIdImpresa(Long id){
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID non valido");
        }

        if (!impreseRepository.existsById(id)) {
            throw new IllegalArgumentException("ID non presente");
        }

        if (!String.valueOf(id).matches("[0-9]+")) {
            throw new NumberFormatException("ID non valido con caratteri alfanumerici");
        }

        return impreseRepository.findById(id).orElseThrow();
    }

    private Contratti editObject(Long id, ContrattiDTO contrattoToEdit){
        Imprese impresaFornitore = impreseRepository.findById(contrattoToEdit.getImpresaFornitore().getId())
                .orElseThrow(() -> new EntityNotFoundException("Impresa Fornitore non trovata"));

        Imprese impresaCliente = impreseRepository.findById(contrattoToEdit.getImpresaCliente().getId())
                .orElseThrow(() -> new EntityNotFoundException("Impresa Cliente non trovata"));

        validazioneCampi(
                impresaFornitore,
                impresaCliente,
                contrattoToEdit.getImporto(),
                contrattoToEdit.getDataInizio(),
                contrattoToEdit.getDataFine()
        );

        Contratti contratto = checkIdAndTake(id);

        contratto.setImpresaFornitore(impresaFornitore);
        contratto.setImpresaCliente(impresaCliente);
        contratto.setImporto(contrattoToEdit.getImporto());
        contratto.setDataInizio(contrattoToEdit.getDataInizio());
        contratto.setDataFine(contrattoToEdit.getDataFine());

        return contrattiRepository.save(contratto);

    }

    private List<ContrattiDTO> lista(){
        if(contrattiRepository.findAll().isEmpty()){
            throw new EntityExistsException("Non sono presenti dati");
        } else {
            List<Contratti> list = contrattiRepository.findAll();
            List<ContrattiDTO> listDTO = new ArrayList<>();
            for(Contratti contratto : list){
                listDTO.add(new ContrattiDTO(contratto));
            }
            return listDTO;
        }
    }

    @Override
    public ContrattiDTO crea(ContrattiDTO contrattoDTO) {
        return new ContrattiDTO(saveObject(contrattoDTO));
    }

    @Override
    public ContrattiDTO modifica(Long id, ContrattiDTO contrattoDTO) {
        return new ContrattiDTO(editObject(id,contrattoDTO));
    }

    @Override
    public List<ContrattiDTO> getAllOrdineDataInizio() {
        return lista().stream()
                .sorted(Comparator.comparing(ContrattiDTO::getDataInizio,Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(contrattiDTO -> contrattiDTO.getImpresaCliente().getRagioneSociale(), String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    @Override
    public ContrattiDTO getById(Long id){
        ContrattiDTO contrattoDTO = new ContrattiDTO(checkIdAndTake(id));
        return contrattoDTO;
    }

    private List<Contratti> listaContrattiClienti(Long impresaClienteId){
        checkIdImpresa(impresaClienteId);
        List<Contratti> lista = contrattiRepository.findByImpresaClienteId(impresaClienteId);
        if(lista.isEmpty()){
            throw new EntityExistsException("Nessun dato trovato");
        } else{
            return lista;
        }
    }

    private List<Contratti> listaContrattiFornitori(Long impresaFornitoreId){
        checkIdImpresa(impresaFornitoreId);
        List<Contratti> lista = contrattiRepository.findByImpresaClienteId(impresaFornitoreId);
        if(lista.isEmpty()){
            throw new EntityExistsException("Nessun dato trovato");
        } else{
            return lista;
        }
    }

    @Override
    public List<ContrattiDTO> getContrattiByImpresaClienteId(Long impresaClienteId) {

        return listaContrattiClienti(impresaClienteId).stream()
                .map(ContrattiDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ContrattiDTO> getContrattiByImpresaFornitoreId(Long impresaFornitoreId) {

        return listaContrattiFornitori(impresaFornitoreId).stream()
                .map(ContrattiDTO::new)
                .collect(Collectors.toList());
    }

}
