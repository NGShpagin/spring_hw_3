package java_spring.lesson_3.api;

import lombok.Data;

/**
 * Запрос на выдачу книг
 */
@Data
public class IssueRequest {

    /**
     * Идентификатор читателя
     */
    private Long readerId;

    /**
     * Идентификатор книги
     */
    private Long bookId;
}
