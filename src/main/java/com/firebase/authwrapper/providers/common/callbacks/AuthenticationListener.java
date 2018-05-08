package com.firebase.authwrapper.providers.common.callbacks;

import com.google.firebase.auth.FirebaseUser;

/**
 * listener class which contains different callback
 * of providers flow.
 */
public interface AuthenticationListener {
    /**
     * callback function which triggered when a provider was
     * successfull authenticate user. The callback returns to
     * the sender the details of the autheticated user for
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

    interface Email {

        void OnNewUserCreated(FirebaseUser user);

        void OnVerficationMailSent();

    }
}
