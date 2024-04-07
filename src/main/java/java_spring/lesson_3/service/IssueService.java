package java_spring.lesson_3.service;

import java_spring.lesson_3.api.IssueRequest;
import java_spring.lesson_3.model.Issue;
import java_spring.lesson_3.repository.BookRepository;
import java_spring.lesson_3.repository.IssueRepository;
import java_spring.lesson_3.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class IssueService {

    // spring все это заинжектит
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;
    private final IssueRepository issueRepository;

    public Issue issue(IssueRequest request) {
        if (bookRepository.getBookById(request.getBookId()) == null) {
            throw new NoSuchElementException(String.format("Не найдена книга с идентификатором %d", request.getBookId()));
        }
        if (readerRepository.getReaderById(request.getReaderId()) == null) {
            throw new NoSuchElementException(String.format("Не найден читатель с идентификатором %d", request.getReaderId()));
        }
        // Нужно проверить, что у читателя нет книги на руках (или его лимит не превышает X книг)
        if (!bookRepository.getBookById(request.getBookId()).isFree()) {
            throw new ExceptionInInitializerError(String.format("Книга %d уже занята", request.getBookId()));
        }
        if (readerRepository.countActiveBooks(request.getReaderId()) > 0) {
            throw new ExceptionInInitializerError(String.format("У читателя %d уже есть книги на руках", request.getReaderId()));
        }

        System.out.println(readerRepository.countActiveBooks(request.getReaderId()));

        Issue issue = new Issue(request.getBookId(), request.getReaderId());
        bookRepository.getBookById(request.getBookId()).setFree(false);
        issueRepository.save(issue);
        return issue;
    }

}
