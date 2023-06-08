package com.example.bbcnewsreader;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * The SessionUtilities class provides utility methods for managing session data using SharedPreferences.
 */
public class SessionUtilities {
    private static String NAME = "store";
    public static String FEED = "feed";

    /**
     * Sets a session value with the specified key.
     *
     * @param context The context of the application.
     * @param key The key for the session value.
     * @param obj The object to be stored as the session value.
     */
    public static void set(Context context, String key, Object obj){
        SharedPreferences sp = context.getSharedPreferences(NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, new Gson().toJson(obj));
        editor.apply();
    }

    /**
     * Retrieves a session value with the specified key.
     *
     * @param context The context of the application.
     * @param key The key for the session value.
     * @param def The default value to be returned if the session value does not exist.
     * @return An ArrayList of NewsArticle objects representing the session value.
     */
    public static ArrayList<NewsArticle> get(Context context, String key, String def){
        if(def == null) def = "[]";
        SharedPreferences sp = context.getSharedPreferences(NAME, MODE_PRIVATE);
        String val = sp.getString(key, def);
        Type type = new TypeToken<ArrayList<NewsArticle>>(){}.getType();
        return new Gson().fromJson(val, type);
    }

    /**
     * Removes a session value with the specified key.
     *
     * @param context The context of the application.
     * @param key The key for the session value to be removed.
     */
    public static void remove(Context context, String key){
        SharedPreferences sp = context.getSharedPreferences(NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }
}
