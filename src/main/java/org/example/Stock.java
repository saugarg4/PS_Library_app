package org.example;

import java.io.BufferedReader;
import java.util.ArrayList;

public interface Stock {
    public void addBook(BufferedReader buff);
    public void updateBook(BufferedReader buff);
    public void removeBook(BufferedReader buff);
    public ArrayList<Book> findBook(BufferedReader buff);
    public void getBookDetails(Book book);
}
