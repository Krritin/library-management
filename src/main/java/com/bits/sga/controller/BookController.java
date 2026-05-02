package com.bits.sga.controller;

import com.bits.sga.entity.Book;
import com.bits.sga.service.AuthorService;
import com.bits.sga.service.BookService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.findAllWithAuthors());
        return "books/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.findAll());
        return "books/add";
    }

    @PostMapping("/save")
    public String saveBook(@Valid @ModelAttribute("book") Book book,
                           BindingResult result,
                           @RequestParam(value = "authorId", required = false) Long authorId,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        if (authorId == null) {
            result.rejectValue("author", "author.required", "Please select an author.");
        }
        if (result.hasErrors()) {
            model.addAttribute("authors", authorService.findAll());
            return "books/add";
        }
        try {
            bookService.save(book, authorId);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Book '" + book.getTitle() + "' created successfully.");
            return "redirect:/books";
        } catch (DataIntegrityViolationException ex) {
            result.rejectValue("isbn", "duplicate.isbn", ex.getMessage());
            model.addAttribute("authors", authorService.findAll());
            return "books/add";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        model.addAttribute("authors", authorService.findAll());
        return "books/edit";
    }

    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable Long id,
                             @Valid @ModelAttribute("book") Book book,
                             BindingResult result,
                             @RequestParam(value = "authorId", required = false) Long authorId,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (authorId == null) {
            result.rejectValue("author", "author.required", "Please select an author.");
        }
        if (result.hasErrors()) {
            model.addAttribute("authors", authorService.findAll());
            return "books/edit";
        }
        try {
            bookService.update(id, book, authorId);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Book '" + book.getTitle() + "' updated successfully.");
            return "redirect:/books";
        } catch (DataIntegrityViolationException ex) {
            result.rejectValue("isbn", "duplicate.isbn", ex.getMessage());
            model.addAttribute("authors", authorService.findAll());
            return "books/edit";
        }
    }
}
