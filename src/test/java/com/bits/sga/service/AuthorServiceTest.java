package com.bits.sga.service;

import com.bits.sga.entity.Author;
import com.bits.sga.exception.ResourceNotFoundException;
import com.bits.sga.repository.AuthorRepository;
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
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author("Ruskin Bond", "ruskin@example.com", "Indian");
        author.setId(1L);
    }

    @Test
    void findAll_returnsAllAuthors() {
        when(authorRepository.findAll()).thenReturn(List.of(author));

        List<Author> result = authorService.findAll();

        assertThat(result).hasSize(1);
    }

    @Test
    void findById_returnsAuthor_whenExists() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        Author result = authorService.findById(1L);

        assertThat(result.getName()).isEqualTo("Ruskin Bond");
    }

    @Test
    void findById_throws_whenMissing() {
        when(authorRepository.findById(42L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authorService.findById(42L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void save_persistsAuthor_whenEmailUnique() {
        Author fresh = new Author("New", "new@example.com", "Indian");
        when(authorRepository.existsByEmail("new@example.com")).thenReturn(false);
        when(authorRepository.save(any(Author.class))).thenReturn(fresh);

        Author result = authorService.save(fresh);

        assertThat(result).isNotNull();
        verify(authorRepository).save(fresh);
    }

    @Test
    void save_throws_whenEmailDuplicate() {
        Author dup = new Author("Dup", "dup@example.com", "Indian");
        when(authorRepository.existsByEmail("dup@example.com")).thenReturn(true);

        assertThatThrownBy(() -> authorService.save(dup))
                .isInstanceOf(DataIntegrityViolationException.class);

        verify(authorRepository, never()).save(any());
    }

    @Test
    void update_modifiesFields() {
        Author updates = new Author("Updated", "updated@example.com", "British");
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorRepository.save(any(Author.class))).thenAnswer(inv -> inv.getArgument(0));

        Author result = authorService.update(1L, updates);

        assertThat(result.getName()).isEqualTo("Updated");
        assertThat(result.getEmail()).isEqualTo("updated@example.com");
        assertThat(result.getNationality()).isEqualTo("British");
    }
}
