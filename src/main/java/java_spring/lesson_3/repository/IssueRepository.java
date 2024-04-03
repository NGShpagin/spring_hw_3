package java_spring.lesson_3.repository;

import jakarta.annotation.PostConstruct;
import java_spring.lesson_3.model.Book;
import java_spring.lesson_3.model.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class IssueRepository {

    private final List<Issue> issues;

    @Autowired
    private BookRepository bookRepository;

    @PostConstruct
    public void generateData() {
        issues.addAll(List.of(
                new Issue(1, 1),
                new Issue(2, 2),
                new Issue(3, 1)
        ));
        issues.forEach(issue -> bookRepository.getBookById(issue.getBookId()).setFree(false));
    }

    public IssueRepository(List<Issue> issues) {
        this.issues = new ArrayList<>();
    }

    public void save(Issue issue) {
        issues.add(issue);
    }

    public void returnBook(Issue issue) {
        issue.setReturned_at(LocalDateTime.now());
    }

    public Issue getIssueById(long id) {
        return issues.stream()
                .filter(issue -> Objects.equals(issue.getId(), id))
                .findFirst()
                .orElse(null);
    }

    public List<Issue> getAll() {
        return List.copyOf(issues);
    }
}
