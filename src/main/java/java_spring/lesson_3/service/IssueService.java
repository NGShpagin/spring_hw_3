package java_spring.lesson_3.service;

import java_spring.lesson_3.api.IssueRequest;
import java_spring.lesson_3.model.Book;
import java_spring.lesson_3.model.Issue;
import java_spring.lesson_3.model.Reader;
import java_spring.lesson_3.repository.BookRepository;
import java_spring.lesson_3.repository.IssueRepository;
import java_spring.lesson_3.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.OverridingClassLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.ExceptionTypeFilter;
import org.w3c.dom.events.EventException;

import javax.management.AttributeNotFoundException;
import javax.management.RuntimeErrorException;
import javax.naming.ConfigurationException;
import java.rmi.server.ExportException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IssueService {

    // spring все это заинжектит
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;
    private final IssueRepository issueRepository;

    public Issue issue(IssueRequest request) {
        if (bookRepository.getBookById(request.getBookId()) == null) {
            throw new NoSuchElementException(STR."Не найдена книга с идентификатором \"\{request.getBookId()}\"");
        }
        if (readerRepository.getReaderById(request.getReaderId()) == null) {
            throw new NoSuchElementException(STR."Не найден читатель с идентификатором \"\{request.getReaderId()}\"");
        }
        // Нужно проверить, что у читателя нет книги на руках (или его лимит не превышает X книг)
        if (!bookRepository.getBookById(request.getBookId()).isFree()) {
            throw new ExceptionInInitializerError(STR."Книга \"\{request.getBookId()}\"уже занята");
        }
        if (readerRepository.countActiveBooks(request.getReaderId()) > 0) {
            throw new ExceptionInInitializerError(STR."У читателя \"\{request.getReaderId()}\"уже есть книги на руках");
        }

        System.out.println(readerRepository.countActiveBooks(request.getReaderId()));

        Issue issue = new Issue(request.getBookId(), request.getReaderId());
        bookRepository.getBookById(request.getBookId()).setFree(false);
        issueRepository.save(issue);
        return issue;
    }

}
