package java_spring.lesson_3.repository;

import jakarta.annotation.PostConstruct;
import java_spring.lesson_3.model.Book;
import java_spring.lesson_3.model.Issue;
import java_spring.lesson_3.model.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class ReaderRepository {

    private final List<Reader> readers;

    @Autowired
    private IssueRepository issueRepository;

    public ReaderRepository(List<Reader> readers) {
        this.readers = new ArrayList<>();
    }

    @PostConstruct
    public void generateData() {
        readers.addAll(List.of(
                new Reader("Игорь"),
                new Reader("Николай"),
                new Reader("Василий")
        ));
    }

    public Reader getReaderById(long id) {
        return readers.stream()
                .filter(book -> Objects.equals(book.getId(), id))
                .findFirst()
                .orElse(null);
    }

    public List<Reader> getAll() {
        return List.copyOf(readers);
    }

    public Reader createReader(String name) {
        Reader newReader = new Reader(name);
        readers.add(newReader);
        return newReader;
    }

    public void deleteReader(long id) {
        readers.remove(getReaderById(id));
    }

    public List<Issue> getReaderIssues(long id) {
        return issueRepository.getAll().stream()
                .filter(issue -> Objects.equals(issue.getReaderId(), id))
                .filter(issue -> issue.getReturned_at() == null)
                .toList();
    }

    public int countActiveBooks(long id) {
        return getReaderIssues(id).stream()
                .filter(issue -> issue.getReturned_at() == null)
                .toList().size();
    }
}
