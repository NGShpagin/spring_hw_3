package java_spring.lesson_3.api;

import java_spring.lesson_3.model.Book;
import java_spring.lesson_3.model.Issue;
import java_spring.lesson_3.model.Reader;
import java_spring.lesson_3.repository.BookRepository;
import java_spring.lesson_3.repository.ReaderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/readers")
public class ReaderController {

    @Autowired
    ReaderRepository readerRepository;

    @Autowired
    BookRepository bookRepository;

    @GetMapping(path = "/all")
    public List<Reader> getAll() {
        return readerRepository.getAll();
    }

    @GetMapping(path = "/{id}")
    public Reader getReaderById(@PathVariable long id) {
        return readerRepository.getReaderById(id);
    }

    @PostMapping
    public Reader createReader(@RequestBody Reader reader) {
        return readerRepository.createReader(reader.getName());
    }

    @DeleteMapping(path = "{id}")
    public Reader deleteBookById(@PathVariable long id) {
        Reader deletedReader = readerRepository.getReaderById(id);
        readerRepository.deleteReader(id);
        return deletedReader;
    }

    @GetMapping(path = "/{id}/issues")
    public List<Issue> getReaderIssues(@PathVariable long id) {
        return readerRepository.getReaderIssues(id);
    }

    @GetMapping
    public String readers(Model model) {
        List<Reader> readers = readerRepository.getAll();
        model.addAttribute("readers", readers);
        return "readers";
    }

    @GetMapping("/ui/{id}")
    public String getReaderUI(@PathVariable long id, Model model) {
        Reader myReader = readerRepository.getReaderById(id);
        List<Issue> readerIssues = getReaderIssues(myReader.getId()).stream()
                .filter(issue -> !bookRepository.getBookById(issue.getBookId()).isFree()).toList();
        model.addAttribute("reader", myReader);
        model.addAttribute("issues", readerIssues);
        model.addAttribute("books", bookRepository);
        return "readerId";
    }
}
