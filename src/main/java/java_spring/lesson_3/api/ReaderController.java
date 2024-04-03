package java_spring.lesson_3.api;

import java_spring.lesson_3.model.Book;
import java_spring.lesson_3.model.Issue;
import java_spring.lesson_3.model.Reader;
import java_spring.lesson_3.repository.ReaderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/readers")
public class ReaderController {

    @Autowired
    ReaderRepository readerRepository;

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
}
