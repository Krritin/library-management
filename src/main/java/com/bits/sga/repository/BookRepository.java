package com.bits.sga.repository;

import com.bits.sga.dto.BookAuthorDTO;
import com.bits.sga.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);

    boolean existsByIsbn(String isbn);

    @Query("SELECT new com.bits.sga.dto.BookAuthorDTO(" +
           "b.id, b.title, b.isbn, b.price, b.publishedYear, " +
           "a.id, a.name, a.nationality) " +
           "FROM Book b INNER JOIN b.author a " +
           "ORDER BY a.name ASC, b.title ASC")
    List<BookAuthorDTO> findAllBooksWithAuthors();

    @Query("SELECT new com.bits.sga.dto.BookAuthorDTO(" +
           "b.id, b.title, b.isbn, b.price, b.publishedYear, " +
           "a.id, a.name, a.nationality) " +
           "FROM Book b INNER JOIN b.author a " +
           "WHERE a.id = :authorId")
    List<BookAuthorDTO> findBooksByAuthorIdWithJoin(Long authorId);
}
