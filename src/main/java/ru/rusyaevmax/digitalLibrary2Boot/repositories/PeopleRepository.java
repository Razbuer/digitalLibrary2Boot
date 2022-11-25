package ru.rusyaevmax.digitalLibrary2Boot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.rusyaevmax.digitalLibrary2Boot.models.Person;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByFullName(String fullName);
}
