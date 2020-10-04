package com.yoham.storieskids.data.db.model;

public class Category {

    private int id_category;
    private String name;
    private int number_stories;

    public int getId_category() {
        return id_category;
    }

    public String getName() {
        return name;
    }

    public int getNumber_stories() {
        return number_stories;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber_stories(int number_stories) {
        this.number_stories = number_stories;
    }
}
