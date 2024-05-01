package java_spring.lesson_3.repository;

import java_spring.lesson_3.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IssueRep extends JpaRepository<Issue, Long> {
    @Query(value = "SELECT * FROM issues i WHERE i.reader_id = :id", nativeQuery = true)
    List<Issue> findAllByReaderId(long id);

    @Query(value = "SELECT * FROM issues i WHERE i.reader_id = :id AND i.returned_at IS NULL", nativeQuery = true)
    List<Issue> findActiveByReaderId(long id);

    @Query(value = "SELECT COUNT(*) FROM issues i WHERE i.reader_id = :id AND i.returned_at IS NULL", nativeQuery = true)
    int countActiveBooks(long id);
}