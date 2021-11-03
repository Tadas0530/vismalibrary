package com.example.visma_library.controllers;

import com.example.visma_library.dao.BookService;
import com.example.visma_library.dao.UserService;
import com.example.visma_library.model.Book;

import com.example.visma_library.model.User;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookController {

    @Autowired
    private ObjectMapper objectMapper;

    private final BookService bookService = new BookService();

    private final UserService userService = new UserService();


    @PostMapping(path = "/books")
    public ResponseEntity<Book> postBook(@RequestBody Book book) {
        try {
            List<Book> books = bookService.getBookList();
            ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
            books.add(book);
            writer.writeValue(Paths.get(bookService.getFILE_PATH()).toFile(), books);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(book);
    }

    @GetMapping(path = "/books/filter/{filter}/{value}")
    public ResponseEntity<List<Book>> getBooksByFilter(@PathVariable("filter") String filter, @PathVariable("value") String value) throws IOException {

        return ResponseEntity.ok().body(bookService.getBooksByFilter(filter, value));
    }

    @GetMapping(path = "/books/{guid}")
    public ResponseEntity<Book> getBookByGuid(@PathVariable("guid") String guid) throws IOException {
        Book foundBook = bookService.getBookByGUID(guid);

        if(foundBook != null) {
            return ResponseEntity.ok().body(foundBook);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/take-book/{guid}/{username}/{takenDays}")
    public ResponseEntity<User> takeABook(@PathVariable("guid") String guid,
                                          @PathVariable("username") String username,
                                          @PathVariable("takenDays") Integer days) throws IOException {
        // get user who takes the book

        User userWhoTakesTheBook = userService.getUserByName(username);

        // get book

        Book takenBook = bookService.getBookByGUID(guid);

        // checking conditions and updating if conditions meet

        if (((userWhoTakesTheBook.getBooksTaken() < 3) && (days < 62))) {

            // updating user

            userWhoTakesTheBook.setBooksTaken(userWhoTakesTheBook.getBooksTaken() + 1);
            userService.deleteUser(username);
            userService.addUser(userWhoTakesTheBook);

            // updating book

            bookService.deleteBook(guid);
            bookService.addBook(takenBook);

            return ResponseEntity.status(HttpStatus.OK).body(userWhoTakesTheBook);
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(userWhoTakesTheBook);
    }

    @DeleteMapping(path = "/books/{guid}")
    public void deleteBookByGuid(@PathVariable("guid") String guid) throws IOException {
        List<Book> books = bookService.getBookList();
        books.remove(books.stream()
                .filter(book -> guid.equals(book.getGuid()))
                .findAny()
                .orElse(null));
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(Paths.get(bookService.getFILE_PATH()).toFile(), books);
    }
}