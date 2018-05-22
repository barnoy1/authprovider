package com.firebase.authwrapper.providers.types;


import android.content.Intent;

import com.firebase.authwrapper.providers.delegate.Provider;
import com.game.authprovider.R;
import com.firebase.authwrapper.providers.common.properties.ProviderProperties;
import com.firebase.authwrapper.providers.common.base.ProviderBase;
import com.firebase.authwrapper.providers.common.callbacks.AuthenticationListener;
import com.google.firebase.auth.FirebaseUser;

/**
 * This class implements NullProvider object. This object throws
 * exceptions of all {@link IProvider IProvider} methods and
 * abstract {@link ProviderBase ProviderBase} methods
 *
 * @author ron barnoy
 * @version 1.0
 * @since 10-5-2018
 * @see Provider
 * @see ProviderBase
 */
public class NullProvider extends ProviderBase implements IProvider {


    /**
     * ctor. Initialize an instance of this class
     * @param providerProperties target provider properties needed for
     *                           {@link ProviderBase ProviderBase} instance
     *
     * @see ProviderBase
     */
    public NullProvider(ProviderProperties providerProperties) {
        super();
    }

    /**
     * This method throws {@link InterruptedException InterruptedException}
     * @throws InterruptedException
     */
    @Override
    public void SignIn() throws InterruptedException {
        String message =  context.getString(R.string
                .sign_in_null_provider_exception);

        throw new InterruptedException(message);
    }

    /**
     * This method throws {@link InterruptedException InterruptedException}
     * @throws InterruptedException
     */
    @Override
    public void SignIn(String email, String Password, AuthenticationListener.Email emailCallback) throws IllegalArgumentException {
        String message =  context.getString(R.string
                .sign_in_null_provider_exception);

        throw new IllegalArgumentException(message);
    }

    /**
     * This method throws {@link InterruptedException InterruptedException}
     * @throws InterruptedException
     */
    @Override
    public void SendEmailVerification(FirebaseUser user) throws IllegalArgumentException {
        String message =  context.getString(R.string
                .send_email_null_provider_exception);

        throw new IllegalArgumentException(message);
    }

    /**
     * This method throws {@link InterruptedException InterruptedException}
     * @throws InterruptedException
     */
    @Override
    public void OnActivityResultReceived(int requestCode, int resultCode, Intent data) {
        String message =  context.getString(R.string
                .on_activity_result_received);

        throw new IllegalArgumentException(message);
    }

}
