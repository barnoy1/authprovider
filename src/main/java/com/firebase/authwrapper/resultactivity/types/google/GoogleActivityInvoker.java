package com.firebase.authwrapper.resultactivity.types.google;

import com.firebase.authwrapper.resultactivity.common.ActivityInvoker;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by ron on 12/15/2017.
 */

public class GoogleActivityInvoker extends ActivityInvoker {
    private GoogleApiClient googleApiClient;


    private GoogleActivityInvoker() {}


    private static GoogleActivityInvoker googleActivityInvoker = new
            GoogleActivityInvoker();

    public static GoogleActivityInvoker getInstance(){
        return googleActivityInvoker;
    }


    public void setGoogleApiClient(GoogleApiClient googleApiClient){
        this.googleApiClient = googleApiClient;
    }

    public GoogleApiClient getGoogleApiClient(){
        return this.googleApiClient;
    }
}
