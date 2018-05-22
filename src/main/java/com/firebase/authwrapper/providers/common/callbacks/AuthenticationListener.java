package com.firebase.authwrapper.providers.common.callbacks;

import com.google.firebase.auth.FirebaseUser;

/**
 * This listener notifies the target application
 * the result of the authentication process
 *
 * @author ron barnoy
 * @version 1.0
 * @since 10-5-2018
 */
public interface AuthenticationListener {
    /**
     * Callback method which triggered when a provider was
     * successful authenticate user. The callback returns to
     * the sender the details of the authenticated user for
     * updating UI
     *
     * @param user the authenticated user
     */
    void OnAuthenticationComplete(FirebaseUser user);

    /**
     * a callback notification for the sender when the provider
     * failed to authenticate the user
     */
    void OnAuthenticationFailed(Exception ex);

    /**
     * This listener contains additional status methods of
     * the authentication process which relevant for
     * {@link com.firebase.authwrapper.providers.types.MailProvider
     * MailProvider}
     */
    interface Email {

        /**
         * create new {@link FirebaseUser FirebaseUser} by using
         * {@link com.firebase.authwrapper.providers.types.MailProvider
         * MailProvider} provider
         * @param user current FirebaseUser user instance
         * @see FirebaseUser
         */
        void OnNewUserCreated(FirebaseUser user);

        /**
         * this method is inovked when the
         * {@link com.firebase.authwrapper.providers.types.MailProvider
         * MailProvider} successfully been able to send a verfication mail
         * to the target {@link FirebaseUser user}
         * @see FirebaseUser
         */
        void OnVerficationMailSent();

    }
}
