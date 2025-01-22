package com.example.CHALENGER_3.service;

import com.example.CHALENGER_3.httpclient.ApiClient;
import com.example.CHALENGER_3.httpclient.ApiRequestBuilder;
import com.example.CHALENGER_3.httpclient.ApiResponseHandler;
import com.example.CHALENGER_3.model.ApiResponse;
import com.example.CHALENGER_3.model.Author;
import com.example.CHALENGER_3.model.Book;
import com.example.CHALENGER_3.model.Language;
import com.example.CHALENGER_3.repository.AuthorRepository;
import com.example.CHALENGER_3.repository.BookRepository;

import com.example.CHALENGER_3.repository.LanguageRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final LanguageRepository languageRepository;


    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, LanguageRepository languageRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.languageRepository = languageRepository;
    }


    public void searchBooks(String keyword) {
        // Implementa la lógica de búsqueda en la API

        try {
            HttpClient client = ApiClient.getClient();
            String apiEndpoint = "https://gutendex.com/books";
            HttpRequest request = ApiRequestBuilder.buildGetRequest(apiEndpoint, keyword);

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Procesar la respuesta JSON
            ApiResponse apiResponse = ApiResponseHandler.handleResponse(response);

            // Obtener los resultados
            List<Book> books = apiResponse.getResults();
            if (books.isEmpty()) {
                System.out.println("No se encontraron libros para la palabra clave: " + keyword);
            } else {
                System.out.println("Resultados encontrados para '" + keyword + "':");
                books.forEach(book -> {
                    System.out.println("Título: " + book.getTitle());
                    book.getAuthors().forEach(author ->
                            System.out.println("  - Autor: " + author.getName())
                    );

                    // Guardar en la base de datos
                    book.setId(null); // Asegurarse de que se genere un nuevo ID
                    this.addBook(book);
                    //System.out.println("Libro guardado en la base de datos: " + book.getTitle());
                });
            }
        } catch (Exception e) {
            System.out.println("Ocurrió un error al buscar libros: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Transactional
    public void listAllBooks() {
        List<Book> books = bookRepository.findAll();
        books.forEach(book -> System.out.println(book));
    }

    @Transactional
    public void listAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        authors.forEach(author -> {
            System.out.println("Nombre: " + author.getName());
            System.out.println("Año de nacimiento: " + author.getBirthYear());
            System.out.println("Año de fallecimiento: " + author.getDeathYear());
        });
    }

    @Transactional
    public void listAuthorsAliveInYear(int year) {
        List<Author> authors = authorRepository.findAll();
        System.out.println("Autores vivos en el año " + year + ":");
        authors.stream()
                .filter(author -> {
                    try {
                        return author.isAliveInYear(year);
                    } catch (NumberFormatException e) {
                        return false; // Manejar casos donde birthYear o deathYear no son válidos
                    }
                })
                .forEach(author -> System.out.println("  - " + author.getName()));
    }

    @Transactional
    public void addBook(Book book) {
        // Verificar si el libro ya existe
        Optional<Book> existingBook = bookRepository.findByTitle(book.getTitle());
        if (existingBook.isPresent()) {
            System.out.println("El libro ya existe en la base de datos: " + book.getTitle());
            return;
        }

        if (book.getTitle().length() > 1024) {
            book.setTitle(book.getTitle().substring(0, 1024));
        }

        // Gestionar autores
        List<Author> managedAuthors = book.getAuthors().stream()
                .map(author -> authorRepository.findByName(author.getName())
                        .orElseGet(() -> {
                            // Si el autor no existe, persistir uno nuevo
                            author.setId(null);
                            return authorRepository.save(author);
                        }))
                .toList();
        book.setAuthors(managedAuthors);

        // Gestionar lenguajes
        List<Language> managedLanguages = book.getLanguages().stream()
                .map(language -> languageRepository.findByName(language.getName())
                        .orElseGet(() -> {
                            // Si el lenguaje no existe, persistir uno nuevo
                            language.setId(null);
                            return languageRepository.save(language);
                        }))
                .toList();
        book.setLanguages(managedLanguages);

        // Persistir el libro
        book.setId(null); // Asegurarse de que el libro sea nuevo
        bookRepository.save(book);
        System.out.println("Libro guardado: " + book.getTitle());
    }

    @Transactional
    public void showBooksByLanguage(String language) {
        Long count = bookRepository.countBooksByLanguage(language);
        System.out.println("Cantidad de libros en el idioma '" + language + "': " + count);
    }

    public Optional<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public List<Book> findByAuthorName(String authorName) {
        return bookRepository.findByAuthorsName(authorName);
    }

    public List<Book> findByLanguage(String language) {
        return bookRepository.findByLanguagesName(language);
    }

    public void addBookManually(String title, String authorName, String birthYear, String deathYear, String languageName, Integer downloadCount) {
        // Verificar o crear autor
        Author author = authorRepository.findByName(authorName)
                .orElseGet(() -> {
                    Author newAuthor = new Author();
                    newAuthor.setName(authorName);
                    newAuthor.setBirthYear(birthYear);
                    newAuthor.setDeathYear(deathYear);
                    return authorRepository.save(newAuthor); // Persistir el nuevo autor
                });

        // Verificar o crear lenguaje
        Language language = languageRepository.findByName(languageName)
                .orElseGet(() -> {
                    Language newLanguage = new Language();
                    newLanguage.setName(languageName);
                    return languageRepository.save(newLanguage); // Persistir el nuevo lenguaje
                });

        // Crear el libro
        Book book = new Book();
        book.setTitle(title);
        book.setDownloadCount(downloadCount);
        book.setAuthors(List.of(author)); // Asociar el autor gestionado
        book.setLanguages(List.of(language)); // Asociar el lenguaje gestionado

        // Verificar si el libro ya existe
        Optional<Book> existingBook = bookRepository.findByTitle(title);
        if (existingBook.isPresent()) {
            System.out.println("El libro ya existe en la base de datos: " + title);
            return;
        }

        // Guardar el libro
        bookRepository.save(book);
        System.out.println("Libro guardado manualmente: " + title);
    }

    public void findBooksByAuthor(String authorName) {
        List<Book> books = bookRepository.findByAuthorsName(authorName);
        if (books.isEmpty()) {
            System.out.println("No se encontraron libros para el autor: " + authorName);
        } else {
            books.forEach(book -> System.out.println("Título: " + book.getTitle()));
        }
    }
}