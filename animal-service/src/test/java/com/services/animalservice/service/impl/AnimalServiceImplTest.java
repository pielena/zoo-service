package com.services.animalservice.service.impl;

import com.services.animalservice.exception.AnimalServiceException;
import com.services.animalservice.model.Animal;
import com.services.animalservice.model.AnimalSex;
import com.services.animalservice.model.AnimalType;
import com.services.animalservice.repository.AnimalRepository;
import com.services.animalservice.service.AnimalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class AnimalServiceImplTest {

    @Autowired
    private AnimalService animalService;

    @MockBean
    private AnimalRepository animalRepository;

    @Test
    void getById_getsInvalidId_throwsAnimalServiceException() {
        assertThrows(AnimalServiceException.class, () -> animalService.getById(-100L));
    }

    @Test
    void getById_getsValidId_returnAnimal() {
        Animal animal = new Animal();
        animal.setId(1L);
        animal.setBirthday(LocalDate.of(2020, 3, 5));
        animal.setAnimalType(AnimalType.BEAR);
        animal.setAnimalSex(AnimalSex.MALE);
        animal.setName("Honey");

        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));

        Animal expected = animal;
        Animal actual = animalService.getById(1L);

        assertEquals(expected, actual);
    }

}