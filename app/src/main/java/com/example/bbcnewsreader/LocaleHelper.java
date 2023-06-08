package com.example.bbcnewsreader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import java.util.Locale;

/**
 * This class provides helper methods for managing the application's locale settings.
 */
public class LocaleHelper {
    private static final String LANGUAGE_KEY = "CHOOSE_LANGUAGE";

    /**
     * Sets a new locale for the application and persists it.
     *
     * @param context  The context of the application.
     * @param language The new language to set.
     * @return The updated context with the new locale.
     */
    public static Context setNewLocale(Context context, String language) {
        persistLanguage(context, language);
        return updateResources(context, language);
    }

    /**
     * Gets the current language setting of the application.
     *
     * @param context The context of the application.
     * @return The current language setting.
     */
    public static String getLanguage(Context c) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
        Locale currentLocale = getLocale(c.getResources());
        return !prefs.contains(LANGUAGE_KEY) ? null : prefs.getString(LANGUAGE_KEY, currentLocale.toString());
    }
    @SuppressLint("ApplySharedPref")
    private static void persistLanguage(Context c, String language) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
        prefs.edit().putString(LANGUAGE_KEY, language).commit();
    }
    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        return updateResources(context, locale);
    }
    private static Context updateResources(Context context, Locale locale) {
        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        if (Build.VERSION.SDK_INT >= 17) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
            res.updateConfiguration(config, res.getDisplayMetrics());
        } else {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return context;
    }
    /**
     * Gets the locale based on the given resources.
     *
     * @param res The resources object.
     * @return The locale.
     */
    public static Locale getLocale(Resources res) {
        Configuration config = res.getConfiguration();
        return Build.VERSION.SDK_INT >= 24 ? config.getLocales().get(0) : config.locale;
    }
}
