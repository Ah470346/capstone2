package com.example.landandproperty4d.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String userName;
    private String password;
    private String name;
    private String birthDay;
    private String address;
    private String homeTown;
    private String email;
    private String phoneNumber;
    private String placesInterest;
    private String Ruler;

    public User() {
    }

    public User(String userName, String password, String name, String birthDay, String address, String homeTown, String email, String phoneNumber, String placesInterest, String ruler) {
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.birthDay = birthDay;
        this.address = address;
        this.homeTown = homeTown;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.placesInterest = placesInterest;
        Ruler = ruler;
    }

    protected User(Parcel in) {
        userName = in.readString();
        password = in.readString();
        name = in.readString();
        birthDay = in.readString();
        address = in.readString();
        homeTown = in.readString();
        email = in.readString();
        phoneNumber = in.readString();
        placesInterest = in.readString();
        Ruler = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(password);
        dest.writeString(name);
        dest.writeString(birthDay);
        dest.writeString(address);
        dest.writeString(homeTown);
        dest.writeString(email);
        dest.writeString(phoneNumber);
        dest.writeString(placesInterest);
        dest.writeString(Ruler);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPlacesInterest() {
        return placesInterest;
    }

    public void setPlacesInterest(String placesInterest) {
        this.placesInterest = placesInterest;
    }

    public String getRuler() {
        return Ruler;
    }

    public void setRuler(String ruler) {
        Ruler = ruler;
    }
}
