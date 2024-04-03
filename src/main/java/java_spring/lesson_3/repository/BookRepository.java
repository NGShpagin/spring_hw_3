package java_spring.lesson_3.repository;

import jakarta.annotation.PostConstruct;
import java_spring.lesson_3.model.Book;
import java_spring.lesson_3.model.Reader;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class BookRepository {

    private final List<Book> books;


    public BookRepository(List<Book> books) {
        this.books = new ArrayList<>();
    }

    @PostConstruct
    public void generateData() {
        books.addAll(List.of(
                new Book("Война и мир"),
                new Book("Чистый код"),
                new Book("Мертвые души"),
                new Book("newBook")
        ));
    }

    public Book getBookById(long id) {
        return books.stream()
                .filter(book -> Objects.equals(book.getId(), id))
                .findFirst()
                .orElse(null);
    }

    public List<Book> getAll() {
        return List.copyOf(books);
    }

    public void deleteBook(long id) {
        books.remove(getBookById(id));
    }

    public Book createBook(String name) {
        Book newBook = new Book(name);
        books.add(newBook);
        return newBook;
    }
}
