package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.Buffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.*;

public class Library implements Stock {

    List<Map<String, Book>> bookRecord;
    List<String> bookCategories;

    String line;
    Connection con;

    public Library() {
        bookRecord = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            bookRecord.add(new HashMap<>());
        }
        bookCategories = new ArrayList<>();
        bookCategories.add("Adventure");
        bookCategories.add("Science Fiction");
        bookCategories.add("Thriller");
        bookCategories.add("Horror");
        bookCategories.add("Comic");
        bookCategories.add("Fantasy");
        bookCategories.add("Mystery");

        line = "";

        // Establishing connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (con == null)
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_db", "root", "saurabh_garg");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private String generateCode(int bookCategory) {
        Random random = new Random();
        int lowerBound = (int) Math.pow(10, 9);
        int upperBound = (int) Math.pow(10, 10) - 1;

        int randomNumber = lowerBound + random.nextInt() % (upperBound - lowerBound + 1);
        return bookCategory + "-" + randomNumber;
    }

    private int getCategoryNumber(String bookCode) {
        String[] tokens = bookCode.split("-");
        return Integer.parseInt(tokens[0]);
    }
    public Book bookDialog(BufferedReader buff)
    {
        Book book = new Book();
        try{
            System.out.print("Enter book name: ");
            String bookName = null;
            do {
                line = buff.readLine();
            } while (line.length() == 0);
            book.setName(line.toLowerCase().trim());
            line = "";
            System.out.print("Enter book author: ");
            line = buff.readLine();
            if(line.length() != 0)
            {
                book.setAuthor(line.toLowerCase().trim());
            }
            line = "";
            System.out.print("Enter book cost: ");
            line = buff.readLine();
            if(line.length() != 0)
            {
                book.setCost(Float.parseFloat(line));
            }
            line = "";
            System.out.print("Enter book edition: ");
            line = buff.readLine();
            if(line.length() != 0)
            {
                book.setEdition(Integer.parseInt(line));
            }
            line = "";
            System.out.println("Select book category:");
            for (int i = 0; i < bookCategories.size(); i++) {
                System.out.println(i + 1 + ". " + bookCategories.get(i));
            }
            do {
                line = buff.readLine();
            } while (line.length() == 0 || (Integer.parseInt(line) <= 0 && Integer.parseInt(line) > 7));
            book.setCategory(bookCategories.get(Integer.parseInt(line)));
            book.setCode(generateCode(Integer.parseInt(line)));
            line = "";

        }
        catch(IOException e){
            e.printStackTrace();
        }
        return book;
    }

    public void addBook(BufferedReader buff) {
            try {
                Book book = bookDialog(buff);
                PreparedStatement pStmt = con.prepareStatement("insert into books values(?,?,?,?,?,?)");
                pStmt.setString(1,book.getCode());
                pStmt.setString(2,book.getName());
                pStmt.setString(3,book.getAuthor());
                pStmt.setFloat(4,book.getCost());
                pStmt.setInt(5, book.getEdition());
                pStmt.setInt(6,getCategoryNumber(book.getCode()));
                pStmt.execute();

            } catch (Exception e) {
                System.out.println("Book is not added");
                e.printStackTrace();
            }
            System.out.println("Book is successfully added. :)");
    }

    public void updateBook(BufferedReader buff) {

    }

    public void removeBook(BufferedReader buff) {

    }

    public ArrayList<Book> findBook(BufferedReader buff) {
        return null;
    }

    public void getBookDetails(Book book) {

    }


}
