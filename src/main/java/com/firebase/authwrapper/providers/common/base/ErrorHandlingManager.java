package com.firebase.authwrapper.providers.common.base;

/**
 * Created by ron on 3/28/2018.
 */

public class ErrorHandlingManager {

    private ErrorHandlingManager() {};
    private static ErrorHandlingManager errorHandler = new ErrorHandlingManager();

    public static ErrorHandlingManager getInstace(){
        return errorHandler;
    }

    public void handle(ProviderBase provider, Exception ex)
    {
        provider.BroadcastOnFailed(ex);
    }

}
