package com.services.animalservice.repository;

import com.services.animalservice.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    @Query("SELECT a FROM Animal a LEFT JOIN FETCH a.user WHERE a.user.id = :userId")
    List<Animal> findAnimalByUser(Long userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Animal a WHERE a.user.id = :userId AND a.id = :animalId")
    void deleteByUserIdAndAnimalId(Long animalId, Long userId);

    Boolean existsAnimalByName(String name);

}
