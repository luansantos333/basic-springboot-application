package edu.simplespringapp.book.repository;

import edu.simplespringapp.book.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface BookRepo extends CrudRepository <Book, Long> {

    List<Book> findByTitle(String title);

}
