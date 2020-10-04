package com.yoham.storieskids.data.db.model;

import android.graphics.Bitmap;

import java.sql.Blob;

public class Story {

    private int id_story;
    private String title;
    private String body;
    private Bitmap image;
    private int is_favorites;
    private int id_category;

    public int getId_story() {
        return id_story;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public Bitmap getImage() {
        return image;
    }

    public int getIs_favorites() {
        return is_favorites;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_story(int id_story) {
        this.id_story = id_story;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setIs_favorites(int is_favorites) {
        this.is_favorites = is_favorites;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }
}
