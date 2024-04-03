package java_spring.lesson_3.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Reader {

    public static long sequence = 1L;

    private final Long id;
    private final String name;

    public Reader(String name) {
        this(sequence++, name);
    }
}

