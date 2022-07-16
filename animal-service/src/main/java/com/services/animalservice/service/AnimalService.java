package com.services.animalservice.service;

import com.services.animalservice.model.Animal;

import java.util.List;

public interface AnimalService {

    Animal save(Animal animal, Long userId);

    Animal update(Animal animal, Long userId);

    void delete(Long animalId, Long userId);

    Animal getById(Long id);

    List<Animal> getAllByUserId(Long userId);
}
