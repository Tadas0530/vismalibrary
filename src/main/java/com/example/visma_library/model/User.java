package com.example.visma_library.model;

public class User {

    private String name;
    private int booksTaken;

    public User(String name, int booksTaken) {
        this.name = name;
        this.booksTaken = booksTaken;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBooksTaken() {
        return booksTaken;
    }

    public void setBooksTaken(int booksTaken) {
        this.booksTaken = booksTaken;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", booksTaken=" + booksTaken +
                '}';
    }
}
