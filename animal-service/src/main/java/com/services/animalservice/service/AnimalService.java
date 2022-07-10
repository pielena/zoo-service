package com.services.animalservice.service;

import com.services.animalservice.model.Animal;

import java.util.List;

public interface AnimalService {

    Animal save(Animal animal);

    Animal update(Long id, Animal animal);

    void delete(Long id);

    Animal getByAnimalId(Long id);

    List<Animal> getByUsername(String username);
}
