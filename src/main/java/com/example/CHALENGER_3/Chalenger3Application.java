package com.example.CHALENGER_3;

import com.example.CHALENGER_3.model.Book;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.CHALENGER_3.service.BookService;

import java.util.Scanner;



@SpringBootApplication
public class Chalenger3Application implements CommandLineRunner {

	private final BookService bookService;

	public Chalenger3Application(BookService bookService) {
		this.bookService = bookService;
	}

	public static void main(String[] args) {
		SpringApplication.run(Chalenger3Application.class, args);
	}

	@Override
	public void run(String... args) {
		Scanner scanner = new Scanner(System.in);
		boolean running = true;


		while (running) {
			System.out.println("\n--- Menú LiterAlura ---");
			System.out.println("1. Buscar libros en la API");
			System.out.println("2. Listar todos los libros guardados");
			System.out.println("3. Agregar un libro manualmente");
			System.out.println("4. Consultar libros por autor");
			System.out.println("5. Listar todos los autores");
			System.out.println("6. Listar autores vivos en un año");
			System.out.println("7. Cantidad de libros por idioma");
			System.out.println("8. Salir");
			System.out.print("Selecciona una opción: ");

			String opcion = scanner.nextLine();

			switch (opcion) {
				case "1":
					buscarLibros(scanner);
					break;
				case "2":
					listarLibros();
					break;
				case "3":
					agregarLibro(scanner);
					break;
				case "4":
					consultarPorAutor(scanner);
					break;
				case "5":
					listarAutores();
					break;
				case "6":
					listarAutoresVivosEnAnio(scanner);
					break;
				case "7":
					mostrarCantidadDeLibrosPorIdioma(scanner);
					break;
				case "8":
					running = false;
					System.out.println("¡Gracias por usar LiterAlura!");
					break;
				default:
					System.out.println("Opción no válida. Intenta nuevamente.");
			}
		}
	}

	private void buscarLibros(Scanner scanner) {
		System.out.print("Introduce una palabra clave para buscar libros: ");
		String keyword = scanner.nextLine();
		bookService.searchBooks(keyword);
	}

	private void listarLibros() {
		bookService.listAllBooks();
	}

	private void agregarLibro(Scanner scanner) {
		System.out.print("Introduce el título del libro: ");
		String title = scanner.nextLine();
		System.out.print("Introduce el autor del libro: ");
		String author = scanner.nextLine();
		System.out.print("Introduce fecha de nacimiento del autor del libro: ");
		String birthYear = scanner.nextLine();
		System.out.print("Introduce fecha de fallecimiento del autor del libro: ");
		String deathYear = scanner.nextLine();
		System.out.print("Introduce el lenguaje del libro: ");
		String languages = scanner.nextLine();
		System.out.print("Introduce el numero de descagar del libro: ");
		Integer downloadCount = Integer.parseInt(scanner.nextLine());

		bookService.addBookManually(title, author, birthYear, deathYear, languages, downloadCount);
	}

	private void consultarPorAutor(Scanner scanner) {
		System.out.print("Introduce el nombre del autor: ");
		String author = scanner.nextLine();
		bookService.findBooksByAuthor(author);
	}

	private void listarAutores() {
		bookService.listAllAuthors();
	}

	private void listarAutoresVivosEnAnio(Scanner scanner) {
		System.out.print("Introduce el año para buscar autores vivos: ");
		int year = Integer.parseInt(scanner.nextLine());
		bookService.listAuthorsAliveInYear(year);
	}

	private void mostrarCantidadDeLibrosPorIdioma(Scanner scanner) {
		System.out.print("Introduce el idioma para buscar libros: ");
		String language = scanner.nextLine();
		bookService.showBooksByLanguage(language);
	}
}