package java_spring.lesson_3.model;

import jakarta.persistence.*;
import java_spring.lesson_3.repository.BookRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Запись о факте выдачи книги
 */
@Entity
@Table(name = "issues")
@Data
@NoArgsConstructor
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "booK_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "reader_id")
    private Reader reader;

    /**
     * Дата выдачи
     */
    private LocalDateTime issued_at;
    private LocalDateTime returned_at;

    public Issue(Book book, Reader reader) {
        this.book = book;
        this.reader = reader;
        this.issued_at = LocalDateTime.now();
        this.returned_at = null;
    }
}
