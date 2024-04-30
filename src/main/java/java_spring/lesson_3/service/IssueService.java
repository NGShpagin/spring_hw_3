package java_spring.lesson_3.service;

import java_spring.lesson_3.api.IssueRequest;
import java_spring.lesson_3.model.Book;
import java_spring.lesson_3.model.Issue;
import java_spring.lesson_3.repository.BookRepository;
import java_spring.lesson_3.repository.IssueRepository;
import java_spring.lesson_3.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class IssueService {

    // spring все это заинжектит
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ReaderRepository readerRepository;
    @Autowired
    private IssueRepository issueRepository;

    public Issue issue(IssueRequest request) {
        if (bookRepository.getBookById(request.getBookId()).isEmpty()) {
            throw new NoSuchElementException(String.format("Не найдена книга с идентификатором %d", request.getBookId()));
        }
        if (readerRepository.getReaderById(request.getReaderId()).isEmpty()) {
            throw new NoSuchElementException(String.format("Не найден читатель с идентификатором %d", request.getReaderId()));
        }
        // Нужно проверить, что у читателя нет книги на руках (или его лимит не превышает X книг)
        if (!bookRepository.getBookById(request.getBookId()).orElseThrow().isFree()) {
            throw new ExceptionInInitializerError(String.format("Книга %d уже занята", request.getBookId()));
        }
        if (readerRepository.countActiveBooks(request.getReaderId()) > 0) {
            throw new ExceptionInInitializerError(String.format("У читателя %d уже есть книги на руках", request.getReaderId()));
        }

        System.out.println(readerRepository.countActiveBooks(request.getReaderId()));

        if (bookRepository.getBookById(request.getBookId()).isPresent()
                && readerRepository.getReaderById(request.getReaderId()).isPresent()) {
            Issue issue = new Issue(bookRepository.getBookById(request.getBookId()).orElseThrow(),
                    readerRepository.getReaderById(request.getReaderId()).orElseThrow());
            bookRepository.getBookById(request.getBookId()).ifPresent(book -> book.setFree(false));
            issueRepository.save(issue);
            log.info(issue.getBook().getName(), issue.getReader().getName());
            return issue;
        } else return null;
    }

}
