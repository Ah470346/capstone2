package com.example.landandproperty4d.data.model;


public class PostProperty {
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

    public PostProperty(final String imagePost, final String title, final String typeLand, final String area,
            final String location,
            final String address, final String houseDirection, final String price, final String contact,
            final String detail, final String postDay) {
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
        this.postDay = postDay;
    }

    public PostProperty() {
    }

    public String getImagePost() {
        return imagePost;
    }

    public void setImagePost(final String imagePost) {
        this.imagePost = imagePost;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getTypeLand() {
        return typeLand;
    }

    public void setTypeLand(final String typeLand) {
        this.typeLand = typeLand;
    }

    public String getArea() {
        return area;
    }

    public void setArea(final String area) {
        this.area = area;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(final String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getHouseDirection() {
        return houseDirection;
    }

    public void setHouseDirection(final String houseDirection) {
        this.houseDirection = houseDirection;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(final String price) {
        this.price = price;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(final String contact) {
        this.contact = contact;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(final String detail) {
        this.detail = detail;
    }

    public String getPostDay() {
        return postDay;
    }

    public void setPostDay(final String postDay) {
        this.postDay = postDay;
    }
}
