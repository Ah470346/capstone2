package com.example.landandproperty4d.utils;

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
}
