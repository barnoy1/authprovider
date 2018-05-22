package com.firebase.authwrapper.resultactivity.types.google;

import com.firebase.authwrapper.providers.types.GoogleProvider;
import com.firebase.authwrapper.resultactivity.common.ActivityInvoker;
import com.google.android.gms.common.api.GoogleApiClient;


/**
 * <p>
 * This class is the facebook implementation of class {@link ActivityInvoker
 * ActivityInvoker} object. It contains method and properties relevant for
 * authentication by using Google API.
 * </p>
 * <p>
 * This singleton class is used for binding the
 * {@link GoogleProvider FacebookProvider} to
 * {@link GoogleResultActivity FacebookResultActivity}.
 * </p>
 *
 * @author ron barnoy
 * @version 1.0
 * @since 10-5-2018
 * @see ActivityInvoker
 */
public class GoogleActivityInvoker extends ActivityInvoker {
    private GoogleApiClient googleApiClient;


    private GoogleActivityInvoker() {}


    private static GoogleActivityInvoker googleActivityInvoker = new
            GoogleActivityInvoker();

    /**
     * Getter of this class singleton instance
     * @return the single instance of this class
     */
    public static GoogleActivityInvoker getInstance(){
        return googleActivityInvoker;
    }

    /**
     *  Setter of GoogleApiClient
     * @param googleApiClient sets this class GoogleApiClient object.
     */
    public void setGoogleApiClient(GoogleApiClient googleApiClient){
        this.googleApiClient = googleApiClient;
    }

    /**
     * Getter of google GoogleApiClient
     * @return google GoogleApiClient object
     */
    public GoogleApiClient getGoogleApiClient(){
        return this.googleApiClient;
    }
}
