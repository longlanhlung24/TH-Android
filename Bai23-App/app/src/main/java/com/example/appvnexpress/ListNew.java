package com.example.appvnexpress;

import android.graphics.Bitmap;

public class ListNew {
    private Bitmap image;
    private String title;
    private String description;
    private String link;

    public ListNew(Bitmap image, String title, String description, String link) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.link = link;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }
}
