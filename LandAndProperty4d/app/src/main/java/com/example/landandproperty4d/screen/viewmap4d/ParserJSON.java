package com.example.landandproperty4d.screen.viewmap4d;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParserJSON {
    private  String data;
    private Double lat,lng;
    public ParserJSON(String data){
        this.data = data;
    }

    public Double laytoadoLAT () {
        try {
            JSONObject object = new JSONObject(data);
            JSONArray result = object.getJSONArray("result");
            for (int i =0; i<result.length();i++){
                JSONObject object1Result = result.getJSONObject(i);
                JSONObject object1Location = object1Result.getJSONObject("location");
                lat = object1Location.getDouble("lat");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lat;
    }

    public Double laytoadoLNG () {
        try {
            JSONObject object = new JSONObject(data);
            JSONArray result = object.getJSONArray("result");
            for (int i =0; i<result.length();i++){
                JSONObject object1Result = result.getJSONObject(i);
                JSONObject object1Location = object1Result.getJSONObject("location");
                lng = object1Location.getDouble("lng");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lng;
    }

    public String laytrangthai (){
        String stt = "";
        try {
            JSONObject object1 = new JSONObject(data);
            stt = object1.getString("code");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stt;
    }

    public String layloi (){
        String error = "";
        try {
            JSONObject object1 = new JSONObject(data);
            error = object1.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return error;
    }
}
