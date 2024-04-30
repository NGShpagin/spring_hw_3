package java_spring.lesson_3.api;

import java_spring.lesson_3.model.Book;
import java_spring.lesson_3.repository.BookRep;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    BookRep bookRepository;

    @GetMapping(path = "/{id}")
    public ResponseEntity<Object> getBookById(@PathVariable long id) {
        if (bookRepository.findById(id).isPresent())
            return ResponseEntity.status(HttpStatus.FOUND).body(bookRepository.findById(id));
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(bookRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Object> createBook(@RequestBody Book book) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookRepository.save(book));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Object> deleteBookById(@PathVariable long id) {
        if (bookRepository.findById(id).isPresent()) {
            Book deletedBook = bookRepository.findById(id).orElseThrow();
            bookRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(deletedBook);
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping(path = "ui/all")
    public String books(Model model) {
        List<Book> books = bookRepository.findAll().stream()
                .filter(Book::isFree).toList();
        model.addAttribute("books", books);
        return "books";
    }

}
