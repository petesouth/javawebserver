package com.shyhumangames.app.javawebserver.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;




public class Utils {

    public static String toJson(Object obj) {
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        return json;
    }

    public static <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
        Gson gson = new Gson();
        T foo = gson.fromJson(json, classOfT);
        return foo;
    }
    
}
