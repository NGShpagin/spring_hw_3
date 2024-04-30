package java_spring.lesson_3.repository;

import jakarta.annotation.PostConstruct;
import java_spring.lesson_3.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BookRepository {
    private final BookRep bookRepository;

    @Autowired
    public BookRepository(BookRep bookRepository) {
        this.bookRepository = bookRepository;
    }

//    @PostConstruct
//    public void generateData() {
//        bookRepository.saveAll(List.of(
//                new Book("Война и мир"),
//                new Book("Чистый код"),
//                new Book("Мертвые души"),
//                new Book("newBook1"),
//                new Book("newBook2"),
//                new Book("newBook3")
//        ));
//    }

    public Optional<Book> getBookById(long id) {
        return bookRepository.findById(id);
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public void deleteBook(long id) {
        bookRepository.deleteById(id);
    }

    public Book createBook(String name) {
        return bookRepository.save(new Book(name));
    }
}
