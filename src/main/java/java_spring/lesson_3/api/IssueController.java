package java_spring.lesson_3.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java_spring.lesson_3.model.Issue;
import java_spring.lesson_3.repository.BookRep;
import java_spring.lesson_3.repository.IssueRep;
import java_spring.lesson_3.repository.ReaderRep;
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
@Tag(name = "Issue")
public class IssueController {

    @Autowired
    private IssueService issueService;
    @Autowired
    private IssueRep issueRepository;
    @Autowired
    private ReaderRep readerRepository;
    @Autowired
    private BookRep bookRepository;

    /**
     * Добавить новую запись
     * @param request - добавляемая запись: bookId и readerId
     * @return добавленная запись
     */
    @PostMapping
    @Operation(summary = "Add new issue", description = "Добавить новую запись")
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

    /**
     * Получить запись по ее идентификатору
     * @param id - идентификатор записи
     * @return запись
     */
    @GetMapping(path = "/{id}")
    @Operation(summary = "Get issue by Id", description = "Получить запись по Id")
    public ResponseEntity<Object> getIssueById(@PathVariable long id) {
        Optional<Issue> issue = issueRepository.findById(id);
        if (issue.isPresent())
            return ResponseEntity.status(HttpStatus.FOUND).body(issue);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    /**
     * Получить все записи
     * @return список книг
     */
    @GetMapping
    @Operation(summary = "Get all issues", description = "Получить все записи")
    public ResponseEntity<List<Issue>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(issueRepository.findAll());
    }

    /**
     * Вернуть книгу по идентификатору записи
     * @param id - идентификатор записи
     * @return запись
     */
    @PutMapping(path = "/{id}")
    @Operation(summary = "Get not closed issues", description = "Получить не закрытые записи")
    public ResponseEntity<Object> returnBook(@PathVariable long id) {
        final Issue issue;
        try {
            issue = issueRepository.findById(id).orElseThrow();
            issueService.returnBook(issue);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(issue);
    }

//    @GetMapping(path = "/ui/all")
//    @Operation(summary = "Get not closed issues", description = "Получить не закрытые записи")
//    public String issues(Model model) {
//        List<Issue> issues = issueRepository.findAll();
//        model.addAttribute("issues", issues);
//        model.addAttribute("books", bookRepository);
//        model.addAttribute("readers", readerRepository);
//        return "issues";
//    }
}
