package ru.rusyaevmax.digitalLibrary2Boot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rusyaevmax.digitalLibrary2Boot.models.Person;
import ru.rusyaevmax.digitalLibrary2Boot.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
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
        return peopleRepository.findById(id);
    }

    public Optional<Person> findByFullName(String fullName) {
        return peopleRepository.findByFullName(fullName);
    }

    public void save(Person person) {
        peopleRepository.save(person);
    }

    public void deleteById(Long id) {
        peopleRepository.deleteById(id);
    }
}
