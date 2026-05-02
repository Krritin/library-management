package com.bits.sga.config;

import com.bits.sga.entity.Author;
import com.bits.sga.entity.Book;
import com.bits.sga.repository.AuthorRepository;
import com.bits.sga.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner seedData(AuthorRepository authorRepository,
                                       BookRepository bookRepository) {
        return args -> {
            if (authorRepository.count() > 0) {
                return;
            }

            List<Author> authors = List.of(
                    new Author("J.K. Rowling", "jkrowling@books.com", "British"),
                    new Author("George R.R. Martin", "grrmartin@books.com", "American"),
                    new Author("J.R.R. Tolkien", "jrrtolkien@books.com", "British"),
                    new Author("Agatha Christie", "agatha@books.com", "British"),
                    new Author("Stephen King", "sking@books.com", "American"),
                    new Author("Ruskin Bond", "ruskin@books.com", "Indian"),
                    new Author("Chetan Bhagat", "chetan@books.com", "Indian"),
                    new Author("Haruki Murakami", "murakami@books.com", "Japanese"),
                    new Author("Paulo Coelho", "coelho@books.com", "Brazilian"),
                    new Author("Dan Brown", "dbrown@books.com", "American")
            );
            List<Author> savedAuthors = authorRepository.saveAll(authors);

            List<Book> books = List.of(
                    new Book("Harry Potter and the Sorcerer's Stone", "978-0439708180", 499.0, 1997, savedAuthors.get(0)),
                    new Book("A Game of Thrones", "978-0553103540", 799.0, 1996, savedAuthors.get(1)),
                    new Book("The Hobbit", "978-0547928227", 599.0, 1937, savedAuthors.get(2)),
                    new Book("Murder on the Orient Express", "978-0062693662", 350.0, 1934, savedAuthors.get(3)),
                    new Book("The Shining", "978-0307743657", 650.0, 1977, savedAuthors.get(4)),
                    new Book("The Blue Umbrella", "978-8129115966", 199.0, 1980, savedAuthors.get(5)),
                    new Book("Five Point Someone", "978-8129104595", 250.0, 2004, savedAuthors.get(6)),
                    new Book("Norwegian Wood", "978-0375704024", 720.0, 1987, savedAuthors.get(7)),
                    new Book("The Alchemist", "978-0062315007", 399.0, 1988, savedAuthors.get(8)),
                    new Book("The Da Vinci Code", "978-0307474278", 550.0, 2003, savedAuthors.get(9))
            );
            bookRepository.saveAll(books);
        };
    }
}
