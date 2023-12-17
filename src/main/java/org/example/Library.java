package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.Buffer;
import java.sql.*;
import java.util.*;

import static javax.management.remote.JMXConnectorFactory.connect;

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

    public Book bookDialog(BufferedReader buff, boolean isAdd) {
        Book book = new Book();
        try {
            System.out.print("Enter book name: ");
            String bookName = null;
            do {
                line = buff.readLine();
            } while (line.length() == 0 && isAdd == true);
            if (line.length() != 0) {
                book.setName(line.toLowerCase().trim());
            }
            line = "";
            System.out.print("Enter book author: ");
            line = buff.readLine();
            if (line.length() != 0) {
                book.setAuthor(line.toLowerCase().trim());
            }
            line = "";
            System.out.print("Enter book cost: ");
            line = buff.readLine();
            if (line.length() != 0) {
                book.setCost(Float.parseFloat(line));
            }
            line = "";
            System.out.print("Enter book edition: ");
            line = buff.readLine();
            if (line.length() != 0) {
                book.setEdition(Integer.parseInt(line));
            }
            line = "";
            System.out.println("Select book category:");
            if(!isAdd)
            {
                System.out.println(0 + ". No specific category");
            }
            for (int i = 0; i < bookCategories.size(); i++) {
                System.out.println(i + 1 + ". " + bookCategories.get(i));
            }
            do {
                line = buff.readLine();
            } while (line.length() == 0 || ((isAdd == true && Integer.parseInt(line) <= 0 && Integer.parseInt(line) > 7) ||
                    (isAdd == false && Integer.parseInt(line) < 0 && Integer.parseInt(line) > 7 )));
            book.setCategory(bookCategories.get(Integer.parseInt(line)));
            book.setCode(generateCode(Integer.parseInt(line)));
            line = "";

        } catch (IOException e) {
            e.printStackTrace();
        }
        return book;
    }

    public void addBook(BufferedReader buff) {
        try {
            System.out.println("Enter the details of book/books, you want to find:- ");
            Book book = bookDialog(buff, true);
            PreparedStatement pStmt = con.prepareStatement("insert into books values(?,?,?,?,?,?)");
            pStmt.setString(1, book.getCode());
            pStmt.setString(2, book.getName());
            pStmt.setString(3, book.getAuthor());
            pStmt.setFloat(4, book.getCost());
            pStmt.setInt(5, book.getEdition());
            pStmt.setInt(6, getCategoryNumber(book.getCode()));
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

    public void findBook(BufferedReader buff, ArrayList<Book> books) {
        Book book = bookDialog(buff, false);
        ResultSet rst;
        try{

            String sql ="Select * from books " +
                    "where " +
                    "(? = 0 and ((name=?) or" +
                    " (? is null and (author = ? or (? is null and ((? != 0 and  cost = ?) or " +
                    " (? = 0 and ((? != 0 and edition = ?) or (? = 0)))))))) or (category = ? and ((name=?) or" +
                    " (? is null and (author = ? or (? is null and ((? != 0 and  cost = ?) or (? = 0 and " +
                    " (? != 0 and edition = ?) or (? = 0)))))))))";
            PreparedStatement pStmt = con.prepareStatement(sql);
            pStmt.setInt(1, getCategoryNumber(book.getCode()));
            pStmt.setString(2, book.getName());
            pStmt.setString(3, book.getName());
            pStmt.setString(4, book.getAuthor());
            pStmt.setString(5, book.getAuthor());
            pStmt.setFloat(6, book.getCost());
            pStmt.setFloat(7, book.getCost());
            pStmt.setFloat(8, book.getCost());
            pStmt.setInt(9, book.getEdition());
            pStmt.setInt(10, book.getEdition());
            pStmt.setInt(11, book.getEdition());
            pStmt.setInt(12, getCategoryNumber(book.getCode()));
            pStmt.setString(13, book.getName());
            pStmt.setString(14, book.getName());
            pStmt.setString(15, book.getAuthor());
            pStmt.setString(16, book.getAuthor());
            pStmt.setFloat(17, book.getCost());
            pStmt.setFloat(18, book.getCost());
            pStmt.setFloat(19, book.getCost());
            pStmt.setInt(20, book.getEdition());
            pStmt.setInt(21, book.getEdition());
            pStmt.setInt(22, book.getEdition());



            rst = pStmt.executeQuery();
            while (rst.next()) {

//                System.out.println(rst.getString("name"));
                book.setCode(rst.getString("code"));
                book.setName(rst.getString("name"));
                book.setAuthor(rst.getString("author"));
                book.setCost(rst.getFloat("cost"));
                book.setEdition(rst.getInt("edition"));
                book.setCategory(bookCategories.get(rst.getInt("category") - 1));
                books.add(book);
            }
        }
        catch(SQLException s){
            s.printStackTrace();
        }
    }

    public void getBookDetails(Book book) {
        System.out.println("Book name: " + book.getName());
        if (book.getAuthor() != null) {
            System.out.println("Book author: " + book.getAuthor());
        }
        if(book.getCost() != 0){
            System.out.println("Book cost: " + book.getCost() + " Rs");
        }
        if(book.getEdition() != 0){
            System.out.println("Book edition: " + book.getEdition());
        }
        System.out.println("Book category: " + book.getCategory());
    }


}
