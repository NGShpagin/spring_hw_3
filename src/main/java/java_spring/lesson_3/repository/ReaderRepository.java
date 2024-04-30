package java_spring.lesson_3.repository;

import jakarta.annotation.PostConstruct;
import java_spring.lesson_3.model.Book;
import java_spring.lesson_3.model.Issue;
import java_spring.lesson_3.model.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ReaderRepository {
    private final ReaderRep readerRepository;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private BookRepository bookRepository;

    public ReaderRepository(ReaderRep readerRepository) {
        this.readerRepository = readerRepository;
    }

//    @PostConstruct
//    public void generateData() {
//        readerRepository.saveAll(List.of(
//                new Reader("Игорь"),
//                new Reader("Николай"),
//                new Reader("Василий")
//        ));
//    }

    public Optional<Reader> getReaderById(long id) {
        return readerRepository.findById(id);
    }

    public List<Reader> getAll() {
        return readerRepository.findAll();
    }

    public Reader createReader(String name) {
        return readerRepository.save(new Reader(name));
    }

    public void deleteReader(long id) {
        readerRepository.deleteById(id);
    }

    public List<Issue> getReaderIssues(Long id) {
        return issueRepository.getAllByReader(id);
    }

    public List<Issue> getReaderBooks(Long id) {
        return issueRepository.getAllByReader(id).stream()
                .filter(issue -> issue.getReturned_at() == null)
                .toList();
    }

    public int countActiveBooks(long id) {
        return getReaderIssues(id).stream()
                .filter(issue -> issue.getReturned_at() == null)
                .toList().size();
    }
}
