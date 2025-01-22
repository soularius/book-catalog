package com.example.CHALENGER_3.repository;

import com.example.CHALENGER_3.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByName(String name);

    List<Author> findByBirthYear(String birthYear);

    List<Author> findByDeathYear(String deathYear);
}
