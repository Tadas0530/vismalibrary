package com.example.visma_library.model;

import java.util.Date;

public class Book {

    private String name;
    private String author;
    private String category;
    private String language;
    private Date publicationDate;
    private String isbn;
    private String guid;

    public Book() {
    }

    public Book(String guid) {

        this.guid = guid;
    }

    public Book(String name, String author, String category, String language, Date publicationDate, String isbn, String guid) {
        this.name = name;
        this.author = author;
        this.category = category;
        this.language = language;
        this.publicationDate = publicationDate;
        this.isbn = isbn;
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", language='" + language + '\'' +
                ", publicationDate=" + publicationDate +
                ", isbn='" + isbn + '\'' +
                ", guid='" + guid + '\'' +
                '}';
    }
}
