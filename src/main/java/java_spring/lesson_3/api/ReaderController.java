package java_spring.lesson_3.api;

import java_spring.lesson_3.model.Issue;
import java_spring.lesson_3.model.Reader;
import java_spring.lesson_3.repository.BookRep;
import java_spring.lesson_3.repository.IssueRep;
import java_spring.lesson_3.repository.ReaderRep;
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
    ReaderRep readerRepository;
    @Autowired
    IssueRep issueRepository;

    @GetMapping
    public ResponseEntity<List<Reader>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(readerRepository.findAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Object> getReaderById(@PathVariable long id) {
        Optional<Reader> reader = readerRepository.findById(id);
        if (reader.isPresent()) return ResponseEntity.status(HttpStatus.FOUND).body(reader);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping
    public ResponseEntity<Object> createReader(@RequestBody Reader reader) {
        return ResponseEntity.status(HttpStatus.CREATED).body(readerRepository.save(reader));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Object> deleteReaderById(@PathVariable long id) {
        Optional<Reader> reader = readerRepository.findById(id);
        if (reader.isPresent()) {
            readerRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(reader);
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping(path = "/{id}/issues")
    public ResponseEntity<List<Issue>> getReaderAllIssues(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(issueRepository.findAllByReaderId(id));
    }

    @GetMapping(path = "/{id}/books")
    public ResponseEntity<List<Issue>> getReaderActiveIssues(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(issueRepository.findActiveByReaderId(id));
    }

    @GetMapping(path = "/ui/all")
    public String readers(Model model) {
        List<Reader> readers = readerRepository.findAll();
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
