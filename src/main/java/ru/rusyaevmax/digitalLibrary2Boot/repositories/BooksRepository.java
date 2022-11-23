package ru.rusyaevmax.digitalLibrary2Boot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.rusyaevmax.digitalLibrary2Boot.models.Book;

@Repository
@Transactional(readOnly = true)
public interface BooksRepository extends JpaRepository<Book, Long> {

}
