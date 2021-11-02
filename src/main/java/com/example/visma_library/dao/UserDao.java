package com.example.visma_library.dao;

import com.example.visma_library.model.Book;
import com.example.visma_library.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class UserDao {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String FILE_PATH = "C:\\Users\\Tadas\\IdeaProjects\\visma_library\\src\\main\\resources\\users.json";

    public List<User> getUserList() throws IOException {
        return objectMapper.readValue(new File(FILE_PATH), new TypeReference<>() {
        });
    }

    public String getFILE_PATH() {
        return FILE_PATH;
    }

    public User getUserByName(String name) throws IOException {
        List<User> users = getUserList();
        return users.stream()
                .filter(user -> name.equals(user.getName()))
                .findAny()
                .orElse(null);
    }

    public void deleteUser(String username) throws IOException {

        List<User> userListToUpdate = getUserList();

        userListToUpdate.removeIf(user -> user.getName().equals(username));

        objectMapper.writeValue(new File(FILE_PATH), userListToUpdate);
    }

    public void addUser(User user) throws IOException {
        List<User> userListToUpdate = getUserList();

        userListToUpdate.add(user);

        objectMapper.writeValue(new File(FILE_PATH), userListToUpdate);
    }
}
