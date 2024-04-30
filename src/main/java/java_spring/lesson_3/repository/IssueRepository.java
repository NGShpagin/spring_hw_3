package java_spring.lesson_3.repository;

import jakarta.annotation.PostConstruct;
import java_spring.lesson_3.model.Book;
import java_spring.lesson_3.model.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class IssueRepository {
    private final IssueRep issueRepository;

    @Autowired
    private BookRep bookRepository;

    @Autowired
    private ReaderRep readerRepository;

    @Autowired
    public IssueRepository(IssueRep issueRepository) {
        this.issueRepository = issueRepository;
    }

//    @PostConstruct
//    public void generateData() {
//        issueRepository.saveAll(List.of(
//                new Issue(bookRepository.findById(1L).orElseThrow(), readerRepository.findById(3L).orElseThrow()),
//                new Issue(bookRepository.findById(2L).orElseThrow(), readerRepository.findById(2L).orElseThrow()),
//                new Issue(bookRepository.findById(3L).orElseThrow(), readerRepository.findById(1L).orElseThrow())
//        ));
////        issues.forEach(issue -> bookRepository.getBookById(issue.getBookId()).setFree(false));
//    }

    public void save(Issue issue) {
        issueRepository.save(issue);
    }

    public void returnBook(Issue issue) {
        Optional<Book> book = bookRepository.findById(issue.getBook().getId());
        book.ifPresent(value -> {
            value.setFree(true);
            issue.setReturned_at(LocalDateTime.now());
            bookRepository.save(value);
            issueRepository.save(issue);
        });
    }

    public Optional<Issue> getIssueById(long id) {
        return issueRepository.findById(id);
    }

    public List<Issue> getAll() {
        return issueRepository.findAll();
    }

    public List<Issue> getAllByReader(Long id) {
        return issueRepository.findAll().stream()
                .filter(issue -> issue.getReader().equals(readerRepository.findById(id).orElseThrow()))
                .toList();
    }
}
