package com.bits.sga.service;

import com.bits.sga.dto.BookAuthorDTO;
import com.bits.sga.entity.Author;
import com.bits.sga.entity.Book;
import com.bits.sga.exception.ResourceNotFoundException;
import com.bits.sga.repository.BookRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;

    public BookService(BookRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<BookAuthorDTO> findAllWithAuthors() {
        return bookRepository.findAllBooksWithAuthors();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    public Book save(Book book, Long authorId) {
        if (book.getId() == null && book.getIsbn() != null
                && bookRepository.existsByIsbn(book.getIsbn())) {
            throw new DataIntegrityViolationException(
                    "A book with ISBN '" + book.getIsbn() + "' already exists.");
        }
        Author author = authorService.findById(authorId);
        book.setAuthor(author);
        return bookRepository.save(book);
    }

    public Book update(Long id, Book updated, Long authorId) {
        Book existing = findById(id);
        existing.setTitle(updated.getTitle());
        existing.setIsbn(updated.getIsbn());
        existing.setPrice(updated.getPrice());
        existing.setPublishedYear(updated.getPublishedYear());
        Author author = authorService.findById(authorId);
        existing.setAuthor(author);
        return bookRepository.save(existing);
    }

    public void deleteById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }
}
