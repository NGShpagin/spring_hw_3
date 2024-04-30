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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private ReaderRepository readerRepository;

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
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(issue);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Object> getIssueById(@PathVariable long id) {
        if (issueRepository.getIssueById(id).isPresent())
            return ResponseEntity.status(HttpStatus.FOUND).body(issueRepository.getIssueById(id));
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping
    public ResponseEntity<List<Issue>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(issueRepository.getAll());
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Object> returnBook(@PathVariable long id) {
        final Issue issue;
        try {
            issue = issueRepository.getIssueById(id).orElseThrow();
            issueRepository.returnBook(issue);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(issue);
    }

    @GetMapping(path = "/ui/all")
    public String issues(Model model) {
        List<Issue> issues = issueRepository.getAll();
        model.addAttribute("issues", issues);
        model.addAttribute("books", bookRepository);
        model.addAttribute("readers", readerRepository);
        return "issues";
    }
}
