package org.example;

import java.io.BufferedReader;
import java.util.ArrayList;

public interface Stock {
    public void addBook(BufferedReader buff);
    public void updateBook(BufferedReader buff);
    public void removeBook(BufferedReader buff);
    public void findBook(BufferedReader buff, ArrayList<Book> books);
    public void getBookDetails(Book book);
}
