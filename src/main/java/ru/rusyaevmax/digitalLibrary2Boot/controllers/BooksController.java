package ru.rusyaevmax.digitalLibrary2Boot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rusyaevmax.digitalLibrary2Boot.models.Book;
import ru.rusyaevmax.digitalLibrary2Boot.models.Person;
import ru.rusyaevmax.digitalLibrary2Boot.services.BooksService;
import ru.rusyaevmax.digitalLibrary2Boot.services.PeopleService;

import javax.validation.Valid;
import java.time.LocalDateTime;
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
    public String index(
            @RequestParam(value = "sort_by_year", required = false, defaultValue = "false") boolean sortByYear,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "books_per_page", required = false, defaultValue = "5") Integer booksPerPage,
            Model model
    ) {
        Page<Book> books = booksService.findAll(sortByYear, page, booksPerPage);

        model.addAttribute("books", books);
        model.addAttribute("sort_by_year", sortByYear);
        model.addAttribute("page", page);
        model.addAttribute("books_per_page", booksPerPage);

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
        booksService.release(id);

        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") Long id, @ModelAttribute("person") Person person) {
        booksService.assign(id, person);

        return "redirect:/books/{id}";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") Long id) {
        booksService.delete(id);

        return "redirect:/books";
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "text", required = false, defaultValue = "") String text, Model model) {
        model.addAttribute("text", text);

        if (!text.isEmpty())
            model.addAttribute("result", booksService.findBookByNameStartingWith(text));

        return "/books/search";
    }
}
