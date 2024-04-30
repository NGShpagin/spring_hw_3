package java_spring.lesson_3.repository;

import java_spring.lesson_3.model.Issue;
import java_spring.lesson_3.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IssueRep extends JpaRepository<Issue, Long> {
//    @Query("SELECT i FROM issues i WHERE i.reader_id = :id")
//    List<Issue> findAllByReaderId(Long id);
}