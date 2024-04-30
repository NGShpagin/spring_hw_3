package java_spring.lesson_3.repository;

import java_spring.lesson_3.model.Issue;
import java_spring.lesson_3.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReaderRep extends JpaRepository<Reader, Long> {
}
