package com.rhyscoronado.weatherapp.util;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by rhysc on 3/14/18.
 */

public class GsonUtility {

    public static GsonBuilder createGsonBuilder(Type type, Object typeAdapter){
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping();
        gsonBuilder.registerTypeAdapter(type, typeAdapter);
        return gsonBuilder;
    }

    public static JsonObject convertJSONObjtoJsonObj(JSONObject jsonObject){

        JsonObject obj = new JsonObject();
        obj.getAsJsonObject(jsonObject.toString());

        return obj;
    }

}