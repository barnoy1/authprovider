package com.firebase.authwrapper.utils.sharedprefrences;

import android.content.Context;


/**
 * This class is an interface of {@link SharedPreferencesManager SharedPreferencesManager}
 * utility object
 *
 * @author ron barnoy
 * @version 1.0
 * @since 10-5-2018
 * @see ISharedPreferencesManager
 */
public interface ISharedPreferencesManager {
    /**
     * Configure SharedPreferences object
     * @param context target context of SharedPreferences object
     * @param sharedPreferencesName target name of SharedPreferences object
     *
     * @see SharedPreferencesManager
     */
    void Configure (Context context, String sharedPreferencesName);

    /**
     * setter method for setting string key value type
     * @param key the name of target key
     * @param value the string value of this key
     */
    void setStringValue(String key, String value);

    /**
     * getter method for setting string key value type
     * @param key the name of target key
     * @return target key value
     */
    String getStringValue(String key);

    /**
     * setter method for setting integer key value type
     * @param key the name of target key
     * @param value the integer value of this key
     */
    void setIntValue(String key, int value);

    /**
     * getter method for setting integer key value type
     * @param key the name of target key
     * @return target key value
     */
    int getIntValue(String key);

    /**
     * setter method for setting boolean key value type
     * @param key the name of target key
     * @param value the integer value of this key
     */
    void setBoolValue(String key, boolean value);

    /**
     * getter method for setting boolean key value type
     * @param key the name of target key
     * @return target key value
     */
    boolean getBoolValue(String key);

}
