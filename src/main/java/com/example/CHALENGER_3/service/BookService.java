package com.example.CHALENGER_3.service;

import com.example.CHALENGER_3.httpclient.ApiClient;
import com.example.CHALENGER_3.httpclient.ApiRequestBuilder;
import com.example.CHALENGER_3.httpclient.ApiResponseHandler;
import com.example.CHALENGER_3.model.ApiResponse;
import com.example.CHALENGER_3.model.Author;
import com.example.CHALENGER_3.model.Book;
import com.example.CHALENGER_3.model.Language;
import com.example.CHALENGER_3.repository.BookRepository;

import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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

    public void listAllBooks() {
        List<Book> books = bookRepository.findAll();
        books.forEach(book -> System.out.println(book.toString()));
    }

    public void addBook(Book book) {
        Optional<Book> existingBook = bookRepository.findByTitle(book.getTitle());
        if (existingBook.isEmpty()) {
            book.setId(null); // Asegurarse de que se genere un nuevo ID
            bookRepository.save(book);
        }
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

    public void addBookManually(String title, String authorName, String birthYear, String deathYear, String languages, Integer downloadCount) {
        // Crear un nuevo libro
        Book book = new Book();
        book.setTitle(title);
        book.setDownloadCount(downloadCount);

        // Crear un nuevo autor
        Author author = new Author();
        author.setName(authorName);
        author.setName(birthYear);
        author.setName(deathYear);

        Language language = new Language();
        language.setName(languages);

        // Asignar autor al libro
        book.setAuthors(List.of(author));
        book.setLanguages(List.of(language));

        // Guardar en la base de datos
        bookRepository.save(book);
        System.out.println("Libro agregado manualmente: " + title);
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