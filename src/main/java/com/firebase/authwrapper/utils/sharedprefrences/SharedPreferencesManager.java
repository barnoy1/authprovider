package com.firebase.authwrapper.utils.sharedprefrences;

import android.content.Context;
import android.content.SharedPreferences;

import com.firebase.authwrapper.providers.delegate.Provider;

import static android.content.Context.MODE_PRIVATE;

/**
 * Utility class for handling SharedPreferences objects
 *
 * @author ron barnoy
 * @version 1.0
 * @since 10-5-2018
 * @see ISharedPreferencesManager
 */
public class SharedPreferencesManager implements ISharedPreferencesManager {

    private static SharedPreferencesManager sharedPreferencesManager = new
             SharedPreferencesManager();

    private String SharedPreferencesName;
    private SharedPreferences prefs =  null;

    private final static String TAG = SharedPreferencesManager.class.getSimpleName();

    private SharedPreferencesManager() {};

    /**
     * method for returning the static instance of the singleton
     * SharedPreferencesManager
     * @return the private static instance of this class
     */
    public static SharedPreferencesManager getInstace(){
        return sharedPreferencesManager;
    }

    /**
     * Configure SharedPreferences object
     * @param context target context of SharedPreferences object
     * @param sharedPreferencesName target name of SharedPreferences object
     */
    public void Configure (Context context, String
            sharedPreferencesName){

        this.SharedPreferencesName = sharedPreferencesName;

        prefs =  context.getSharedPreferences(SharedPreferencesName,
                MODE_PRIVATE);

    }

    /**
     * setter method for setting string key value type
     * @param key the name of target key
     * @param value the string value of this key
     */
    public void setStringValue(String key, String value){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * getter method for setting string key value type
     * @param key the name of target key
     * @return target key value
     */
    public String getStringValue(String key){
        return prefs.getString(key, null);
    }

    /**
     * setter method for setting integer key value type
     * @param key the name of target key
     * @param value the integer value of this key
     */
    public void setIntValue(String key, int value){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * getter method for setting integer key value type
     * @param key the name of target key
     * @return target key value
     */
    public int getIntValue(String key){
        return prefs.getInt(key, -1);
    }

    /**
     * setter method for setting boolean key value type
     * @param key the name of target key
     * @param value the integer value of this key
     */
    public void setBoolValue(String key, boolean value){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * getter method for setting boolean key value type
     * @param key the name of target key
     * @return target key value
     */
    public boolean getBoolValue(String key){
        return prefs.getBoolean(key, false);
    }

}
