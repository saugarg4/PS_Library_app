package org.example;

import java.io.*;
import java.util.ArrayList;

public class Main implements Runnable{
    InputStreamReader isr;
    BufferedReader buff;
    int selectedOperation;
    ArrayList<Book> books;
    Stock stk;

    boolean isUserContinue;
    public Main(){
        if(isr == null){
            isr = new InputStreamReader(System.in);
        }
        if(buff == null){
            buff = new BufferedReader(isr);
        }
        if(stk == null){
            stk = new Library();
        }
        if(books == null){
            books = new ArrayList<>();
        }
        isUserContinue = true;
    }

    public void getBooks(){
        books.clear();
        stk.findBook(buff, books);
        if(books.size() == 0){
            System.out.println("No match found");
        }
        else{
            for (Book book : books) {
                stk.getBookDetails(book);
                System.out.println();
            }
        }
    }
    public void getCheapestBooks(){
        books.clear();
        stk.findCheapestBook(books);
        if(books.size() == 0){
            System.out.println("Library is empty or book cost is unknown");
        }
        else{
            System.out.println("Cheapest books are:-");
            for (Book book : books) {
                stk.getBookDetails(book);
                System.out.println();
            }
        }
    }
    public void getMostCostlyBooks(){
        books.clear();
        stk.findMostCostlyBook(books);
        if(books.size() == 0){
            System.out.println("Library is empty or book cost is unknown");
        }
        else{
            System.out.println("Most Costly books are:-");
            for (Book book : books) {
                stk.getBookDetails(book);
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        Main obj = new Main();
        Thread t = new Thread(obj);
        t.start();
        
    }
    
    public void run(){
        System.out.println("Welcome to Library System");
        while(isUserContinue){
            System.out.println("Please select the actions you want to perform\n1. Add book\n2. Update Book\n3. Remove Book\n4. Find book \n5. Find cheapest books\n6. Find most costly books\n7. Quit");
            try {
                do {
                    selectedOperation = Integer.parseInt(buff.readLine());
                } while (selectedOperation < 1 && selectedOperation > 4);
                System.out.println("Selected operation: " + selectedOperation);
                switch (selectedOperation) {
                    case 1:
                        stk.addBook(buff);
                        break;
                    case 2:
                        stk.updateBook(buff);
                        break;
                    case 3:
                        stk.removeBook(buff);
                        break;
                    case 4:
                        getBooks();
                        break;
                    case 5:
                        getCheapestBooks();
                        break;
                    case 6:
                        getMostCostlyBooks();
                        break;
                    case 7:
                        isUserContinue = false;
                        System.out.println("Quiting...");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        System.out.println("Thank you for using Library System!");
    }
}