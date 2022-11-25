package ru.rusyaevmax.digitalLibrary2Boot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rusyaevmax.digitalLibrary2Boot.models.Book;
import ru.rusyaevmax.digitalLibrary2Boot.models.Person;
import ru.rusyaevmax.digitalLibrary2Boot.repositories.BooksRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public Page<Book> findAll(boolean sortByYear, int page, int booksPerPage) {
        if (sortByYear)
            return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year")));
        else
            return booksRepository.findAll(PageRequest.of(page, booksPerPage));
    }

    public Optional<Book> findById(Long id) {
        return booksRepository.findById(id);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void assign(Long id, Person person) {
        booksRepository.findById(id).ifPresent(
            book -> {
                book.setOwner(person);
                book.setTakenFrom(LocalDateTime.now());
            });
    }

    @Transactional
    public void release(Long id) {
        booksRepository.findById(id).ifPresent(
            book -> {
                book.setOwner(null);
                book.setTakenFrom(null);
            });
    }

    @Transactional
    public void delete(Long id) {
        booksRepository.deleteById(id);
    }

    public List<Book> findBookByNameStartingWith(String text) {
        return booksRepository.findBookByNameStartingWith(text);
    }
}
