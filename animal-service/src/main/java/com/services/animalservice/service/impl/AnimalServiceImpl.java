package com.services.animalservice.service.impl;

import com.services.animalservice.model.Animal;
import com.services.animalservice.repository.AnimalRepository;
import com.services.animalservice.service.AnimalService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;

    public AnimalServiceImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public Animal save(Animal animal) {
        return null;
    }

    @Override
    public Animal update(Long id, Animal animal) {
        return null;
    }

    @Override
    public void delete(Long id) {
        try {
            animalRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no animal with this id");
        }
    }

    @Override
    public Animal getByAnimalId(Long id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no animal with this id"));
    }

    @Override
    public List<Animal> getByUsername(String username) {
        return null;
    }
}
