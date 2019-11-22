package com.example.landandproperty4d.data.local;

import android.content.Context;

public class SharedPreferences implements Constant {

    private static SharedPreferences suInstance;
    android.content.SharedPreferences sharedPreferences;
    android.content.SharedPreferences.Editor editor;

    public static SharedPreferences getInstance() {
        if (suInstance == null) {
            suInstance = new SharedPreferences();
        }
        return suInstance;
    }

    public void init(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void reset() {
        editor.clear();
        save();
    }

    private void save() {
        editor.apply();
    }
    public void saveAddress(String address) {
        editor.putString(this.ADDRESS,address);
        save();
    }

    public String getAddress() {
        String value = sharedPreferences.getString(this.ADDRESS, "");
        return value;
    }

    public void saveImage(String image) {
        editor.putString(this.IMAGE,image);
        save();
    }

    public String getImage() {
        String value = sharedPreferences.getString(this.IMAGE, "");
        return value;
    }

    public void saveArea(String area) {
        editor.putString(this.AREA,area);
        save();
    }

    public String getArea() {
        String value = sharedPreferences.getString(this.AREA, "");
        return value;
    }

    public void saveTypeland(String type) {
        editor.putString(this.TYPELAND,type);
        save();
    }

    public String getTypeland() {
        String value = sharedPreferences.getString(this.TYPELAND, "");
        return value;
    }

    public void saveLocation(String location) {
        editor.putString(this.LOCATION,location);
        save();
    }

    public String getLocation() {
        String value = sharedPreferences.getString(this.LOCATION, "");
        return value;
    }

    public void saveDirecton(String direction) {
        editor.putString(this.DIRECTION,direction);
        save();
    }

    public String getDirection() {
        String value = sharedPreferences.getString(this.DIRECTION, "");
        return value;
    }

    public void savePrice(String price) {
        editor.putString(this.PRICE,price);
        save();
    }

    public String getPrice() {
        String value = sharedPreferences.getString(this.PRICE, "");
        return value;
    }

    public void saveContact(String contact) {
        editor.putString(this.CONTACT,contact);
        save();
    }

    public String getContact() {
        String value = sharedPreferences.getString(this.CONTACT, "");
        return value;
    }

    public void saveDetail(String detail) {
        editor.putString(this.DETAIL,detail);
        save();
    }

    public String getDetail() {
        String value = sharedPreferences.getString(this.DETAIL, "");
        return value;
    }

    public void saveTitle(String title) {
        editor.putString(this.TITLE,title);
        save();
    }

    public String getTitle() {
        String value = sharedPreferences.getString(this.TITLE, "");
        return value;
    }
}