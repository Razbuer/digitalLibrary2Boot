package ru.rusyaevmax.digitalLibrary2Boot.controllers;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rusyaevmax.digitalLibrary2Boot.models.Book;
import ru.rusyaevmax.digitalLibrary2Boot.models.Person;
import ru.rusyaevmax.digitalLibrary2Boot.services.BooksService;
import ru.rusyaevmax.digitalLibrary2Boot.services.PeopleService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("books", booksService.findAll());

        return "/books/index";
    }

    @GetMapping("{id}")
    public String show(@PathVariable("id") Long id, Model model, @ModelAttribute("person") Person person) {
        Optional<Book> book = booksService.findById(id);

        if (book.isEmpty())
            return "redirect:/errors/404";

        model.addAttribute("book", book.get());
        model.addAttribute("people", peopleService.findAll());

        return "/books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "/books/new";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "/books/new";

        booksService.save(book);

        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        Optional<Book> book = booksService.findById(id);

        if (book.isEmpty())
            return "redirect:/errors/404";

        model.addAttribute("book", book.get());

        return "/books/edit";
    }

    @PatchMapping("/{id}")
    public String save(@PathVariable("id") Long id, @ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "/books/edit";

        booksService.save(book);

        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") Long id) {
        Optional<Book> book = booksService.findById(id);

        if (book.isEmpty())
            return "redirect:/errors/404";

        booksService.release(book.get());

        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") Long id, @ModelAttribute("person") Person person) {
        Optional<Book> book = booksService.findById(id);

        if (book.isEmpty())
            return "redirect:/errors/404";

        book.get().setOwner(person);

        booksService.save(book.get());

        return "redirect:/books/{id}";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") Long id) {
        booksService.deleteById(id);

        return "redirect:/books";
    }
}
