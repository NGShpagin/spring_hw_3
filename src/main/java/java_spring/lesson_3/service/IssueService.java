package java_spring.lesson_3.service;

import java_spring.lesson_3.api.IssueRequest;
import java_spring.lesson_3.model.Book;
import java_spring.lesson_3.model.Issue;
import java_spring.lesson_3.model.Reader;
import java_spring.lesson_3.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class IssueService {

    // spring все это заинжектит
    @Autowired
    private BookRep bookRepository;
    @Autowired
    private ReaderRep readerRepository;
    @Autowired
    private IssueRep issueRepository;

    public Issue issue(IssueRequest request) {
        Optional<Book> book = bookRepository.findById(request.getBookId());
        Optional<Reader> reader = readerRepository.findById(request.getBookId());

        if (book.isEmpty()) {
            throw new NoSuchElementException(String.format("Не найдена книга с идентификатором %d", request.getBookId()));
        }
        if (reader.isEmpty()) {
            throw new NoSuchElementException(String.format("Не найден читатель с идентификатором %d", request.getReaderId()));
        }
        // Нужно проверить, что у читателя нет книги на руках (или его лимит не превышает X книг)
        if (!book.orElseThrow().isFree()) {
            throw new ExceptionInInitializerError(String.format("Книга %d уже занята", request.getBookId()));
        }
        if (issueRepository.countActiveBooks(request.getReaderId()) > 0) {
            throw new ExceptionInInitializerError(String.format("У читателя %d уже есть книги на руках", request.getReaderId()));
        }

        Issue issue = new Issue(book.orElseThrow(), reader.orElseThrow());
        book.ifPresent(it -> it.setFree(false));
        issueRepository.save(issue);
        return issue;
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

//    public int countActiveBooks(long id) {
//        return issueRepository.findAllByReaderId(id).stream()
//                .filter(issue -> issue.getReturned_at() == null)
//                .toList().size();
//    }

}
