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
            if (!isAdd) {
                System.out.println(0 + ". No specific category");
            }
            for (int i = 0; i < bookCategories.size(); i++) {
                System.out.println(i + 1 + ". " + bookCategories.get(i));
            }
            int categoryNumber = 0;
            do {
                line = buff.readLine();
                categoryNumber = Integer.parseInt(line);
            } while (line.length() == 0 || ((isAdd == true && categoryNumber <= 0 && categoryNumber > 7) ||
                    (isAdd == false && categoryNumber < 0 && categoryNumber > 7)));
            if (categoryNumber > 0)
                book.setCategory(bookCategories.get(categoryNumber - 1));
            book.setCode(generateCode(Integer.parseInt(line)));
            line = "";

        } catch (IOException e) {
            e.printStackTrace();
        }
        return book;
    }

    public void addBook(BufferedReader buff) {
        try {
            System.out.println("Enter the details of book/books, you want to add:- ");
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
        System.out.println("Enter the details of book/books, you want to update:- ");
        Book book1 = bookDialog(buff, false);
        System.out.println("Enter the new details of the updated book/books :- ");
        Book book2 = bookDialog(buff, false);
        try {
            String sql = "UPDATE books " +
                    "SET " +
                    "    name = " +
                    "        CASE " +
                    "            WHEN ? is null THEN name " +
                    "            ELSE ? " +
                    "        END, " +
                    "    author = " +
                    "        CASE " +
                    "            WHEN ? is null THEN author " +
                    "            ELSE ? " +
                    "        END, " +
                    "    cost = " +
                    "        CASE " +
                    "            WHEN ? != 0 THEN ? " +
                    "            ELSE cost " +
                    "        END, " +
                    " edition = " +
                    "        CASE " +
                    "            WHEN ? != 0 THEN ? " +
                    "            ELSE edition " +
                    "        END, " +
                    " category = " +
                    "        CASE " +
                    "            WHEN ? != 0 THEN ? " +
                    "            ELSE category " +
                    "        END        " +
                    "where " +
                    "(? = 0 or category = ?) and " +
                    "(? is null or name = ?) and " +
                    "(? is null or author = ? ) and " +
                    "(? = 0 or cost = ?) and " +
                    "(? = 0 or edition = ?)";

            PreparedStatement pStmt = con.prepareStatement(sql);
            pStmt.setString(1, book2.getName());
            pStmt.setString(2, book2.getName());
            pStmt.setString(3, book2.getAuthor());
            pStmt.setString(4, book2.getAuthor());
            pStmt.setFloat(5, book2.getCost());
            pStmt.setFloat(6, book2.getCost());
            pStmt.setInt(7, book2.getEdition());
            pStmt.setInt(8, book2.getEdition());
            pStmt.setInt(9, getCategoryNumber(book2.getCode()));
            pStmt.setInt(10, getCategoryNumber(book2.getCode()));
            pStmt.setInt(11, getCategoryNumber(book1.getCode()));
            pStmt.setInt(12, getCategoryNumber(book1.getCode()));
            pStmt.setString(13, book1.getName());
            pStmt.setString(14, book1.getName());
            pStmt.setString(15, book1.getAuthor());
            pStmt.setString(16, book1.getAuthor());
            pStmt.setFloat(17, book1.getCost());
            pStmt.setFloat(18, book1.getCost());
            pStmt.setInt(19, book1.getEdition());
            pStmt.setInt(20, book1.getEdition());
            System.out.println("Rows Affected " + pStmt.executeUpdate());
        } catch (SQLException s) {
            s.printStackTrace();
        }


    }

    public void removeBook(BufferedReader buff) {
        System.out.println("Enter the details of book/books, you want to remove:- ");
        Book book = bookDialog(buff, false);
        ResultSet rst;
        try {

            String sql = "Delete from books " +
                    "where " +
                    "(? = 0 or category = ?) and " +
                    "(? is null or name = ?) and " +
                    "(? is null or author = ? ) and " +
                    "(? = 0 or cost = ?) and " +
                    "(? = 0 or edition = ?) " +
                    "and (? is not null or ? is not null or ? != 0  or ? != 0 or ? != 0) ";
            PreparedStatement pStmt = con.prepareStatement(sql);
            pStmt.setInt(1, getCategoryNumber(book.getCode()));
            pStmt.setInt(2, getCategoryNumber(book.getCode()));
            pStmt.setString(3, book.getName());
            pStmt.setString(4, book.getName());
            pStmt.setString(5, book.getAuthor());
            pStmt.setString(6, book.getAuthor());
            pStmt.setFloat(7, book.getCost());
            pStmt.setFloat(8, book.getCost());
            pStmt.setInt(9, book.getEdition());
            pStmt.setInt(10, book.getEdition());
            pStmt.setString(11, book.getName());
            pStmt.setString(12, book.getAuthor());
            pStmt.setFloat(13, book.getCost());
            pStmt.setInt(14, book.getEdition());
            pStmt.setInt(15, getCategoryNumber(book.getCode()));
            System.out.println("Rows Affected " + pStmt.executeUpdate());


        } catch (SQLException s) {
            s.printStackTrace();
        }

    }

    public void findCheapestBook(ArrayList<Book> books) {
        try {

            String sql = "Select * from books where cost = (" +
                    "  select min(cost) from books where cost != 0);";
            ResultSet rst;
            PreparedStatement pStmt = con.prepareStatement(sql);


            rst = pStmt.executeQuery();
            while (rst.next()) {
                Book book = new Book();
                book.setCode(rst.getString("code"));
                book.setName(rst.getString("name"));
                book.setAuthor(rst.getString("author"));
                book.setCost(rst.getFloat("cost"));
                book.setEdition(rst.getInt("edition"));
                book.setCategory(bookCategories.get(rst.getInt("category") - 1));
                books.add(book);
            }
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

    public void findMostCostlyBook(ArrayList<Book> books) {
        try {

            String sql = "Select * from books where cost = (" +
                    "  select max(cost) from books where cost != 0);";
            ResultSet rst;
            PreparedStatement pStmt = con.prepareStatement(sql);


            rst = pStmt.executeQuery();
            while (rst.next()) {
                Book book = new Book();
                book.setCode(rst.getString("code"));
                book.setName(rst.getString("name"));
                book.setAuthor(rst.getString("author"));
                book.setCost(rst.getFloat("cost"));
                book.setEdition(rst.getInt("edition"));
                book.setCategory(bookCategories.get(rst.getInt("category") - 1));
                books.add(book);
            }
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

    public void findBook(BufferedReader buff, ArrayList<Book> books) {
        System.out.println("Enter the details of book/books, you want to find:- ");
        Book book = bookDialog(buff, false);
        ResultSet rst;
        try {

            String sql = "Select * from books " +
                    "where " +
                    "(? = 0 or category = ?) and " +
                    "(? is null or name = ?) and " +
                    "(? is null or author = ? ) and " +
                    "(? = 0 or cost = ?) and " +
                    "(? = 0 or edition = ?)";
            PreparedStatement pStmt = con.prepareStatement(sql);
            pStmt.setInt(1, getCategoryNumber(book.getCode()));
            pStmt.setInt(2, getCategoryNumber(book.getCode()));
            pStmt.setString(3, book.getName());
            pStmt.setString(4, book.getName());
            pStmt.setString(5, book.getAuthor());
            pStmt.setString(6, book.getAuthor());
            pStmt.setFloat(7, book.getCost());
            pStmt.setFloat(8, book.getCost());
            pStmt.setInt(9, book.getEdition());
            pStmt.setInt(10, book.getEdition());

            rst = pStmt.executeQuery();
            while (rst.next()) {
                book = new Book();
                book.setCode(rst.getString("code"));
                book.setName(rst.getString("name"));
                book.setAuthor(rst.getString("author"));
                book.setCost(rst.getFloat("cost"));
                book.setEdition(rst.getInt("edition"));
                book.setCategory(bookCategories.get(rst.getInt("category") - 1));
                books.add(book);
            }
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

    public void findBooksInGivenCostRange(BufferedReader buff, ArrayList<Book> books) {

        float startAmount = 0, endAmount = 0;
        try {
            System.out.println("Enter the starting cost of books:- ");
            startAmount = Float.parseFloat(buff.readLine());

            System.out.println("Enter the ending cost range of books:- ");
            endAmount = Float.parseFloat(buff.readLine());



        } catch (IOException e) {
            e.printStackTrace();
        }
        ResultSet rst;
        startAmount = Math.min(startAmount, endAmount);
        endAmount = Math.max(startAmount, endAmount);
        try {

            String sql = "Select * from books where cost >= ? and cost <= ? and cost != 0";
            PreparedStatement pStmt = con.prepareStatement(sql);
            pStmt.setFloat(1, startAmount);
            pStmt.setFloat(2, endAmount);


            rst = pStmt.executeQuery();
            Book book = null;
            while (rst.next()) {
                book = new Book();
                book.setCode(rst.getString("code"));
                book.setName(rst.getString("name"));
                book.setAuthor(rst.getString("author"));
                book.setCost(rst.getFloat("cost"));
                book.setEdition(rst.getInt("edition"));
                book.setCategory(bookCategories.get(rst.getInt("category") - 1));
                books.add(book);
            }
        } catch (SQLException s) {
            s.printStackTrace();
        }

    }

    public void remove2YrsOrMoreOldBooks()
    {
        try{
            String sql = "DELETE FROM books WHERE  edition != 0 and edition <= EXTRACT(YEAR FROM CURRENT_DATE) - 2;";

                PreparedStatement pStmt = con.prepareStatement(sql);
                System.out.println("Rows Affected " + pStmt.executeUpdate());

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
        if (book.getCost() != 0) {
            System.out.println("Book cost: " + book.getCost() + " Rs");
        }
        if (book.getEdition() != 0) {
            System.out.println("Book edition: " + book.getEdition());
        }
        System.out.println("Book category: " + book.getCategory());
    }


}
