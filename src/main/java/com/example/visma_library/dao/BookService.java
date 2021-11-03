package com.example.visma_library.dao;

import com.example.visma_library.model.Book;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class BookService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String FILE_PATH = "C:\\Users\\tadas\\IdeaProjects\\vismalibrary\\src\\main\\resources\\books.json";

    public List<Book> getBookList() throws IOException {
        return objectMapper.readValue(new File(FILE_PATH), new TypeReference<>() {
        });
    }


    public void deleteBook(String guid) throws IOException {

        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());

        List<Book> bookListToUpdate = getBookList();

        bookListToUpdate.removeIf(book -> book.getGuid().equals(guid));

        writer.writeValue(new File(FILE_PATH), bookListToUpdate);
    }

    public void addBook(Book book) throws IOException {
        List<Book> bookListToUpdate = getBookList();

        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());

        bookListToUpdate.add(book);

        writer.writeValue(new File(FILE_PATH), bookListToUpdate);
    }

    public Book getBookByGUID(String guid) throws IOException {
        List<Book> books = getBookList();
        return books.stream()
                .filter(book -> guid.equals(book.getGuid()))
                .findAny()
                .orElse(null);
    }

    public List<Book> getBooksByFilter(String filter, String value) throws IOException {
        List<Book> books = getBookList();

        switch (filter) {
            case "author":
                return books.stream().filter(b -> b.getAuthor().equals(value)).collect(Collectors.toList());
            case "category":
                return books.stream().filter(b -> b.getCategory().equals(value)).collect(Collectors.toList());
            case "language":
                return books.stream().filter(b -> b.getLanguage().equals(value)).collect(Collectors.toList());
            case "ISBN":
                return books.stream().filter(b -> b.getIsbn().equals(value)).collect(Collectors.toList());
        }

        return books;
    }

    public String getFILE_PATH() {
        return this.FILE_PATH;
    }

}
