package com.services.animalservice.service.impl;

import com.services.animalservice.model.Animal;
import com.services.animalservice.model.User;
import com.services.animalservice.repository.AnimalRepository;
import com.services.animalservice.service.AnimalService;
import com.services.animalservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final UserService userService;

    public AnimalServiceImpl(AnimalRepository animalRepository, UserService userService) {
        this.animalRepository = animalRepository;
        this.userService = userService;
    }

    @Override
    public Animal save(Animal animal, Long userId) {
        if (!animalRepository.existsAnimalByName(animal.getName())) {
            User user = userService.getUserById(userId);
            animal.setUser(user);
            return animalRepository.save(animal);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Animal with name " + animal.getName() + " already exists");
        }
    }

    @Override
    public Animal update(Animal animal, Long userId) {
         Animal result = animalRepository.findById(animal.getId())
                 .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Animal with id " + animal.getId() + " doesn't exist"));
         if (result.getUser().getId().equals(userId)) {
             result.setBirthday(animal.getBirthday());
             result.setAnimalType(animal.getAnimalType());
             result.setAnimalSex(animal.getAnimalSex());
             result.setName(animal.getName());
             animalRepository.save(result);
         }
         else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "It's not your animal, you can't change it");

        return result;
    }

    @Override
    public void delete(Long animalId, Long userId) {
        Animal animal = getById(animalId);
        if (!animal.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "It's not your animal, you can't delete it");
        }
            animalRepository.deleteByUserIdAndAnimalId(animalId, userId);
    }

    @Override
    public Animal getById(Long animalId) {
        return animalRepository.findById(animalId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Animal with id " + animalId + " doesn't exist"));
    }

    @Override
    public List<Animal> getAllByUserId(Long userId) {
        return animalRepository.findAnimalByUser(userId);
    }
}
