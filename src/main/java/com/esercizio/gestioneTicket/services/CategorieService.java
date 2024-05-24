package com.esercizio.gestioneTicket.services;

import com.esercizio.gestioneTicket.model.DTO.CategorieDTO;

import java.util.List;

public interface CategorieService {
    CategorieDTO crea(CategorieDTO categoriaDTO);

    CategorieDTO modifica(Long id, CategorieDTO categoriaDTO);

    List<CategorieDTO> getAllOrdineId();

    List<CategorieDTO> getAllOrdineDescrizione();

    List<CategorieDTO> getAllTrue();

    Boolean switchValidazione(Long id);

    CategorieDTO getById(Long id);

    List<CategorieDTO> findByString(String stringa);
}
