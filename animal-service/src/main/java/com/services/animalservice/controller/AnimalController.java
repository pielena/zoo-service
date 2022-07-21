package com.services.animalservice.controller;

import com.services.animalservice.dto.AnimalDto;
import com.services.animalservice.model.Animal;
import com.services.animalservice.service.AnimalService;
import com.services.animalservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/animals")
public class AnimalController {

    private final AnimalService animalService;
    private final UserService userService;

    public AnimalController(AnimalService animalService, UserService userService) {
        this.animalService = animalService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List <AnimalDto>> getAllAnimalsByUser() {

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<AnimalDto> result = animalService.getAllByUsername(username).stream()
                .map(AnimalDto::fromAnimal)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AnimalDto> getAnimalById(@PathVariable(name = "id") Long id) {

        Animal animal = animalService.getById(id);
        AnimalDto result = AnimalDto.fromAnimal(animal);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AnimalDto> saveAnimal(@RequestBody AnimalDto animalDto) {

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Animal animal = animalDto.toAnimal();
        Animal result = animalService.save(animal, username);
        return new ResponseEntity<>(AnimalDto.fromAnimal(result), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AnimalDto> putAnimalById(@PathVariable Long id, @RequestBody AnimalDto animalDto) {

        Animal animal = animalDto.toAnimal();
        animal.setId(id);
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Animal result = animalService.update(animal, username);
        return new ResponseEntity<>(AnimalDto.fromAnimal(result), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteAnimalById(@PathVariable Long id) {

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        animalService.delete(id, username);
        return new ResponseEntity<>("Animal is deleted", HttpStatus.OK);
    }
}
