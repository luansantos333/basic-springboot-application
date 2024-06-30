package edu.simplespringapp.book;

import edu.simplespringapp.book.model.Book;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;


import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SimpleSpringAppTests {

    private static final String API_ROOT = "http://localhost:8080/api/books";
    private Book createARandomBook () {

        Book book = new Book();
        book.setTitle(RandomStringUtils.randomAlphabetic(10));
        book.setAuthor(RandomStringUtils.random(20) + "," + RandomStringUtils.random(10));
        book.setPrice(ThreadLocalRandom.current().nextDouble());

        return book;
    }


    private String createBookAsURI (Book book) {


        Response response = RestAssured.given().
                contentType(MediaType.APPLICATION_JSON_VALUE).
                body(book).post(API_ROOT);

      return API_ROOT + "/" +  response.jsonPath().get("id_book");


    }


    @Test
    public void whenGetAllBooks_thenOk () {

        Response response = RestAssured.get(API_ROOT);
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode());

    }

    @Test
    public void whenGetBooksByTitle_thenOk () {

        Book book = createARandomBook();
        createBookAsURI(book);
        Response response = RestAssured.get(
                API_ROOT + "/title/" + book.getTitle()
        );

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        Assertions.assertTrue(response.as(List.class).size()>0);

    }

    @Test
    public void whenGetBookByID_thenOk () {

        Book book = createARandomBook();
        String location = createBookAsURI(book);
        Response response = RestAssured.get(location);

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        Assertions.assertEquals(book.getTitle(), response.jsonPath().get("title"));


    }

    @Test
    public void whenGetNotExistBookById_thenNotFound() {
        Response response = RestAssured.get(API_ROOT + "/" + RandomStringUtils.randomNumeric(4));

        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    @Test
    void whenCreatedWithSucess_thenOk () {

        Book book = createARandomBook();

        Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE).body(book).post(API_ROOT);


        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode());


    }

    @Test
    void whenInvalidBookRequest_thenError () {

        Book book = createARandomBook();
        book.setAuthor(null);

        Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE).body(book)
                .post(API_ROOT);


        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());



    }


    @Test
    void whenUpdatingBook_thenOk () {

        Book book = createARandomBook();




    }




    @Test
    void contextLoads() {
    }
}
