package com.example.visma_library.controllers;

import com.example.visma_library.dao.BookDao;
import com.example.visma_library.dao.UserDao;
import com.example.visma_library.model.Book;

import com.example.visma_library.model.User;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.DataInput;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookController {

    @Autowired
    private ObjectMapper objectMapper;

    private final BookDao bookDao = new BookDao();

    private final UserDao userDao = new UserDao();


    @PostMapping(path = "/books")
    public ResponseEntity<Book> postBook(@RequestBody Book book) {
        try {
            List<Book> books = bookDao.getBookList();
            ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
            books.add(book);
            writer.writeValue(Paths.get(bookDao.getFILE_PATH()).toFile(), books);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(book);
    }

    @GetMapping(path = "/books/{filter}/{value}")
    public List<Book> getBooksByFilter(@PathVariable("filter") String filter, @PathVariable("value") String value) throws IOException {

        List<Book> books = bookDao.getBookList();

        switch (filter) {
            case "author":
                return books.stream().filter(b -> b.getAuthor().equals(value)).collect(Collectors.toList());
            case "category":
                return books.stream().filter(b -> b.getCategory().equals(value)).collect(Collectors.toList());
            case "language":
                return books.stream().filter(b -> b.getLanguage().equals(value)).collect(Collectors.toList());
            case "ISBN":
                return books.stream().filter(b -> b.getIsbn().equals(value)).collect(Collectors.toList());
            case "availability":
                return books.stream().filter(b -> !b.isTaken()).collect(Collectors.toList());
        }

        return bookDao.getBookList();
    }

    @GetMapping(path = "/books/{guid}")
    public Book getBookByGuid(@PathVariable("guid") String guid) throws IOException {
        List<Book> books = bookDao.getBookList();
        return books.stream()
                .filter(book -> guid.equals(book.getGuid()))
                .findAny()
                .orElse(null);
    }

    @GetMapping(path = "/take-book/{guid}/{username}/{date}")
    public ResponseEntity<User> takeABook(@PathVariable("guid") String guid,
                                          @PathVariable("username") String username,
                                          @PathVariable("date") String days) throws IOException {
        // get user who takes the book

        User userWhoTakesTheBook = userDao.getUserByName(username);

        // get book

        Book takenBook = getBookByGuid(guid);

        // get Date

        // checking conditions and updating if conditions meet

        if ((userWhoTakesTheBook.getBooksTaken() < 3)
                &&
                (Integer.parseInt(days) < 62) && !takenBook.isTaken()) {

            // updating user

            userWhoTakesTheBook.setBooksTaken(userWhoTakesTheBook.getBooksTaken() + 1);
            userDao.deleteUser(username);
            userDao.addUser(userWhoTakesTheBook);

            // updating book

            takenBook.setTaken(true);
            bookDao.deleteBook(guid);
            bookDao.addBook(takenBook);

            return ResponseEntity.status(HttpStatus.OK).body(userWhoTakesTheBook);
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(userWhoTakesTheBook);
    }

    @DeleteMapping(path = "/books/{guid}")
    public void deleteBookByGuid(@PathVariable("guid") String guid) throws IOException {
        List<Book> books = bookDao.getBookList();
        books.remove(books.stream()
                .filter(book -> guid.equals(book.getGuid()))
                .findAny()
                .orElse(null));
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(Paths.get(bookDao.getFILE_PATH()).toFile(), books);
    }


}