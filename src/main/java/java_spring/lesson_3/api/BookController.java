package java_spring.lesson_3.api;

import java_spring.lesson_3.model.Book;
import java_spring.lesson_3.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @GetMapping(path = "{id}")
    public Book getBookById(@PathVariable long id) {
        return bookRepository.getBookById(id);
    }

    @GetMapping(path = "/all")
    public List<Book> getAll() {
        return bookRepository.getAll();
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookRepository.createBook(book.getName());
    }

    @DeleteMapping(path = "{id}")
    public Book deleteBookById(@PathVariable long id) {
        Book deletedBook = bookRepository.getBookById(id);
        bookRepository.deleteBook(id);
        return deletedBook;
    }
}
