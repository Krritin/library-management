package com.bits.sga.repository;

import com.bits.sga.dto.BookAuthorDTO;
import com.bits.sga.entity.Author;
import com.bits.sga.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    private Author savedAuthor;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();

        Author author = new Author("Test Author", "test@author.com", "Indian");
        savedAuthor = authorRepository.save(author);

        bookRepository.save(new Book("Test Book One", "ISBN-001", 100.0, 2020, savedAuthor));
        bookRepository.save(new Book("Test Book Two", "ISBN-002", 200.0, 2021, savedAuthor));
    }

    @Test
    void findByIsbn_shouldReturnBook_whenIsbnExists() {
        Optional<Book> result = bookRepository.findByIsbn("ISBN-001");

        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Test Book One");
    }

    @Test
    void findByIsbn_shouldReturnEmpty_whenIsbnDoesNotExist() {
        Optional<Book> result = bookRepository.findByIsbn("DOES-NOT-EXIST");

        assertThat(result).isEmpty();
    }

    @Test
    void existsByIsbn_shouldReturnTrue_whenIsbnExists() {
        assertThat(bookRepository.existsByIsbn("ISBN-001")).isTrue();
    }

    @Test
    void findAllBooksWithAuthors_shouldReturnInnerJoinResults() {
        List<BookAuthorDTO> results = bookRepository.findAllBooksWithAuthors();

        assertThat(results).hasSize(2);
        assertThat(results).extracting(BookAuthorDTO::getAuthorName)
                .containsOnly("Test Author");
        assertThat(results).extracting(BookAuthorDTO::getTitle)
                .containsExactlyInAnyOrder("Test Book One", "Test Book Two");
    }

    @Test
    void findBooksByAuthorIdWithJoin_shouldFilterByAuthor() {
        List<BookAuthorDTO> results =
                bookRepository.findBooksByAuthorIdWithJoin(savedAuthor.getId());

        assertThat(results).hasSize(2);
        assertThat(results).allMatch(dto -> dto.getAuthorId().equals(savedAuthor.getId()));
    }
}
