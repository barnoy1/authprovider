package com.firebase.authwrapper.providers.types;


import android.content.Intent;

import com.game.authprovider.R;
import com.firebase.authwrapper.providers.common.properties.ProviderProperties;
import com.firebase.authwrapper.providers.common.base.ProviderBase;
import com.firebase.authwrapper.providers.common.callbacks.AuthenticationListener;
import com.google.firebase.auth.FirebaseUser;

public class NullProvider extends ProviderBase implements IProvider {


    public NullProvider(ProviderProperties providerProperties) {
        super();
    }

    @Override
    public void SignIn() throws InterruptedException {
        String message =  context.getString(R.string
                .sign_in_null_provider_exception);

        throw new InterruptedException(message);
    }

    @Override
    public void SignIn(String email, String Password, AuthenticationListener.Email emailCallback) throws IllegalArgumentException {
        String message =  context.getString(R.string
                .sign_in_null_provider_exception);

        throw new IllegalArgumentException(message);
    }

    @Override
    public void SendEmailVerification(FirebaseUser user) throws IllegalArgumentException {
        String message =  context.getString(R.string
                .send_email_null_provider_exception);

        throw new IllegalArgumentException(message);
    }


    @Override
    public void OnActivityResultReceived(int requestCode, int resultCode, Intent data) {
        String message =  context.getString(R.string
                .on_activity_result_received);

        throw new IllegalArgumentException(message);
    }

}
