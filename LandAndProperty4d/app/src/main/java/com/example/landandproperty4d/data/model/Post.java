package com.example.landandproperty4d.data.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Post implements Parcelable {
    private Bitmap ImagePost;
    private String Title;
    private String TypeLand;
    private String Area;
    private String Location;
    private String Address;
    private String HouseDirection;
    private String Price;
    private String Contact;
    private String Detail;
    private String PostDay;


    public Post() {

    }

    public Post(Bitmap ImagePost, String Title, String TypeLand, String Location, String Address
            , String Area, String HouseDirection, String Price, String Contact, String Detail,String PostDay) {
        this.ImagePost = ImagePost;
        this.Title = Title;
        this.TypeLand = TypeLand;
        this.Area = Area;
        this.Location = Location;
        this.Address = Address;
        this.HouseDirection = HouseDirection;
        this.Price = Price;
        this.Contact = Contact;
        this.Detail = Detail;
        this.PostDay = PostDay;

    }

    protected Post(Parcel in) {
        ImagePost = in.readParcelable(Bitmap.class.getClassLoader());
        Title = in.readString();
        TypeLand = in.readString();
        Address = in.readString();
        Area = in.readString();
        Location = in.readString();
        HouseDirection = in.readString();
        Price = in.readString();
        Contact = in.readString();
        Detail = in.readString();
        PostDay = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(ImagePost, flags);
        dest.writeString(Title);
        dest.writeString(TypeLand);
        dest.writeString(Area);
        dest.writeString(Address);
        dest.writeString(Price);
        dest.writeString(HouseDirection);
        dest.writeString(Contact);
        dest.writeString(Location);
        dest.writeString(Detail);
        dest.writeString(PostDay);
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public Bitmap getImagePost() {
        return ImagePost;
    }

    public void setImagePost(Bitmap imagePost) {
        ImagePost = imagePost;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getTypeLand() {
        return TypeLand;
    }

    public void setTypeLand(String typeLand) {
        TypeLand = typeLand;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getHouseDirection() {
        return HouseDirection;
    }

    public void setHouseDirection(String houseDirection) {
        HouseDirection = houseDirection;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }

    public String getPostDay() {
        return PostDay;
    }

    public void setPostDay(String postDay) {
        PostDay = postDay;
    }



}

