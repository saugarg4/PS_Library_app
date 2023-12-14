package org.example;

import java.io.*;
import java.util.ArrayList;

public class Main {
    InputStreamReader isr;
    BufferedReader buff;
    int selectedOperation;
    ArrayList<Book> books;
    Stock stk;
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
    }

    public void getBooks(){
        for(Book book : books){
           stk.getBookDetails(book);
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Main obj = new Main();
        System.out.println("Welcome to Library System");
        System.out.println("Please select the actions you want to perform\n1. Add book\n2. Update Book\n3. Remove Book\n4. Find book");
        try{
            do{
                obj.selectedOperation = Integer.parseInt(obj.buff.readLine());
            }while(obj.selectedOperation <1 && obj.selectedOperation  > 4);
            System.out.println("Selected operation: " + obj.selectedOperation);
            switch(obj.selectedOperation){
                case 1: obj.stk.addBook(obj.buff); break;
                case 2: obj.stk.updateBook(obj.buff); break;
                case 3: obj.stk.removeBook(obj.buff); break;
                case 4:
                    if(obj.books.size() > 0){
                        obj.books.clear();
                    }
                    obj.books =  obj.stk.findBook(obj.buff);
                        obj.getBooks(); break;
            }
        }catch(IOException e){
         e.printStackTrace();
        }
    }
}