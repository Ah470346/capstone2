package com.example.landandproperty4d.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.landandproperty4d.screen.login.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public  class CommonUtils {
    public static String CHILD_STORAGE = "imagepost";
    public static String EXTENTION_PHOTO = ".JPEG";
    public static String rule = "";

    public static String getSimpleDateFormat(){
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    }
    public static String getSimpleDateFormatPost(){
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    }
    public static boolean isVietnames (String a){
        String vn = "|Ắ|Ắ|Ắ|Ằ|Ằ|Ằ|Ẳ|Ẳ|Ẳ|Ẵ|Ẵ|Ẵ|Ặ|Ặ|Ặ|Ặ|Ặ|Ặ|Ă|Ă|Ấ|Ấ|Ấ|Ầ|Ầ|Ầ|Ẩ|Ẩ|Ẩ|Ẫ|Ẫ|Ẫ|Ậ|Ậ|Ậ|Ậ|Ậ|Ậ|Â|Â|Á|Á|À|À|Ã|Ã|Ả|Ả|Ạ|Ạ|Ế|Ế|Ế|Ề|Ề|Ề|Ể|Ể|Ể|Ễ|Ễ|Ễ|Ệ|Ệ|Ệ|Ệ|Ệ|Ệ|Ê|Ê|É|É|È|È|Ẻ|Ẻ|Ẽ|Ẽ|Ẹ|Ẹ|Í|Í|Ì|Ì|Ỉ|Ỉ|Ĩ|Ĩ|Ị|Ị|Ố|Ố|Ố|Ồ|Ồ|Ồ|Ổ|Ổ|Ổ|Ỗ|Ỗ|Ỗ|Ộ|Ộ|Ộ|Ộ|Ộ|Ộ|Ô|Ô|Ớ|Ớ|Ớ|Ớ|Ớ|Ớ|Ờ|Ờ|Ờ|Ờ|Ờ|Ờ|Ở|Ở|Ở|Ở|Ở|Ở|Ỡ|Ỡ|Ỡ|Ỡ|Ỡ|Ỡ|Ợ|Ợ|Ợ|Ợ|Ợ|Ợ|Ơ|Ơ|Ó|Ó|Ò|Ò|Õ|Õ|Ỏ|Ỏ|Ọ|Ọ|Ứ|Ứ|Ứ|Ứ|Ứ|Ứ|Ừ|Ừ|Ừ|Ừ|Ừ|Ừ|Ử|Ử|Ử|Ử|Ử|Ử|Ữ|Ữ|Ữ|Ữ|Ữ|Ữ|Ự|Ự|Ự|Ự|Ự|Ự|Ư|Ư|Ú|Ú|Ù|Ù|Ủ|Ủ|Ũ|Ũ|Ụ|Ụ|Ý|Ý|Ỳ|Ỳ|Ỷ|Ỷ|Ỹ|Ỹ|Ỵ|á|à|ạ|ã|ả|ă|ắ|ằ|ẳ|ặ|ẵ|â|ấ|ầ|ẫ|ẩ|ậ|đ|é|è|ẹ|ẽ|ẻ|ê|ế|ề|ệ|ể|ễ|í|ì|ị|ỉ|ĩ|ó|ò|ọ|ỏ|õ|ố|ồ|ộ|ổ|ỗ|ô|ớ|ơ|ờ|ợ|ỡ|ở|ú|ù|ụ|ũ|ủ|ứ|ư|ừ|ự|ữ|ử|ý|ỳ|ỵ|ỷ|ỹ|";
        for (int i = 0 ; i< vn.length(); i++){
            char ch = vn.charAt(i);
            Log.d("holll" , Character.toString(ch));
            if(a.contains(Character.toString(ch))){
                return true;
            }
        }
        return false;
    }
    public  static boolean isSpecialCharacter(String a){
        String special = " {}[]()_-=+:<>&*^&%$#~`|:;'@?/.,";
        for (int i = 0 ; i< special.length(); i++){
            char ch = special.charAt(i);
            Log.d("hollloo" , Character.toString(ch));
            if(a.contains(Character.toString(ch))){
                return true;
            }
        }
        return false;
    }

    public  static boolean isSpecialEmail(String a){
        String special = " {}[]()_-=+:<>&*^&%$#~`|:;'?/,";
        for (int i = 0 ; i< special.length(); i++){
            char ch = special.charAt(i);
            Log.d("hollloo" , Character.toString(ch));
            if(a.contains(Character.toString(ch))){
                return true;
            }
        }
        return false;
    }
    public static boolean isErrorEmail (String a){
        String special = "@gmail.com";
        for (int i = 0 ; i< special.length(); i++){
            char ch = special.charAt(i);
            Log.d("hollloo" , Character.toString(ch));
            if(!a.contains(Character.toString(ch))){
                return true;
            }
        }
        return false;
    }
}
