package com.example.visma_library;

import com.example.visma_library.controllers.BookController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
@SpringBootTest
class VismaLibraryApplicationTests {



    @Autowired
    private BookController controller;

    @Autowired
    private MockMvc mvc;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    void getBookByGuid() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books/1");
        MvcResult result = mvc.perform(requestBuilder).andReturn();
    }

}
