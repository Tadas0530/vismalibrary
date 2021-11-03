package com.example.visma_library;

import com.example.visma_library.controllers.BookController;
import com.example.visma_library.dao.BookService;
import com.example.visma_library.model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = VismaLibraryApplication.class)
@WebMvcTest(BookController.class)
class VismaLibraryApplicationTests {


    @MockBean
    private BookController controller;

    @MockBean
    private BookService bookService;

    @Autowired
    private MockMvc mvc;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    void testGetBook() throws Exception {
        Book book = new Book("4");
        Mockito.when(bookService.getBookByGUID("4")).thenReturn(book);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books/4", 4);

        ResultMatcher expectedStatus = MockMvcResultMatchers.status().is(HttpStatus.OK.value()); // status = 200

        mvc.perform(requestBuilder).andExpect(expectedStatus);
    }

    @Test
    void testGetBookByGUID() throws IOException {
        Mockito.when(bookService.getBookByGUID("4")).thenReturn(new Book("Python programming", "Arnas",
                "Programming", "Lithuanian", Date.valueOf("2020-08-23T00:00:00.000+00:00"),
                "0264926", "4"));

        Book book = bookService.getBookByGUID("4");

        Assertions.assertEquals("4", book.getGuid());
    }

    @Test
    void testLanguageFilter() throws IOException {

        List<Book> books = bookService.getBooksByFilter("author", "Someone");

        // testing size

        Assertions.assertEquals(3, books.size());
    }
}
