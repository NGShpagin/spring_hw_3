//package java_spring.lesson_3.demo;
//
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//
//import java.time.Duration;
//
//@Slf4j
//@Component
//public class DatabaseConnector {
//
//    private final ApplicationEventPublisher eventPublisher;
//
//    @Autowired
//    public DatabaseConnector(ApplicationEventPublisher eventPublisher) {
//        this.eventPublisher = eventPublisher;
//    }
//
//    public String getData() {
//        return "data";
//    }
//
//    @SneakyThrows
//    @EventListener(ApplicationReadyEvent.class)
//    public void init() {
//        log.info("Подключаемся к БД...");
//        Thread.sleep(Duration.ofSeconds(1));
//        log.info("Подключение к БД успешно");
//
//        eventPublisher.publishEvent(new DatabaseConnectionSetupEvent(this));
//    }
//}
