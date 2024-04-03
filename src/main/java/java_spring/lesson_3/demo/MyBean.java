//package java_spring.lesson_3.demo;
//
//import jakarta.annotation.PostConstruct;
//import jakarta.annotation.PreDestroy;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//public class MyBean {
//
//    public MyBean(MyService myService) {
//        log.info("constructor");
//    }
//
//    @Autowired
//    public void setMyService(MyService myService) {
//        log.info("setter-injection");
//    }
//
//    @PostConstruct
//    public void postConstruct() {
//        log.info("postConstruct");
//    }
//
//    @EventListener(DatabaseConnectionSetupEvent.class)
//    public void onDatabaseConnectionSetup() {
//        log.info("onDatabaseConnectionSetup");
//    }
//
//    @PreDestroy
//    public void preDestroy() {
//        log.info("preDestroy");
//    }
//}
