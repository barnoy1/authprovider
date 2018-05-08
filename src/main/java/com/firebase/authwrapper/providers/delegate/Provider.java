package com.firebase.authwrapper.providers.delegate;

import android.content.Intent;

import com.firebase.authwrapper.providers.common.properties.ProviderProperties;
import com.firebase.authwrapper.providers.common.enums.ProviderEnum;
import com.firebase.authwrapper.providers.common.callbacks.AuthenticationListener;
import com.firebase.authwrapper.providers.common.factory.ProviderFactory;
import com.firebase.authwrapper.providers.types.IProvider;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;

/**
 * the delegate is used for encapsulate methods and data which not
 * relevant to the user. The end users will be using this delegate
 * API in order to interact with the providers. The delegate will
 * handle the provider switch.
 */
public class Provider implements IProvider {

    private IProvider providerDelegate = ProviderFactory.NULL_PROVIDER;

    public Provider(ProviderProperties providerProperties){

        this.providerDelegate =
                ProviderFactory.createAuthProvider(providerProperties);

    }

    @Override
    public void SignOut() {
        providerDelegate.SignOut();
    }

    @Override
    public void SignIn() throws InterruptedException {
        providerDelegate.SignIn();
    }

    @Override
    public void SignIn(String email, String Password,
                       AuthenticationListener.Email emailCallback) throws
            IllegalArgumentException {
        providerDelegate.SignIn(email, Password, emailCallback);
    }

    @Override
    public void SendEmailVerification(FirebaseUser user)
            throws IllegalArgumentException {
        providerDelegate.SendEmailVerification(user);
    }

    @Override
    public FirebaseUser getUser() {
        return providerDelegate.getUser();
    }

    @Override
    public AuthCredential getAuthCredential() {
        return providerDelegate.getAuthCredential();
    }

    @Override
    public ProviderEnum.ProviderType getProviderType() {
        return providerDelegate.getProviderType();
    }

    @Override
    public void Register(AuthenticationListener authenticationListener) {
        providerDelegate.Register(authenticationListener);
    }

    @Override
    public void UnRegister(AuthenticationListener authenticationListener) {
        providerDelegate.UnRegister(authenticationListener);
    }

    @Override
    public void OnActivityResultReceived(int requestCode, int resultCode, Intent data) {
        providerDelegate.OnActivityResultReceived(requestCode, resultCode, data);
    }

}
