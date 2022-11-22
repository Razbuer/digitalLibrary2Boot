package ru.rusyaevmax.digitalLibrary2Boot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rusyaevmax.digitalLibrary2Boot.models.Person;
import ru.rusyaevmax.digitalLibrary2Boot.services.PeopleService;
import ru.rusyaevmax.digitalLibrary2Boot.util.PersonValidator;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PeopleService peopleService, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("people", peopleService.findAll());

        return "people/index";
    }

    @GetMapping("{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        Optional<Person> person = peopleService.findById(id);

        if (person.isEmpty())
            return "redirect:/errors/404";

        model.addAttribute("person", person.get());


        return "/people/show";
    }

    @GetMapping("new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "people/new";

        peopleService.save(person);

        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        Optional<Person> person = peopleService.findById(id);

        if (person.isEmpty())
            return "redirect:/error/404";

        model.addAttribute("person", person.get());

        return "people/edit";
    }

    @PatchMapping("{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "people/edit";

        peopleService.save(person);

        return "redirect:/people";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") Long id) {
        peopleService.deleteById(id);

        return "redirect:/people";
    }
}
