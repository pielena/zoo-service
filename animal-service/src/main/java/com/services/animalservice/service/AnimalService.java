package com.services.animalservice.service;

import com.services.animalservice.model.Animal;

import java.util.List;

public interface AnimalService {

    Animal save(Animal animal, String username);

    Animal update(Animal animal, String username);

    void delete(Long animalId, String username);

    Animal getById(Long id);

    List<Animal> getAllByUsername(String username);
}
