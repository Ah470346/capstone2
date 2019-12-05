package com.example.landandproperty4d.data.model;

public class New {
    String title ;
    String content;
    String image ;
    String id;
    String postNewDay ;

    public New() {
    }

    public New(String title, String content, String image,String id, String postNewDay) {
        this.title = title;
        this.content = content;
        this.image = image;
        this.id = id;
        this.postNewDay = postNewDay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPostNewDay() {
        return postNewDay;
    }

    public void setPostNewDay(String postNewDay) {
        this.postNewDay = postNewDay;
    }
}
