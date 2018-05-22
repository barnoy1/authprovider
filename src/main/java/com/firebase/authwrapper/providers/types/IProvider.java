package com.firebase.authwrapper.providers.types;

import android.content.Intent;

import com.firebase.authwrapper.providers.common.base.ProviderBase;
import com.firebase.authwrapper.providers.common.callbacks.AuthenticationListener;
import com.firebase.authwrapper.providers.common.enums.ProviderType;
import com.firebase.authwrapper.providers.delegate.Provider;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;

/**
 * This class is an interface which exposed to the end user
 * by the implemented provider delegate object. The delegate
 * provider object encapsulates the concrete provider.
 *
 * @author ron barnoy
 * @version 1.0
 * @since 10-5-2018
 * @see Provider
 */
public interface IProvider {

    /**
     * Sign out current provider
     * @see ProviderBase#SignOut()
     */
    void SignOut();

    /**
     * Sign in current provider
     * @throws InterruptedException
     * @see ProviderBase#SignIn()
     */
    void SignIn() throws InterruptedException;

    /**
     * Sign in current provider by using mail signature provider
     * @param email user mail address
     * @param Password user password
     * @param emailCallback a {@link AuthenticationListener.Email listener} for
     *                      capturing
     * @throws IllegalArgumentException
     * @see ProviderBase#SignIn(String, String, AuthenticationListener.Email)
     */
    void SignIn(String email, String Password, AuthenticationListener.Email
            emailCallback) throws IllegalArgumentException;

    /**
     * @param user target user for mail verification required
     *             for {@link MailProvider}
     * @throws IllegalArgumentException
     * @see ProviderBase#SendEmailVerification(FirebaseUser)
     */
    void SendEmailVerification(FirebaseUser user) throws
            IllegalArgumentException;;

    /**
     * Registers {@link AuthenticationListener AuthenticationListener} to
     * this provider delegate
     * @see ProviderBase#Register(AuthenticationListener)
     */
    void Register(AuthenticationListener authenticationListener);

    /**
     * UnRegister {@link AuthenticationListener AuthenticationListener} to
     * this provider delegate
     * @see ProviderBase#UnRegister(AuthenticationListener)
     */
    void UnRegister(AuthenticationListener authenticationListener);

    /**
     * Notifies current provider that data has arrived from
     * private auth activity containing relevant authentication data
     * @param requestCode activity result requestCode
     * @param resultCode activity result resultCode
     * @param data activity result intent
     * @see ProviderBase#OnActivityResultReceived(int, int, Intent)
     */
    void OnActivityResultReceived(int requestCode, int resultCode, Intent data);

    /**
     * Retrieves current firebase user
     * @return current {@link FirebaseUser FirebaseUser}
     * @see ProviderBase#getUser()
     */
    FirebaseUser getUser();

    /**
     * Retrieves current firebase user credential
     * @return current {@link AuthCredential FirebaseUser}
     * @see ProviderBase#getAuthCredential()
     */
    AuthCredential getAuthCredential();

    /**
     * Retrieves current provider type
     * @return current {@link ProviderType ProviderType}
     * @see ProviderBase#getProviderType()
     */
    ProviderType getProviderType();

}
