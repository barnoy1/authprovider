package com.firebase.authwrapper.resultactivity.common;

import com.firebase.authwrapper.resultactivity.callback.ActivityResultCallback;

/**
 * An Interface of {@link BaseResultActivity BaseResultActivity} object
 * Used for bind {@link ActivityInvoker ActivityInvoker} to the target activity
 * by initializing {@link ActivityResultCallback ActivityResultCallback}
 *
 * @author ron barnoy
 * @version 1.0
 * @since 10-5-2018
 * @see BaseResultActivity
 */
public interface IResultActivity {

    /**
     * This method is used for initialize {@link ActivityResultCallback
     * ActivityResultCallback}. This connects the current result activity
     * to an {@link ActivityInvoker ActivityInvoker}. This binding allows the
     * library to pass OnActivityResult callback from a result activity to
     * target provider and finally to the target application callbacks
     * @param activityResultCallback the target callback of a {@link ActivityInvoker
     * ActivityInvoker}
     */
    void Register(ActivityResultCallback activityResultCallback);
}
