package org.example;

public class Book{
    private String code;
    private String name;
    private String author;
    private float cost;
    private int edition;
    private String category;

    public Book(){

    }

    public Book(String name,String category, String code){
        this.name = name;
        this.category = category;
        this.code = code;
    }
    public Book(String name, String author, String category, String code){
        this.name = name;
        this.author = author;
        this.category = category;
        this.code = code;
    }
    public Book(String name, float cost, String category, String code){
        this.name = name;
        this.cost = cost;
        this.category = category;
        this.code = code;
    }
    public Book(String name, int edition,String category, String code){
        this.name = name;
        this.edition = edition;
        this.category = category;
        this.code = code;
    }

    public Book(String name, String author,float cost, String category, String code){
        this.name = name;
        this.cost = cost;
        this.category = category;
        this.code = code;
        this.author = author;
    }
    public Book(String name, String author, int edition,String category, String code){
        this.name = name;
        this.edition = edition;
        this.category = category;
        this.code = code;
        this.author = author;
    }

    public Book(String name, int edition, float cost, String category, String code){
        this.name = name;
        this.edition = edition;
        this.category = category;
        this.code = code;
        this.cost = cost;
    }

    public Book(String name, String author, float cost, int edition, String category, String code){
        this.name = name;
        this.author = author;
        this.edition = edition;
        this.category = category;
        this.cost = cost;
        this.code = code;
    }

    public float getCost() {
        return cost;
    }

    public String getCode() {
        return code;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public int getEdition() {
        return edition;
    }

    public String getName() {
        return name;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }
}
