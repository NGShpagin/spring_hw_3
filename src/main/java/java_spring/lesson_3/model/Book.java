package java_spring.lesson_3.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@RequiredArgsConstructor
public class Book {

    public static long sequence = 1L;

    private final Long id;
    private final String name;
    private boolean isFree = true;

    public Book(String name) {
        this(sequence++, name);
    }
}
