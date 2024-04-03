package java_spring.lesson_3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Lesson3Application {

	/*
		Tomcat - контейнер сервелетов (веб-сервер)

		/ student
		spring-student.war -> tomcat
		/ api
		spring-api.war -> tomcat

		spring-web.jar

		Слои приложения:
		1. controllers (api)
		2. сервисный слой (services)
		3. репозитории (repositories, dao - data access objects)

		Сервис, отвечающий за выдачу книг в библиотеке.
		Нужно напрограммировать ручку, которая либо выдает книгу читателю, либо отказывает в выдаче

		// book - книга
		// GET /book/{id}
		// reader - читатель
		// issue - информация о выдаче книг
		// POST /issue {readerId: 24, bookId: 12}
	 */

	public static void main(String[] args) {
		SpringApplication.run(Lesson3Application.class, args);
	}

}
