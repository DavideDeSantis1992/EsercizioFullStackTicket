package com.esercizio.gestioneTicket.services.impl;

import com.esercizio.gestioneTicket.model.DTO.EntitaGeneraleDTO;
import com.esercizio.gestioneTicket.model.EntitaGenerale;
import com.esercizio.gestioneTicket.repositories.GenericRepository;

import jakarta.persistence.EntityExistsException;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class MetodiToDo {

    public static void validaDescrizione(String descrizione) {
        if (descrizione == null || descrizione.isEmpty()) {
            throw new IllegalArgumentException("Valore di descrizione ruolo team mancante o vuoto");
        }

        try {
            Double.parseDouble(descrizione);
            throw new IllegalArgumentException("Il valore della descrizione non può essere un numero");
        } catch (NumberFormatException ignored) {}
    }

    public static <T, R extends GenericRepository<T, ?>> void existingObject(R repository, String descrizione) {
        T existingObject = repository.findByDescrizione(descrizione);
        if (existingObject != null) {
            throw new EntityExistsException("Oggetto già presente");
        }
    }

    public static <T, R extends JpaRepository<?, Long>> void checkId(Long id, R repository) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID non valido");
        }

        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("ID non presente");
        }

        if (!String.valueOf(id).matches("[0-9]+")) {
            throw new NumberFormatException("ID non valido con caratteri alfanumerici");
        }
    }

    public static <T extends EntitaGenerale, R extends JpaRepository<T, Long>> T saveNewDbObject(String descrizione, R repository, Supplier<T> constructor) {
        T nuovoObject = constructor.get();
        nuovoObject.setDescrizione(descrizione);
        nuovoObject.setValido(true); // Impostazione del campo valido a true
        return repository.save(nuovoObject);
    }

    public static <T extends EntitaGenerale, R extends JpaRepository<T, Long>> T editDbObject(String descrizione, Long id, R repository, BiFunction<T, String, T> updateFunction) {
        T entityToUpdate = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID non valido o non presente"));

        // Applica la funzione di aggiornamento fornita all'entità
        T updatedEntity = updateFunction.apply(entityToUpdate, descrizione);

        // Salva l'entità aggiornata nel repository e restituiscila
        return repository.save(updatedEntity);
    }

    public static <T extends EntitaGenerale, D extends EntitaGeneraleDTO> List<D> listEntities(JpaRepository<T, Long> repository, Function<T, D> mapper) {
        List<T> entities = repository.findAll();
        List<D> dtoList = new ArrayList<>();
        for (T entity : entities) {
            dtoList.add(mapper.apply(entity));
        }
        if (entities.isEmpty()) {
            throw new EntityExistsException("Entità non presenti");
        }
        return dtoList;
    }

    public static <T extends EntitaGenerale, D extends EntitaGeneraleDTO> List<D> listValidEntities(JpaRepository<T, Long> repository, Predicate<T> isValid, Function<T, D> mapper, String errorMessage) {
        List<T> validEntities = repository.findAll().stream()
                .filter(isValid)
                .collect(Collectors.toList());

        if (validEntities.isEmpty()) {
            throw new EntityExistsException(errorMessage);
        }

        return validEntities.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    public static <T extends EntitaGenerale, D extends EntitaGeneraleDTO> List<D> listEntitiesByString(
            JpaRepository<T, Long> repository,
            String searchString,
            Function<T, D> mapper,
            Predicate<T> filter,
            String errorMessage
    ) {
        List<T> filteredEntities = repository.findAll().stream()
                .filter(filter)
                .filter(entity -> entity.getDescrizione().toLowerCase().contains(searchString.toLowerCase()))
                .collect(Collectors.toList());

        if (filteredEntities.isEmpty()) {
            throw new EntityExistsException(errorMessage);
        }

        return filteredEntities.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    public static <T extends EntitaGenerale, R extends JpaRepository<T, Long>> Boolean switchValid(Long id, R repository) {
        MetodiToDo.checkId(id, repository);
        Optional<T> optionalEntity = repository.findById(id);
        if (optionalEntity.isEmpty()) {
            throw new IllegalArgumentException("ID non valido o non presente");
        }
        T entity = optionalEntity.get();
        entity.setValido(!entity.getValido());
        repository.save(entity);
        return entity.getValido();
    }
}
