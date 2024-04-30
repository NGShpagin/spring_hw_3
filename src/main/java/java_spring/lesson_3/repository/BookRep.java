package java_spring.lesson_3.repository;

import java_spring.lesson_3.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRep extends JpaRepository<Book, Long> {
}
