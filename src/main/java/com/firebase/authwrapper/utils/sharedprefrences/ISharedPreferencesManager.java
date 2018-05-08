package com.firebase.authwrapper.utils.sharedprefrences;

import android.content.Context;

/**
 * Created by ron on 4/21/2018.
 */

public interface ISharedPreferencesManager {
    void Configure (Context context, String sharedPreferencesName);

    void setStringValue(String key, String value);
    String getStringValue(String key);

    void setIntValue(String key, int value);
    int getIntValue(String key);

    void setBoolValue(String key, boolean value);
    boolean getBoolValue(String key);

}
