package java_spring.lesson_3.model;

import java_spring.lesson_3.repository.BookRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * Запись о факте выдачи книги
 */
@Data
public class Issue {
    public static long sequence = 1L;

    private final long id;
    private final long bookId;
    private final long readerId;

    /**
     * Дата выдачи
     */
    private final LocalDateTime issued_at;
    private LocalDateTime returned_at;

    public Issue(long bookId, long readerId) {
        this.id = sequence++;
        this.bookId = bookId;
        this.readerId = readerId;
        this.issued_at = LocalDateTime.now();
        this.returned_at = null;
    }
}
