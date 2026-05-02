package com.bits.sga.service;

import com.bits.sga.entity.Author;
import com.bits.sga.exception.ResourceNotFoundException;
import com.bits.sga.repository.AuthorRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public Author findById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
    }

    public Author save(Author author) {
        if (author.getId() == null && author.getEmail() != null
                && authorRepository.existsByEmail(author.getEmail())) {
            throw new DataIntegrityViolationException(
                    "An author with email '" + author.getEmail() + "' already exists.");
        }
        return authorRepository.save(author);
    }

    public Author update(Long id, Author updated) {
        Author existing = findById(id);
        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setNationality(updated.getNationality());
        return authorRepository.save(existing);
    }

    public void deleteById(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Author not found with id: " + id);
        }
        authorRepository.deleteById(id);
    }
}
