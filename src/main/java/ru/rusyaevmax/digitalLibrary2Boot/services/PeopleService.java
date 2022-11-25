package ru.rusyaevmax.digitalLibrary2Boot.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rusyaevmax.digitalLibrary2Boot.models.Person;
import ru.rusyaevmax.digitalLibrary2Boot.repositories.PeopleRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Optional<Person> findById(Long id) {
        Optional<Person> person = peopleRepository.findById(id);

        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());

            person.ifPresent(value -> value.getBooks().stream()
                    .filter(book -> book.getTakenFrom() != null)
                    .forEach(book -> book.setOverdue(book.getTakenFrom().isBefore(LocalDateTime.now().minusDays(10))))
            );
        }

        return person;
    }

    public Optional<Person> findByFullName(String fullName) {
        return peopleRepository.findByFullName(fullName);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void delete(Long id) {
        peopleRepository.deleteById(id);
    }
}
