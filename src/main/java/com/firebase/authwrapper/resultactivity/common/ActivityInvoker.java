package com.firebase.authwrapper.resultactivity.common;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.firebase.authwrapper.providers.common.callbacks.AuthenticationListener;
import com.firebase.authwrapper.providers.common.properties.ProviderProperties;
import com.firebase.authwrapper.providers.common.base.ProviderBase;
import com.firebase.authwrapper.providers.common.base.ErrorHandlingManager;
import com.firebase.authwrapper.resultactivity.callback.ActivityResultCallback;
import com.google.android.gms.common.ConnectionResult;
import com.google.firebase.auth.FirebaseUser;

/**
 * This class base class provides the connection between an activity
 * object (google/facebook result activity) and its matching provider object.
 * The {@link ActivityInvoker} is used to pass data and trigger relevant methods
 * from within the result activities.
 *
 *
 * @author ron barnoy
 * @version 1.0
 * @since 10-5-2018
 * @see BaseResultActivity
 * @see ProviderBase
 */
public abstract class ActivityInvoker {

    protected ActivityResultCallback activityResultCallback;
    protected Context context;
    protected ProviderBase provider;
    protected ProviderProperties providerProperties;

    /**
     * Getter of activity context
     * @return activity Context object
     */
    public Context getContext(){
        return this.context;
    }

    /**
     * Getter of this provider {@link ProviderProperties ProviderProperties}
     * @return the target provider ProviderProperties
     */
    public ProviderProperties getProviderProperties() {
        return this.providerProperties;
    }

    /**
     * Registers a result activity callback to this object
     * @param activityResultCallback the target {@link ActivityResultCallback
     * ActivityResultCallback} which will be triggered on authentication process
     */
    public void register(ActivityResultCallback activityResultCallback){
        this.activityResultCallback = activityResultCallback;
    }

    /**
     * Registers a provider to this object
     * @param provider the target {@link ProviderBase
     * provider} which will be triggered on authentication process
     */
    public void register(final ProviderBase provider){

        this.context = provider.getContext();
        this.provider = provider;
        this.providerProperties = provider.getProviderProperties();

        this.activityResultCallback = new ActivityResultCallback() {
            @Override
            public void OnActivityResult(int requestCode, int resultCode, Intent data) {
                provider.OnActivityResultReceived(requestCode, resultCode,
                        data);
            }
        };
    }

    /**
     * <p>
     * This method passes the result activity OnActivityResult callback
     * to the target provider. This shows the bind that
     * {@link ActivityInvoker ActivityInvoker} does between
     * {@link ProviderBase provider} and {@link BaseResultActivity
     * BaseResultActivity} objects.
     * </p>
     * <p>
     * Notifying the derived provider that data has arrived from
     * private auth activity containing relevant authentication data
     * @param requestCode activity result requestCode
     * @param resultCode activity result resultCode
     * @param data activity result intent
     * </p>
     */
    public void raiseOnActivityResultCallback(int requestCode, int
            resultCode, Intent data){
        this.activityResultCallback
                .OnActivityResult(requestCode, resultCode, data);
    }

    /**
     * Triggers {@link ErrorHandlingManager ErrorHandlingManager} singleton
     * by raise an exception which was caught in {@link BaseResultActivity
     * BaseResultActivity} during authentication process of the target
     * {@link ProviderBase provider}
     * @param ex the target exception which was caught in result activity
     *
     * @see ProviderBase
     * @see ErrorHandlingManager
     */
    public void raiseOnBroadcastFailed(Exception ex){
        ErrorHandlingManager.getInstace()
                .handle(provider, ex);
    }

    /**
     * Triggers the registered {@link ProviderBase provider} to broadcast
     * the authentication data to registered {@link AuthenticationListener
     * AuthenticationListener} of this provider
     *
     * @see ProviderBase
     * @see AuthenticationListener
     */
    public void raiseOnBroadcastComplete(FirebaseUser user){
        provider.BroadcastOnComplete(user);
    }
}
