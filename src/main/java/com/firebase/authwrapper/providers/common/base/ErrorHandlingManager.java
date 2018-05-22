package com.firebase.authwrapper.providers.common.base;

import com.firebase.authwrapper.providers.common.callbacks
        .AuthenticationListener;

/**
 * This Class is used for handling Exception raised in the library. It catches
 * the exceptions of provider classes and pass it to
 * client application. The ErrorHandlerManager invokes
 *
 *
 * {@link com.firebase.authwrapper.providers.common.base.ErrorHandlingManager#BroadcastOnFailed(Exception)
 * BroadcastOnFailed } method of the target provider which triggers
 * {@link AuthenticationListener#OnAuthenticationFailed  OnAuthenticationFailed}
 * method.
 *
 * @author ron barnoy
 * @version 1.0
 * @see AuthenticationListener#OnAuthenticationFailed
 * @since 10-5-2018
 */
public class ErrorHandlingManager {

    private ErrorHandlingManager() {};
    private static ErrorHandlingManager errorHandler = new ErrorHandlingManager();

    private ProviderBase provider;

    /**
     * Retrieves the error handler singleton object
     * @return handler singleton singleton
     */
    public static ErrorHandlingManager getInstace(){
        return errorHandler;
    }

    /**
     * invoke the error
     * @param provider target provider
     * @param ex the exception which was caught
     * @see ProviderBase
     */
    public void handle(ProviderBase provider, Exception ex)
    {
        this.provider = provider;
        BroadcastOnFailed(ex);
    }

    /**
     * Notifying all registered application targets by triggering
     * {@link AuthenticationListener#OnAuthenticationFailed(Exception)
     * OnAuthenticationFailed(Exception)} callback
     * @param ex the expection that was caught
     */
    private void BroadcastOnFailed(Exception ex) {

        for (AuthenticationListener authenticationListener
                : this.provider.authCallbacklist) {
            authenticationListener.OnAuthenticationFailed(ex);
        }
    }

}
