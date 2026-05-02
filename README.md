# Library Management System

A web-based **Library Management System** that lets a user create, read and update
information about **Authors** and the **Books** they have written. Built on
Spring Boot 3.2 with Spring MVC, Spring Data JPA, JSP views and an embedded H2
database.

Submitted as part of the BITS-SGA Spring Boot assignment.

---

## Tech Stack

| Layer            | Technology                                                              |
|------------------|-------------------------------------------------------------------------|
| Language         | Java 17                                                                 |
| Framework        | Spring Boot 3.2.5 (Spring MVC, Spring Data JPA)                         |
| View             | JSP, JSTL, Spring `<form>` tags                                         |
| Persistence      | Hibernate, H2 (in-memory)                                               |
| Validation       | Jakarta Bean Validation (`@NotBlank`, `@Email`, `@Positive`)            |
| Build            | Maven (with wrapper `./mvnw`)                                           |
| Testing          | JUnit 5, Mockito, Spring Boot Test, `@DataJpaTest`                      |

---

## Domain Model

Two entities with a **one-to-many** relationship:

```
AUTHOR  ──── 1 : N ────►  BOOK
 id (PK)                   id (PK)
 name                      title
 email (unique)            isbn (unique)
 nationality               price
                           published_year
                           author_id (FK)
```

- An author can write many books.
- Every book belongs to exactly one author.

---

## Features

- **Populate Database** — `DataInitializer` seeds 10 authors and 10 books on first run.
- **Create** — JSP form with field-level validation and inline integrity-violation
  errors (duplicate ISBN / duplicate email).
- **Read** — listing pages backed by a custom JPQL `@Query` that performs an
  **INNER JOIN** between `Book` and `Author`, projected into a `BookAuthorDTO`.
- **Update** — pre-populated edit form, in-place update preserving the entity id.
- **Error handling** — `@ControllerAdvice` global handler renders a friendly error
  page for `ResourceNotFoundException` and any uncaught
  `DataIntegrityViolationException`.
- **Styled UI** — custom CSS (`/static/css/styles.css`) for a clean, responsive layout.

---

## Project Structure

```
src/main/java/com/bits/sga
 ├── LibraryManagementApplication.java   # @SpringBootApplication entry-point
 ├── entity/
 │     ├── Author.java                   # @Entity, @OneToMany
 │     └── Book.java                     # @Entity, @ManyToOne
 ├── dto/
 │     └── BookAuthorDTO.java            # JPQL projection for INNER JOIN result
 ├── repository/
 │     ├── AuthorRepository.java
 │     └── BookRepository.java           # custom @Query with INNER JOIN
 ├── service/
 │     ├── AuthorService.java
 │     └── BookService.java
 ├── controller/
 │     ├── HomeController.java
 │     ├── AuthorController.java
 │     ├── BookController.java
 │     └── GlobalExceptionHandler.java   # @ControllerAdvice
 ├── exception/ResourceNotFoundException.java
 └── config/DataInitializer.java         # CommandLineRunner that seeds data

src/main/resources/
 ├── application.properties              # JPA, H2, JSP view-resolver config
 └── static/css/styles.css               # CSS for JSP pages

src/main/webapp/WEB-INF/views/
 ├── home.jsp
 ├── error.jsp
 ├── authors/{list,add,edit}.jsp
 └── books/{list,add,edit}.jsp

src/test/java/com/bits/sga/
 ├── repository/BookRepositoryTest.java  # @DataJpaTest, exercises INNER JOIN
 └── service/{AuthorServiceTest, BookServiceTest}.java   # Mockito unit tests

docs/
 ├── report.html                         # Project report (HTML source)
 └── report.pdf                          # Project report (PDF, 8 pages)
```

---

## Running the Project

You only need a JDK 17+ installed. Maven is bundled via the wrapper.

```bash
# Run the application
./mvnw spring-boot:run

# On Windows
mvnw.cmd spring-boot:run
```

Then open in your browser:

| URL                                   | Description                                |
|---------------------------------------|--------------------------------------------|
| http://localhost:8080/                | Home page                                  |
| http://localhost:8080/authors         | Authors list (CRUD)                        |
| http://localhost:8080/books           | Books list (rendered using INNER JOIN)     |
| http://localhost:8080/h2-console      | H2 database console                        |

H2 console JDBC URL: `jdbc:h2:mem:librarydb` &nbsp;·&nbsp; user: `sa` &nbsp;·&nbsp; password: *(empty)*

---

## Running the Tests

```bash
./mvnw test
```

17 tests covering:

- Service-layer business logic with **Mockito** (find / save / update,
  duplicate-ISBN and duplicate-email integrity paths).
- Repository-layer with `@DataJpaTest`, including the **custom INNER JOIN**
  query against an in-memory H2.

Expected output:

```
[INFO] Tests run: 17, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

---

## Custom INNER JOIN Query

Required by the assignment, defined in `BookRepository`:

```java
@Query("SELECT new com.bits.sga.dto.BookAuthorDTO(" +
       "b.id, b.title, b.isbn, b.price, b.publishedYear, " +
       "a.id, a.name, a.nationality) " +
       "FROM Book b INNER JOIN b.author a " +
       "ORDER BY a.name ASC, b.title ASC")
List<BookAuthorDTO> findAllBooksWithAuthors();
```

Hibernate translates this to:

```sql
SELECT b1_0.id, b1_0.title, b1_0.isbn, b1_0.price, b1_0.published_year,
       a1_0.id, a1_0.name, a1_0.nationality
  FROM books b1_0
  JOIN authors a1_0 ON a1_0.id = b1_0.author_id
 ORDER BY a1_0.name, b1_0.title;
```

---

## Project Report

A full write-up — entity design, implementation details for each operation,
testing strategy, and challenges faced — is available at
[`docs/report.pdf`](docs/report.pdf).
