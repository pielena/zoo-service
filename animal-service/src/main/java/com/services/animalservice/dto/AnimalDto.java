package com.services.animalservice.dto;

import com.services.animalservice.model.Animal;
import com.services.animalservice.model.AnimalSex;
import com.services.animalservice.model.AnimalType;
import lombok.Data;

import java.time.LocalDate;

@Data
//@JsonIgnoreProperties(ignoreUnknown = true)
public class AnimalDto {

        private Long id;
        private String username;
        private LocalDate birthday;
        private AnimalType animalType;
        private AnimalSex animalSex;
        private String name;

        public Animal toAnimal() {
            Animal animal = new Animal();
            animal.setId(id);
            animal.setUsername(username);
            animal.setBirthday(birthday);
            animal.setAnimalType(animalType);
            animal.setAnimalSex(animalSex);
            animal.setName(name);

            return animal;
        }

        public static AnimalDto fromAnimal(Animal animal) {
            AnimalDto animalDto = new AnimalDto();
            animalDto.setId(animal.getId());
            animalDto.setUsername(animal.getUsername());
            animalDto.setBirthday(animal.getBirthday());
            animalDto.setAnimalType(animal.getAnimalType());
            animalDto.setAnimalSex(animal.getAnimalSex());
            animalDto.setName(animal.getName());

            return animalDto;
        }
}
