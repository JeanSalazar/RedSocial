package com.example.jksa.redsocial.Clases;

import android.util.Log;

public class Common {

    private static String DB_NAME = "redsocial";
    private static String COLLECTION_NAME = "Users";
    private static String API_KEY = "0vgC0wHfmdoyqGDpUQKSn8p0A3Jd4EQI";

    public static String getAddressSingle(User user){
        String baseUrl = String.format("https://api.mlab.com/api/1/databases/%s/collections/%s",DB_NAME,COLLECTION_NAME);
        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        stringBuilder.append("/"+user.get_id().getOid()+"?apiKey="+API_KEY);
        return stringBuilder.toString();
    }

    public static String getAdressAPI(){
        String baseUrl = String.format("https://api.mlab.com/api/1/databases/%s/collections/%s",DB_NAME,COLLECTION_NAME);
        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        stringBuilder.append("?apiKey="+API_KEY);
        Log.d("fds",stringBuilder.toString());
        return stringBuilder.toString();
    }

}
