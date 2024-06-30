package edu.simplespringapp.book.controller;

import edu.simplespringapp.book.model.Book;
import edu.simplespringapp.book.repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping ("/api/books")
public class BookController {

    private final BookRepo bookRepo;
    @Autowired
    public BookController(BookRepo bookRepo) {

        this.bookRepo = bookRepo;

    }

    @GetMapping
    public ResponseEntity <Iterable<Book>> findAllBooks () {

        return ResponseEntity.ok(bookRepo.findAll());

    }

    @GetMapping ("/{id}")
    public ResponseEntity<Book> findBookById (@PathVariable ("id") long id) {

        return ResponseEntity.ok(bookRepo.findById(id).orElseThrow(NoSuchElementException::new));

    }

    @GetMapping ("/title/{title}")
    public ResponseEntity<List<Book>> findBookByTitle (@PathVariable("title") String title) {

        return ResponseEntity.ok(bookRepo.findByTitle(title));

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Book> addNewBook (@RequestBody Book book) {

        return ResponseEntity.ok(bookRepo.save(book));

    }

    @DeleteMapping ("/{id}")
    public void deleteBook (@PathVariable("id") long id) {

        bookRepo.findById(id).orElseThrow(NoSuchElementException::new);

        bookRepo.deleteById(id);

    }


    @PutMapping ("/{id}")
    public ResponseEntity<Book> updateBook (@RequestBody Book book, Long id) throws BookIdMismatchException {


        if (book.getId_book() != id) {
            throw new BookIdMismatchException();
        }

        bookRepo.findById(id).orElseThrow(NoSuchElementException::new);
        return ResponseEntity.ok(bookRepo.save(book));


    }




}
