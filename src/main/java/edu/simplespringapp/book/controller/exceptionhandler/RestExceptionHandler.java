package edu.simplespringapp.book.controller.exceptionhandler;

import edu.simplespringapp.book.controller.BookIdMismatchException;
import edu.simplespringapp.book.controller.BookNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler ({BookNotFoundException.class})
    protected ResponseEntity <Object> handleNotFound (Exception ex, WebRequest request) {


      return  handleExceptionInternal(ex, "Book not found", new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }


    @ExceptionHandler ({BookIdMismatchException.class})
    protected ResponseEntity<Object> handleMismatch (Exception e, WebRequest request) {


        return handleExceptionInternal(e, "Book Mismatch", new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE,
                request);

    }


}


