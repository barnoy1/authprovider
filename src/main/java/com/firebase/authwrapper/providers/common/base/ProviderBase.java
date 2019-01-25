package com.firebase.authwrapper.providers.common.base;

import android.content.Context;
import android.content.Intent;

import com.firebase.authwrapper.manager.ProviderManager;
import com.firebase.authwrapper.providers.common.enums.ProviderType;
import com.firebase.authwrapper.providers.delegate.Provider;
import com.game.authprovider.R;
import com.firebase.authwrapper.providers.common.properties.ProviderProperties;
import com.firebase.authwrapper.utils.sharedprefrences.SharedPreferencesManager;
import com.firebase.authwrapper.providers.common.callbacks.AuthenticationListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.firebase.authwrapper.providers.types.*;
import java.util.ArrayList;

/**
 * <p>
 * This Class is the providers base class. Contains all mutual methods
 * (behaviors) for all concrete providers, including a base ctor. Apart of
 * implementing concrete methods, the ProviderBase declares several abstract
 * behaviors which needed implementation in derived providers.
 * </p>
 *
 *<p>
 * As an abstract class (not pure interface), this class implements some of
 * the functionality exposed to the {@link Provider provider delegate}. This
 * {@link Provider provider delegate}, invoked by the singleton {@link
 * ProviderManager ProviderManager} which exposed to the user.
 *</p>
 *
 * @author ron barnoy
 * @version 1.0
 * @since 10-5-2018
 * @see Provider
 */
public abstract class ProviderBase {


    protected FirebaseAuth mAuth;
    protected ProviderType currentProviderType;
    protected AuthCredential credential;
    protected FirebaseUser user;
    protected ArrayList<AuthenticationListener> authCallbacklist = new ArrayList<>();
    protected Context context;
    protected ProviderProperties providerProperties = null;

    private static final String MY_PREFS_NAME = "AuthSharedPrefs";

    /**
     * Abstract constructor for concrete provider object
     * @param providerProperties the provider properties needed for
     *                           initialization
     * @see ProviderProperties
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
     * Register a {@link AuthenticationListener listener} to the provider
     * class. The listener holds several callbacks which will be
     * reflected on the sender class.
     * @param authenticationListener listener for communicating the sender
     *                     to the providers flow
     * @see AuthenticationListener
     */
    public void Register(AuthenticationListener authenticationListener)
            throws IllegalArgumentException {
        if (authenticationListener == null)
        {
            throw new IllegalArgumentException(context.getString(R.string
                    .null_authentication_listener_exception));
        }
        authCallbacklist.add(authenticationListener);
    }

    /**
     * UnRegister {@link AuthenticationListener listener} from the provider.
     * The listener holds several callbacks which will be
     * reflected on the sender class.
     * @param authenticationListener listener for communicating the sender
     *                     to the providers flow.
     * @see AuthenticationListener
     */
    public void UnRegister(AuthenticationListener authenticationListener) {
        // remove from list
        authCallbacklist.remove(authenticationListener);
    }

    /**
     * This method signout the target provider from Firebase authentication
     * server.
     */
    public void SignOut(){
        mAuth.signOut();
    }


    /**
     * <p>
     * SignIn method using mail and password. Implemented only in {@link
     * MailProvider MailProvider}. The other concrete providers (except
     * {@link NullProvider NullProvider} throw this exception.
     *</p>
     *
     * <p>
     * The reason that this method is found in {@link ProviderBase ProviderBase}
     * (and not exclusively found in {@link MailProvider MailProvider}) is that
     * this method must be exposed to the end user. This can only be done if it
     * will be exposed to {@link Provider Provider delegate} object.
     * </p>
     *
     * @param email an email address string
     * @param Password an email password string
     * @param callback {@link AuthenticationListener.Email Email} callback
     *                                                           implemented
     *                                                           by the user
     *                                                           application
     *
     * @throws IllegalArgumentException this exception is triggered only if
     * other concrete provider (either of {@link MailProvider MailProvider}
     * called this method.
     * @see MailProvider
     */
    public void SignIn(String email, String Password,
                       AuthenticationListener.Email callback)
            throws  IllegalArgumentException {

        throw new IllegalArgumentException(context.getString(R.string
                .base_provider_signin_not_implemented_exception));
    }


    /**
     * <p>
     * This method is used for sending verification mail to the
     * {@link FirebaseUser current user}, after this user was successfully
     * registered by using {@link MailProvider MailProvider}
     * </p>
     *
     * <p>
     * The reason that this method is found in {@link ProviderBase ProviderBase}
     * (and not exclusively found in {@link MailProvider MailProvider}) is that
     * this method must be exposed to the end user. This can only be done if it
     * will be exposed to {@link Provider Provider delegate} object.
     * </p>
     * @param user the target user which will receive this verfication mail
     * @throws IllegalArgumentException this exception is triggered only if
     * other concrete provider (either of {@link MailProvider MailProvider}
     * called this method.
     * @see MailProvider
     *
     */
    public void SendEmailVerification(FirebaseUser user)
            throws IllegalArgumentException {
        String message =  context.getString(R.string
                .base_provider_send_email_not_implemented_exception);

        throw new IllegalArgumentException(message);
    }

    /**
     * Method for getting the target application context
     * @return application context
     * @see Context
     */
    public Context getContext(){
        return this.context;
    }

    /**
     * gets {@link ProviderType ProviderType} property
     * according to current used provider
     * @return current provider type used by the current user
     */
    public ProviderType getProviderType() {
        return this.currentProviderType;
    }

    /**
     * sets {@link ProviderType ProviderType} property
     * according to current used provider
     */
    public void setProvederType(ProviderType providerType) {
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
     * sets {@link AuthCredential credential} property
     * according to current used provider
     */
    public void setAuthCredential(AuthCredential credential) {
        this.credential = credential;
    }

    /**
     * gets current {@link FirebaseUser FirebaseUser}
     * @return current {@link FirebaseUser FirebaseUser}
     */
    public FirebaseUser getUser() {
        return this.user;
    }


    /**
     * method all relevant providers are requested to override
     * except {@link NullProvider NullProvider} and
     * {@link MailProvider MailProvider}
     * @throws InterruptedException the exception which will be caught by the
     * {@link ErrorHandlingManager ErrorHandlingManager} on concrete providers
     */
    public void SignIn() throws InterruptedException {

        throw new IllegalArgumentException
                (context.getString(R.string.base_provider_signin_not_implemented_exception));
    }


    /**
     * Notifying the derived provider that data has arrived from
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

    /**
     * Handle Errors that were caught during authentication process
     * @param ex the exception that was caught
     */
    protected void HandleAuthProviderError(Exception ex)
    {
        ErrorHandlingManager.getInstace()
                .handle(this, ex);
    }

    /**
     * retrieve provider's {@link ProviderProperties ProviderProperties}
     * @return target provider ProviderProperties
     * @see ProviderProperties
     */
    public ProviderProperties getProviderProperties() {
        return providerProperties;
    }
}
