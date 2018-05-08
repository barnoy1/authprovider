package com.firebase.authwrapper.utils.sharedprefrences;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesManager implements ISharedPreferencesManager {

    private static SharedPreferencesManager sharedPreferencesManager = new
             SharedPreferencesManager();

    private String SharedPreferencesName;
    private SharedPreferences prefs =  null;

    private final static String TAG = SharedPreferencesManager.class.getSimpleName();

    /**
     * A private ctor prevents any other
     * class from instantiating.
     */
    private SharedPreferencesManager() {};

    /**
     * method for returning the static instance of the singleton
     * SharedPreferencesManager
     * @return the private static instance of this class
     */
    public static SharedPreferencesManager getInstace(){
        return sharedPreferencesManager;
    }

    public void Configure (Context context, String
            sharedPreferencesName){

        this.SharedPreferencesName = sharedPreferencesName;

        prefs =  context.getSharedPreferences(SharedPreferencesName,
                MODE_PRIVATE);

    }

    public void setStringValue(String key, String value){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getStringValue(String key){
        return prefs.getString(key, null);
    }

    public void setIntValue(String key, int value){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getIntValue(String key){
        return prefs.getInt(key, -1);
    }

    public void setBoolValue(String key, boolean value){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolValue(String key){
        return prefs.getBoolean(key, false);
    }

}
