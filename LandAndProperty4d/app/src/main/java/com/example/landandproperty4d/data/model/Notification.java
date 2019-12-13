package com.example.landandproperty4d.data.model;

public class Notification {
    String namePost ;
    String email ;
    String interestPeople ;
    String notifyDay;
    String phone;
    String idSeller;

    public Notification() {
    }

    public Notification(String namePost, String email, String interestPeople, String notifyDay, String phone , String idSeller) {
        this.namePost = namePost;
        this.email = email;
        this.interestPeople = interestPeople;
        this.notifyDay = notifyDay;
        this.phone = phone;
        this.idSeller = idSeller;
    }

    public String getIdSeller() {
        return idSeller;
    }

    public void setIdSeller(String idSeller) {
        this.idSeller = idSeller;
    }

    public String getNamePost() {
        return namePost;
    }

    public void setNamePost(String namePost) {
        this.namePost = namePost;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInterestPeople() {
        return interestPeople;
    }

    public void setInterestPeople(String interestPeople) {
        this.interestPeople = interestPeople;
    }

    public String getNotifyDay() {
        return notifyDay;
    }

    public void setNotifyDay(String notifyDay) {
        this.notifyDay = notifyDay;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
