package com.services.authservice.repository;

import com.services.authservice.model.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

public interface AttemptRepository extends JpaRepository<Attempt, Long> {

    @Query("SELECT COUNT(a) FROM Attempt a WHERE a.creationDateTime > :creationDateTime AND a.username = :username")
    int countFailAttempts(LocalDateTime creationDateTime, String username);

    @Transactional
    void deleteAttemptsByUsername(String username);
}
