package com.firebase.authwrapper.providers.types;

import android.content.Intent;

import com.firebase.authwrapper.providers.common.enums.ProviderEnum;
import com.firebase.authwrapper.providers.common.callbacks.AuthenticationListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;

/**
 * Auth provider API as exposed to the end user
 * by the authentication delegate.
 */
public interface IProvider {

    //========
    // methods
    //========

    void SignOut();
    void SignIn() throws InterruptedException;

    void SignIn(String email, String Password, AuthenticationListener.Email
            emailCallback) throws IllegalArgumentException;
    void SendEmailVerification(FirebaseUser user) throws
            IllegalArgumentException;;

    void Register(AuthenticationListener authenticationListener);
    void UnRegister(AuthenticationListener authenticationListener);
    void OnActivityResultReceived(int requestCode, int resultCode, Intent data);

    FirebaseUser getUser();
    AuthCredential getAuthCredential();
    ProviderEnum.ProviderType getProviderType();

}
