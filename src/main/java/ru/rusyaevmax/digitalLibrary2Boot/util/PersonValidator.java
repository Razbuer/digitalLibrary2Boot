package ru.rusyaevmax.digitalLibrary2Boot.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.rusyaevmax.digitalLibrary2Boot.models.Person;
import ru.rusyaevmax.digitalLibrary2Boot.services.PeopleService;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {
    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Person.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        Optional<Person> foundPerson = peopleService.findByFullName(person.getFullName());

        // Проверяем сначала есть ли в базе пользователь с таким же именем, потом проверяем, что мы не нашли того же пользователя
        if (foundPerson.isPresent())
            if (!foundPerson.get().equals(person))
                errors.rejectValue("fullName", "", "Читатель с таким именем уже существует");
    }
}
