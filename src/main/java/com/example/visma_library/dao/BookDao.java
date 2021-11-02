package com.example.visma_library.dao;

import com.example.visma_library.model.Book;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class BookDao {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String FILE_PATH = "C:\\Users\\Tadas\\IdeaProjects\\visma_library\\src\\main\\resources\\books.json";

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

    public String getFILE_PATH() {
        return this.FILE_PATH;
    }
}
