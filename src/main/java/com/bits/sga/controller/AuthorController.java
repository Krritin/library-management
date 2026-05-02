package com.bits.sga.controller;

import com.bits.sga.entity.Author;
import com.bits.sga.service.AuthorService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public String listAuthors(Model model) {
        model.addAttribute("authors", authorService.findAll());
        return "authors/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("author", new Author());
        return "authors/add";
    }

    @PostMapping("/save")
    public String saveAuthor(@Valid @ModelAttribute("author") Author author,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "authors/add";
        }
        try {
            authorService.save(author);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Author '" + author.getName() + "' created successfully.");
            return "redirect:/authors";
        } catch (DataIntegrityViolationException ex) {
            result.rejectValue("email", "duplicate.email", ex.getMessage());
            return "authors/add";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("author", authorService.findById(id));
        return "authors/edit";
    }

    @PostMapping("/update/{id}")
    public String updateAuthor(@PathVariable Long id,
                               @Valid @ModelAttribute("author") Author author,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "authors/edit";
        }
        try {
            authorService.update(id, author);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Author '" + author.getName() + "' updated successfully.");
            return "redirect:/authors";
        } catch (DataIntegrityViolationException ex) {
            result.rejectValue("email", "duplicate.email", ex.getMessage());
            return "authors/edit";
        }
    }
}
