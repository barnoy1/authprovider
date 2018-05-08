package com.firebase.authwrapper.resultactivity.callback;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.firebase.authwrapper.providers.common.base.ProviderBase;
import com.google.android.gms.common.ConnectionResult;

/**
 * Interface for exposing activity result data from
 * {@link AuthProviderActivity AuthProviderActivity}
 * to {@link ProviderBase
 *  ProviderBase}
 */
public interface ActivityResultCallback {
    /**
     * this callback is for exposing the targetActivity ActivityResult
     * data to the provider base class
     * @param requestCode request code of the authProvider Activity exposed
     *                    to provider class.
     * @param resultCode result code of the authProvider Activity exposed
     *                    to provider class.
     * @param data data of the authProvider Activity exposed
     *                    to provider class.
     */
    void OnActivityResult(int requestCode, int resultCode, Intent data);

    void OnConnectionFailed(@NonNull ConnectionResult connectionResult);

}
