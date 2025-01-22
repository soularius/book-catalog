package com.example.CHALENGER_3.repository;

import com.example.CHALENGER_3.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Cambia el retorno a Optional<Book>
    Optional<Book> findByTitle(String title);

    // Buscar libros por autor
    List<Book> findByAuthorsName(String name);

    // Buscar libros por idioma
    List<Book> findByLanguagesName(String language);
}
