package com.firebase.authwrapper.providers.delegate;

import android.content.Intent;

import com.firebase.authwrapper.providers.common.base.ProviderBase;
import com.firebase.authwrapper.providers.common.enums.ProviderType;
import com.firebase.authwrapper.providers.common.properties.ProviderProperties;
import com.firebase.authwrapper.providers.common.callbacks.AuthenticationListener;
import com.firebase.authwrapper.providers.common.factory.ProviderFactory;
import com.firebase.authwrapper.providers.types.IProvider;
import com.firebase.authwrapper.providers.types.MailProvider;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;

/**
 * the delegate is used for encapsulate methods and data which not
 * relevant to the user. The end users will be using
 * {@link IProvider IProvider} interface in order to interact
 * with the concrete provider.
 *
 * @author rbarnoy
 * @version 1.0
 * @see IProvider
 * @since 11-25-2017
 */
public class Provider implements IProvider {

    private IProvider provider = ProviderFactory.NULL_PROVIDER;

    /**
     * Ctor. returns new instance of concrete provider based on
     * current {@link ProviderProperties ProviderProperties} which
     * passed to this constructor
     * @param providerProperties current provider properties
     */
    public Provider(ProviderProperties providerProperties){

        this.provider =
                ProviderFactory.createAuthProvider(providerProperties);

    }

    /**
     * Sign out current provider
     * @see ProviderBase#SignOut()
     */
    @Override
    public void SignOut() {
        provider.SignOut();
    }

    /**
     * Sign in current provider
     * @throws InterruptedException
     * @see ProviderBase#SignIn()
     */
    @Override
    public void SignIn() throws InterruptedException {
        provider.SignIn();
    }

    /**
     * Sign in current provider by using mail signature provider
     * @param email user mail address
     * @param Password user password
     * @param emailCallback a {@link AuthenticationListener.Email listener} for
     *                      capturing
     * @throws IllegalArgumentException
     * @see ProviderBase#SignIn(String, String, AuthenticationListener.Email)
     */
    @Override
    public void SignIn(String email, String Password,
                       AuthenticationListener.Email emailCallback) throws
            IllegalArgumentException {
        provider.SignIn(email, Password, emailCallback);
    }

    /**
     * @param user target user for mail verification required
     *             for {@link MailProvider}
     * @throws IllegalArgumentException
     * @see ProviderBase#SendEmailVerification(FirebaseUser)
     */
    @Override
    public void SendEmailVerification(FirebaseUser user)
            throws IllegalArgumentException {
        provider.SendEmailVerification(user);
    }

    /**
     * Retrieves current firebase user
     * @return current {@link FirebaseUser FirebaseUser}
     * @see ProviderBase#getUser()
     */
    @Override
    public FirebaseUser getUser() {
        return provider.getUser();
    }

    /**
     * Retrieves current firebase user credential
     * @return current {@link AuthCredential FirebaseUser}
     * @see ProviderBase#getAuthCredential()
     */
    @Override
    public AuthCredential getAuthCredential() {
        return provider.getAuthCredential();
    }

    /**
     * Retrieves current provider type
     * @return current {@link ProviderType ProviderType}
     * @see ProviderBase#getProviderType()
     */
    @Override
    public ProviderType getProviderType() {
        return provider.getProviderType();
    }

    /**
     * Registers {@link AuthenticationListener AuthenticationListener} to
     * this provider delegate
     * @see ProviderBase#Register(AuthenticationListener)
     */
    @Override
    public void Register(AuthenticationListener authenticationListener) {
        provider.Register(authenticationListener);
    }

    /**
     * UnRegister {@link AuthenticationListener AuthenticationListener} to
     * this provider delegate
     * @see ProviderBase#UnRegister(AuthenticationListener)
     */
    @Override
    public void UnRegister(AuthenticationListener authenticationListener) {
        provider.UnRegister(authenticationListener);
    }

    /**
     * Notifies current provider that data has arrived from
     * private auth activity containing relevant authentication data
     * @param requestCode activity result requestCode
     * @param resultCode activity result resultCode
     * @param data activity result intent
     * @see ProviderBase#OnActivityResultReceived(int, int, Intent)
     */
    @Override
    public void OnActivityResultReceived(int requestCode, int resultCode, Intent data) {
        provider.OnActivityResultReceived(requestCode, resultCode, data);
    }

}
