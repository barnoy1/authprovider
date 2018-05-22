package com.firebase.authwrapper.resultactivity.callback;

import android.content.Intent;
import com.firebase.authwrapper.providers.common.base.ProviderBase;
import com.firebase.authwrapper.providers.delegate.Provider;
import com.firebase.authwrapper.resultactivity.common.ActivityInvoker;
/**
 * Interface for exposing activity result data from
 * {@link ActivityInvoker ActivityInvoker}
 * to {@link ProviderBase
 * ProviderBase}
 *
 * @author ron barnoy
 * @version 1.0
 * @since 10-5-2018
 * @see ActivityInvoker
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

}
