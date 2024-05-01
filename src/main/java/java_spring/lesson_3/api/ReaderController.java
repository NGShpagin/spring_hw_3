package java_spring.lesson_3.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java_spring.lesson_3.model.Issue;
import java_spring.lesson_3.model.Reader;
import java_spring.lesson_3.repository.BookRep;
import java_spring.lesson_3.repository.IssueRep;
import java_spring.lesson_3.repository.ReaderRep;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/readers")
@Tag(name = "Reader")
public class ReaderController {

    @Autowired
    ReaderRep readerRepository;
    @Autowired
    IssueRep issueRepository;

    /**
     * Получить всех читателей
     *
     * @return список читателей
     */
    @GetMapping
    @Operation(summary = "Get all readers", description = "Получить всех читателей")
    public ResponseEntity<List<Reader>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(readerRepository.findAll());
    }

    /**
     * Получить читателя по его идентификатору
     *
     * @param id - идентификатор читателя
     * @return полученный читатель
     */
    @GetMapping(path = "/{id}")
    @Operation(summary = "Get reader by Id", description = "Получить читателя по Id")
    public ResponseEntity<Object> getReaderById(@PathVariable long id) {
        Optional<Reader> reader = readerRepository.findById(id);
        if (reader.isPresent()) return ResponseEntity.status(HttpStatus.FOUND).body(reader);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    /**
     * Добавить нового читателя
     *
     * @param reader - добавляемый читатель: name
     * @return добавленный читатель
     */
    @PostMapping
    @Operation(summary = "Add new reader", description = "Добавить нового читателя")
    public ResponseEntity<Object> createReader(@RequestBody Reader reader) {
        return ResponseEntity.status(HttpStatus.CREATED).body(readerRepository.save(reader));
    }

    /**
     * Удалить читателя по его идентификатору
     *
     * @param id - идентификатор удаляемого читателя
     * @return удаленный читатель
     */
    @DeleteMapping(path = "{id}")
    @Operation(summary = "Delete reader by Id", description = "Удалить читателя по Id")
    public ResponseEntity<Object> deleteReaderById(@PathVariable long id) {
        Optional<Reader> reader = readerRepository.findById(id);
        if (reader.isPresent()) {
            readerRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(reader);
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    /**
     * Получить все записи читателя
     *
     * @param id - идентификатор читателя
     * @return список записей читателя
     */
    @GetMapping(path = "/{id}/issues")
    @Operation(summary = "Get all reader issues by Id", description = "Получить все записи читателя по его Id")
    public ResponseEntity<List<Issue>> getReaderAllIssues(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(issueRepository.findAllByReaderId(id));
    }

    /**
     * Получить только те записи, по которым не сданы книги
     *
     * @param id - идентификатор читателя
     * @return список записей, по которым не сданы книги
     */
    @GetMapping(path = "/{id}/issues/active")
    @Operation(summary = "Get not closed issues", description = "Получить не закрытые записи")
    public ResponseEntity<List<Issue>> getReaderActiveIssues(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(issueRepository.findActiveByReaderId(id));
    }

//
//    @GetMapping(path = "/ui/all")
//    public String readers(Model model) {
//        List<Reader> readers = readerRepository.findAll();
//        model.addAttribute("readers", readers);
//        return "readers";
//    }
}
