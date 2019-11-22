package com.example.landandproperty4d.data.model;

public class PostProperty  {
    private String imagePost;
    private String title;
    private String typeLand;
    private String area;
    private String location;
    private String address;
    private String houseDirection;
    private String price;
    private String contact;
    private String detail;
    private String postDay;
    private String id;

    public PostProperty() {
    }

    public PostProperty(String imagePost, String title, String typeLand, String area, String location, String address,
                        String houseDirection, String price, String contact, String detail, String id, String postDay) {
        this.imagePost = imagePost;
        this.title = title;
        this.typeLand = typeLand;
        this.area = area;
        this.location = location;
        this.address = address;
        this.houseDirection = houseDirection;
        this.price = price;
        this.contact = contact;
        this.detail = detail;
        this.id = id;
        this.postDay = postDay;

    }

    public String getImagePost() {
        return imagePost;
    }

    public void setImagePost(String imagePost) {
        this.imagePost = imagePost;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTypeLand() {
        return typeLand;
    }

    public void setTypeLand(String typeLand) {
        this.typeLand = typeLand;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHouseDirection() {
        return houseDirection;
    }

    public void setHouseDirection(String houseDirection) {
        this.houseDirection = houseDirection;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPostDay() {
        return postDay;
    }

    public void setPostDay(String postDay) {
        this.postDay = postDay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

