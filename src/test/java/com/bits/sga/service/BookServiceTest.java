package com.bits.sga.service;

import com.bits.sga.dto.BookAuthorDTO;
import com.bits.sga.entity.Author;
import com.bits.sga.entity.Book;
import com.bits.sga.exception.ResourceNotFoundException;
import com.bits.sga.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private BookService bookService;

    private Author author;
    private Book book;

    @BeforeEach
    void setUp() {
        author = new Author("J.K. Rowling", "jk@example.com", "British");
        author.setId(1L);

        book = new Book("Harry Potter", "ISBN-100", 499.0, 1997, author);
        book.setId(10L);
    }

    @Test
    void findById_shouldReturnBook_whenExists() {
        when(bookRepository.findById(10L)).thenReturn(Optional.of(book));

        Book result = bookService.findById(10L);

        assertThat(result.getTitle()).isEqualTo("Harry Potter");
    }

    @Test
    void findById_shouldThrow_whenBookMissing() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void save_shouldPersistBook_whenIsbnIsUnique() {
        Book newBook = new Book("New Book", "ISBN-NEW", 250.0, 2023, null);
        when(bookRepository.existsByIsbn("ISBN-NEW")).thenReturn(false);
        when(authorService.findById(1L)).thenReturn(author);
        when(bookRepository.save(any(Book.class))).thenReturn(newBook);

        Book result = bookService.save(newBook, 1L);

        assertThat(result).isNotNull();
        assertThat(newBook.getAuthor()).isEqualTo(author);
        verify(bookRepository).save(newBook);
    }

    @Test
    void save_shouldThrow_whenIsbnAlreadyExists() {
        Book duplicate = new Book("Duplicate", "ISBN-DUP", 100.0, 2020, null);
        when(bookRepository.existsByIsbn("ISBN-DUP")).thenReturn(true);

        assertThatThrownBy(() -> bookService.save(duplicate, 1L))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("ISBN-DUP");

        verify(bookRepository, never()).save(any());
    }

    @Test
    void update_shouldModifyExistingBook() {
        Book updates = new Book("Updated Title", "ISBN-100", 599.0, 1998, null);
        when(bookRepository.findById(10L)).thenReturn(Optional.of(book));
        when(authorService.findById(1L)).thenReturn(author);
        when(bookRepository.save(any(Book.class))).thenAnswer(inv -> inv.getArgument(0));

        Book result = bookService.update(10L, updates, 1L);

        assertThat(result.getTitle()).isEqualTo("Updated Title");
        assertThat(result.getPrice()).isEqualTo(599.0);
        assertThat(result.getAuthor()).isEqualTo(author);
    }

    @Test
    void findAllWithAuthors_shouldDelegateToRepository() {
        BookAuthorDTO dto = new BookAuthorDTO(10L, "Harry Potter", "ISBN-100",
                499.0, 1997, 1L, "J.K. Rowling", "British");
        when(bookRepository.findAllBooksWithAuthors()).thenReturn(List.of(dto));

        List<BookAuthorDTO> results = bookService.findAllWithAuthors();

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getAuthorName()).isEqualTo("J.K. Rowling");
    }
}
