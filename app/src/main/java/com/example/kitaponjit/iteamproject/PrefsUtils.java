package com.example.kitaponjit.iteamproject;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsUtils {

    private static String p_name = "db_project";

    public PrefsUtils() {

    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(p_name, Context.MODE_PRIVATE);
    }

    public static void setHost(Context context, String host) {
        if(!host.equals(getHost(context))){
            SharedPreferences.Editor editor = getSharedPreferences(context).edit();
            editor.putString("host", "http://"+ host +"/iTeam");
            editor.apply();
        }
    }

    public static String getHost(Context context) {
        return getSharedPreferences(context).getString("host", null);
    }

    public static void clearHost(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.clear();
        editor.apply();
    }

}
