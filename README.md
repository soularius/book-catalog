
# LiterAlura - Catálogo de Libros

LiterAlura es un proyecto de desafío para construir un catálogo de libros utilizando Java, Spring Boot y PostgreSQL. El proyecto se centra en consumir una API externa, procesar datos JSON, y proporcionar estadísticas e interacciones enriquecedoras con los usuarios. Este repositorio demuestra habilidades en el manejo de relaciones Many-to-Many con JPA, consultas derivadas con Spring Data JPA y procesamiento de datos con Streams de Java.

---

## **Descripción del Proyecto**

El objetivo principal del proyecto es:
1. Buscar libros y sus autores desde una API pública.
2. Guardar los libros y autores en una base de datos relacional.
3. Listar libros y autores con estadísticas avanzadas, como:
   - Autores vivos en un año específico.
   - Cantidad de libros en un idioma específico.
4. Proveer interacción vía consola con un menú para que el usuario explore la base de datos.

---

## **Funcionalidades**

1. **Buscar libros desde la API**:
   - Consume datos JSON desde una API pública para almacenar libros y autores.
   - Relación Many-to-Many para gestionar libros con múltiples autores y lenguajes.

2. **Agregar libros manualmente**:
   - Los usuarios pueden añadir libros ingresando detalles como título, autor, idioma y estadísticas.

3. **Listar información guardada**:
   - Todos los libros.
   - Todos los autores.
   - Autores vivos en un año específico.
   - Cantidad de libros en un idioma específico.

4. **Estadísticas**:
   - Consulta el número de libros disponibles en idiomas específicos.

5. **Nuevas funcionalidades**:
   - Mostrar estadísticas de descargas de libros (promedio, máximo, mínimo, total).
   - Top 10 libros más descargados.
   - Buscar autor por nombre con coincidencias parciales.
   - Listar autores por año de nacimiento.
   - Listar autores por año de fallecimiento.

---

## **Requisitos del Proyecto**

### **Tecnologías Utilizadas**
- **Java 21** o superior
- **Spring Boot 3.4.1**
- **Maven 4.0.0**
- **PostgreSQL 16**
- **H2 Database** (opcional para pruebas locales)

### **Dependencias Principales**
- Spring Data JPA
- Spring Web
- PostgreSQL Driver
- Lombok (opcional para simplificar getters/setters)

---

## **Ejecución del Proyecto**

### **1. Clonar el Repositorio**
```bash
git clone https://github.com/soularius/book-catalog
cd literalura
```

### **2. Configurar la Base de Datos**
Configura las credenciales de tu base de datos PostgreSQL en el archivo `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
# dev
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=false
# prod
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### **3. Interacción**
Sigue las opciones del menú para buscar libros, listar autores o consultar estadísticas.

---

## **Estructura del Proyecto**

```plaintext
src/
├── main/
│   ├── java/
│   │   └── com.example.literalura/
│   │       ├── model/         # Clases de entidad (Book, Author, Language)
│   │       ├── repository/    # Repositorios JPA
│   │       ├── service/       # Lógica de negocio
│   │       ├── httpclient/    # Clases para consumir la API
│   │       └── Chalenger3Application.java
│   ├── resources/
│       ├── application.properties  # Configuración de la aplicación
│       └── data.sql                # Datos iniciales (opcional)
└── test/                           # Tests automatizados
```

---

## **Uso del Proyecto**

### **Menú Principal**
Al ejecutar la aplicación, verás un menú como este:
```plaintext
--- Menú LiterAlura ---
1. Buscar libros en la API
2. Listar todos los libros guardados
3. Agregar un libro manualmente
4. Consultar libros por autor
5. Listar todos los autores
6. Listar autores vivos en un año
7. Cantidad de libros por idioma
8. Mostrar estadísticas de descargas
9. Top 10 libros más descargados
10. Buscar autor por nombre
11. Listar autores por año de nacimiento
12. Listar autores por año de fallecimiento
13. Salir
```

### **Consultar Autores Vivos**
Ejemplo de consulta para listar autores vivos en 1900:
```plaintext
Introduce el año para buscar autores vivos: 1900
Autores vivos en el año 1900:
  - Kartini, Raden Adjeng
  - Joshua Bloch
```

### **Top 10 Libros Más Descargados**
```plaintext
Top 10 libros más descargados:
1. Effective Java - Descargas: 1200
2. Clean Code - Descargas: 900
...
```

### **Estadísticas de Descargas**
```plaintext
Estadísticas de descargas:
Total de descargas: 2100
Promedio de descargas: 700
Máximo de descargas: 1200
Mínimo de descargas: 200
```

## **Licencia**
Este proyecto está bajo la Licencia MIT. Consulta el archivo `LICENSE` para más información.
