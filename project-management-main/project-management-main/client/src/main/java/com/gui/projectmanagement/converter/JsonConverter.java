package com.gui.projectmanagement.converter;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

public class JsonConverter {
    public static <T> T convertJsonToObject(String json, Type type) {
        try {
            Gson gson = new Gson();
            T object = gson.fromJson(json, type);
            return object;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return (T) "";
        }
    }

    public static String convertObjectToJson(Object object) {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(object);
            return json;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
