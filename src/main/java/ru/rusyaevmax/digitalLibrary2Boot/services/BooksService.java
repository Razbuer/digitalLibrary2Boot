package ru.rusyaevmax.digitalLibrary2Boot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rusyaevmax.digitalLibrary2Boot.models.Book;
import ru.rusyaevmax.digitalLibrary2Boot.repositories.BooksRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public Optional<Book> findById(Long id) {
        return booksRepository.findById(id);
    }

    public void save(Book book) {
        booksRepository.save(book);
    }

    public void release(Book book) {
        book.setOwner(null);
        booksRepository.save(book);
    }

    public void deleteById(Long id) {
        booksRepository.deleteById(id);
    }
}
