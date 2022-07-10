package com.services.animalservice.controller;

import com.services.animalservice.dto.AnimalDto;
import com.services.animalservice.model.Animal;
import com.services.animalservice.service.AnimalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/animals")
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

//    @GetMapping
//    public List<Animal> getAllAnimalsByUser(){
//        return null;
//    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AnimalDto> getAnimalById(@PathVariable(name = "id") Long id) {
        Animal animal = animalService.getByAnimalId(id);

        if (animal == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        AnimalDto result = AnimalDto.fromAnimal(animal);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> saveAnimal(@RequestBody AnimalDto animalDto) {
        Animal animal = animalDto.toAnimal();
        animalService.save(animal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> putAnimalById(@PathVariable Long id, @RequestBody AnimalDto animalDto) {
        animalService.update(id, animalDto.toAnimal());
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @DeleteMapping(value = "/{id}")
//    public void deleteAnimalById(@PathVariable Long id) {
//        animalService.delete(id);
//    }
}
