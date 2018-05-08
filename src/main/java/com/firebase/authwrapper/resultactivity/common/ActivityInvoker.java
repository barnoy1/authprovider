package com.firebase.authwrapper.resultactivity.common;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.firebase.authwrapper.providers.common.properties.ProviderProperties;
import com.firebase.authwrapper.providers.common.base.ProviderBase;
import com.firebase.authwrapper.providers.common.base.ErrorHandlingManager;
import com.firebase.authwrapper.resultactivity.callback.ActivityResultCallback;
import com.google.android.gms.common.ConnectionResult;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by ron on 12/15/2017.
 */

public abstract class ActivityInvoker {

    protected ActivityResultCallback activityResultCallback;
    protected Context context;
    protected ProviderBase provider;
    protected ProviderProperties providerProperties;

    public Context getContext(){
        return this.context;
    }

    public ProviderProperties getProviderProperties() {
        return this.providerProperties;
    }

    public void register(ActivityResultCallback activityResultCallback){
        this.activityResultCallback = activityResultCallback;
    }

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

            @Override
            public void OnConnectionFailed(@NonNull ConnectionResult connectionResult) {
                String errMessage = String.format("error " +
                        "code:{0}\nMessage{1}", connectionResult
                        .getErrorCode
                                (), connectionResult.getErrorMessage());
                ErrorHandlingManager.getInstace().handle(provider, new
                        Exception(errMessage));
            }

        };
    }

    public void raiseOnActivityResultCallback(int requestCode, int
            resultCode, Intent data){
        this.activityResultCallback
                .OnActivityResult(requestCode, resultCode, data);
    }



    public void raiseOnBroadcastFailed(Exception ex){
        provider.BroadcastOnFailed(ex);
    }

    public void raiseOnBroadcastComplete(FirebaseUser user){
        provider.BroadcastOnComplete(user);
    }
}
