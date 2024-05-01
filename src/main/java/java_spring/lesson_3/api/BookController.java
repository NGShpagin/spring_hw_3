package java_spring.lesson_3.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Book")
public class BookController {

    @Autowired
    BookRep bookRepository;

    /**
     * Получить книгу по ее идентификатору
     * @param id - идентификатор книги
     * @return полученная книга
     */
    @GetMapping(path = "/{id}")
    @Operation(summary = "Get book by Id", description = "Получить книгу по Id")
    public ResponseEntity<Object> getBookById(@PathVariable long id) {
        if (bookRepository.findById(id).isPresent())
            return ResponseEntity.status(HttpStatus.FOUND).body(bookRepository.findById(id));
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    /**
     * Получить все книги
     * @return список книг
     */
    @GetMapping
    @Operation(summary = "Get all books", description = "Получить все книги")
    public ResponseEntity<List<Book>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(bookRepository.findAll());
    }

    /**
     * Добавить новую книгу
     * @param book - добавляемая книга: name
     * @return созданная книга
     */
    @PostMapping
    @Operation(summary = "Add new book", description = "Добавить новую книгу")
    public ResponseEntity<Object> createBook(@RequestBody Book book) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookRepository.save(book));
    }

    /**
     * Удалить книгу по ее идентификатору
     * @param id - идентификатор книги
     * @return удаленная книга
     */
    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete book by Id", description = "Удалить книгу по Id")
    public ResponseEntity<Object> deleteBookById(@PathVariable long id) {
        if (bookRepository.findById(id).isPresent()) {
            Book deletedBook = bookRepository.findById(id).orElseThrow();
            bookRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(deletedBook);
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

//    @GetMapping(path = "ui/all")
//    public String books(Model model) {
//        List<Book> books = bookRepository.findAll().stream()
//                .filter(Book::isFree).toList();
//        model.addAttribute("books", books);
//        return "books";
//    }

}
