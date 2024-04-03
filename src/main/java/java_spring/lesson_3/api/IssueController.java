package java_spring.lesson_3.api;

import java_spring.lesson_3.model.Book;
import java_spring.lesson_3.model.Issue;
import java_spring.lesson_3.repository.BookRepository;
import java_spring.lesson_3.repository.IssueRepository;
import java_spring.lesson_3.repository.ReaderRepository;
import java_spring.lesson_3.service.IssueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.AttributeNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private BookRepository bookRepository;

    // GET /issue/{id}

    @PostMapping
    public ResponseEntity<Issue> issueBook(@RequestBody IssueRequest request) {
        log.info("Получен запрос на выдачу: readerId = {}, bookId = {}", request.getReaderId(), request.getBookId());

        final Issue issue;
        try {
            issue = issueService.issue(request);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (ExceptionInInitializerError e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(issue);
    }

    @GetMapping(path = "/{id}")
    public Issue getIssueById(@PathVariable long id) {
        return issueRepository.getIssueById(id);
    }

    @GetMapping(path = "/all")
    public List<Issue> getAll() {
        return issueRepository.getAll();
    }

    @PutMapping(path = "/{id}")
    public Issue returnBook(@PathVariable long id) {
        Issue issue = issueRepository.getIssueById(id);
        bookRepository.getBookById(issue.getBookId()).setFree(true);
        issueRepository.returnBook(issue);
        return issue;
    }
}
