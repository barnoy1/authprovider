package com.firebase.authwrapper.providers.common.base;

import android.content.Context;
import android.content.Intent;

import com.game.authprovider.R;
import com.firebase.authwrapper.providers.common.enums.ProviderEnum;
import com.firebase.authwrapper.providers.common.properties.ProviderProperties;
import com.firebase.authwrapper.utils.sharedprefrences.SharedPreferencesManager;
import com.firebase.authwrapper.providers.common.callbacks.AuthenticationListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * base class of auth provider types
 */
public abstract class ProviderBase {


    //=======
    // fields
    //=======

    protected FirebaseAuth mAuth;
    protected ProviderEnum.ProviderType currentProviderType;
    protected AuthCredential credential;
    protected FirebaseUser user;
    protected ArrayList<AuthenticationListener> authCallbacklist = new ArrayList<>();
    protected Context context;
    protected ProviderProperties providerProperties = null;

    private static final String MY_PREFS_NAME = "AuthSharedPrefs";
    //=================
    // Concrete methods
    //=================

    /**
     * initialize firebase singleton
     */
    public ProviderBase(ProviderProperties providerProperties){

        mAuth = FirebaseAuth.getInstance();

        context = providerProperties.getTargetActivity()
                .getApplicationContext();

        this.providerProperties = providerProperties;
        this.currentProviderType = providerProperties.getProviderType();

        Register((AuthenticationListener)
                providerProperties.getTargetActivity());

        // Configure shared prefrances for retrieving current mail
        SharedPreferencesManager.getInstace()
                .Configure(context, MY_PREFS_NAME);
    }



    /**
     * NULL_PROVIDER ctor
     */
    protected ProviderBase() {
    }

    /**
     * register a {@link AuthenticationListener listener} to the provider
     * class. The listener holds several callbacks which will be
     * reflected on the sender class.
     * @param authenticationListener listener for communicating the sender
     *                     to the providers flow.
     */
    public void Register(AuthenticationListener authenticationListener) {
        authCallbacklist.add(authenticationListener);
    }

    public void UnRegister(AuthenticationListener authenticationListener) {
        // remove from list
        authCallbacklist.remove(authenticationListener);
    }

    /**
     * handles signout from firebase service
     */
    public void SignOut(){
        mAuth.signOut();
    }


    public void SignIn(String email, String Password,
                       AuthenticationListener.Email callback)
            throws  IllegalArgumentException {

        throw new IllegalArgumentException(context.getString(R.string
                .sign_in_exception_message));
    }


    public void SendEmailVerification(FirebaseUser user)
            throws IllegalArgumentException {
        String message =  context.getString(R.string
                .send_email_not_implemented_provider_exception);

        throw new IllegalArgumentException(message);
    }

    //===========
    // properties
    //===========

    public Context getContext(){
        return this.context;
    }

    /**
     * gets {@link ProviderEnum.ProviderType ProviderType} property
     * according to current used provider
     * @return current provider type used by the current user
     */
    public ProviderEnum.ProviderType getProviderType() {
        return this.currentProviderType;
    }

    /**
     * sets {@link ProviderEnum.ProviderType ProviderType} property
     * according to current used provider
     */
    public void setProvederType(ProviderEnum.ProviderType providerType) {
        this.currentProviderType = providerType;
    }

    /**
     * gets current user {@link AuthCredential Credential}
     * @return current user credential
     */
    public AuthCredential getAuthCredential() {
        return this.credential;
    }

    /**
     * {@link AuthCredential AuthCredential} setter
     * @return
     */

    /**
     * sets {@link AuthCredential credential} property
     * according to current used provider
     */
    public void setAuthCredential(AuthCredential credential) {
        this.credential = credential;
    }

    /**
     * gets current {@link FirebaseUser FirebaseUser}
     * @return
     */
    public FirebaseUser getUser() {
        return this.user;
    }

    //=========================================================
    // abstract methods (private interface between all provider
    // derived classes
    //=========================================================

    public abstract void SignIn() throws InterruptedException;
    /**
     * signaling the derived provider that data has arrived from
     * private auth activity containing relevant authentication data
     * @param requestCode activity result requestCode
     * @param resultCode activity result resultCode
     * @param data activity result intent
     */
    public abstract void OnActivityResultReceived(int requestCode, int
            resultCode, Intent data);


    public void BroadcastOnComplete(FirebaseUser user){
        this.user = user;
        for (AuthenticationListener authenticationListener : authCallbacklist){
            authenticationListener.OnAuthenticationComplete(this.user);
        }
    }

    protected void HandleAuthProviderError(Exception ex)
    {
        ErrorHandlingManager.getInstace()
                .handle(this, ex);
    }

    public void BroadcastOnFailed(Exception ex) {
        this.user = null;
        for (AuthenticationListener authenticationListener : authCallbacklist) {
            authenticationListener.OnAuthenticationFailed(ex);
        }
    }

    public ProviderProperties getProviderProperties() {
        return providerProperties;
    }
}
