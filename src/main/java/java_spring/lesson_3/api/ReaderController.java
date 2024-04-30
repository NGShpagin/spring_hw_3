package java_spring.lesson_3.api;

import java_spring.lesson_3.model.Book;
import java_spring.lesson_3.model.Issue;
import java_spring.lesson_3.model.Reader;
import java_spring.lesson_3.repository.BookRepository;
import java_spring.lesson_3.repository.ReaderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/readers")
public class ReaderController {

    @Autowired
    ReaderRepository readerRepository;

    @Autowired
    BookRepository bookRepository;

    @GetMapping
    public ResponseEntity<List<Reader>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(readerRepository.getAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Object> getReaderById(@PathVariable long id) {
        if (readerRepository.getReaderById(id).isPresent())
            return ResponseEntity.status(HttpStatus.FOUND).body(readerRepository.getReaderById(id));
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping
    public ResponseEntity<Object> createReader(@RequestBody Reader reader) {
        return ResponseEntity.status(HttpStatus.CREATED).body(readerRepository.createReader(reader.getName()));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Object> deleteReaderById(@PathVariable long id) {
        if (readerRepository.getReaderById(id).isPresent()) {
            Reader deletedReader = readerRepository.getReaderById(id).orElseThrow();
            readerRepository.deleteReader(id);
            return ResponseEntity.status(HttpStatus.OK).body(deletedReader);
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping(path = "/{id}/issues")
    public ResponseEntity<List<Issue>> getReaderIssues(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(readerRepository.getReaderIssues(id));
    }

    @GetMapping(path = "/{id}/books")
    public ResponseEntity<List<Issue>> getReaderBooks(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(readerRepository.getReaderBooks(id));
    }

    @GetMapping(path = "/ui/all")
    public String readers(Model model) {
        List<Reader> readers = readerRepository.getAll();
        model.addAttribute("readers", readers);
        return "readers";
    }

//    @GetMapping("/ui/{id}")
//    public String getReaderUI(@PathVariable long id, Model model) {
//        Reader myReader = readerRepository.getReaderById(id);
//        List<Issue> readerIssues = getReaderIssues(myReader.getId()).stream()
//                .filter(issue -> !bookRepository.getBookById(issue.getBookId()).isFree()).toList();
//        model.addAttribute("reader", myReader);
//        model.addAttribute("issues", readerIssues);
//        model.addAttribute("books", bookRepository);
//        return "readerId";
//    }
}
