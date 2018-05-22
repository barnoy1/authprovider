package com.firebase.authwrapper.providers.common.factory;

import com.firebase.authwrapper.providers.common.enums.ProviderType;
import com.firebase.authwrapper.providers.common.properties.ProviderProperties;
import com.firebase.authwrapper.providers.types.FacebookProvider;
import com.firebase.authwrapper.providers.types.GoogleProvider;
import com.firebase.authwrapper.providers.types.IProvider;
import com.firebase.authwrapper.providers.types.MailProvider;
import com.firebase.authwrapper.providers.types.NullProvider;

/**
 * Factory class for creating new instances of different provider
 * types which are specified in {@link ProviderType ProviderType}.
 * The factory class contain a factory method
 * {@link ProviderFactory#createAuthProvider(ProviderProperties)
 * createAuthProvider(ProviderProperties)} which takes as an input
 * the {@link ProviderProperties ProviderProperties} assigned by the
 * user and returns a concrete provider based on this properties. The
 * newly provider instance is exposed by {@link IProvider IProvider}
 * Interface
 *
 * @author rbarnoy
 * @version 1.0
 * @see IProvider
 * @since 11-25-2017
 */
public class ProviderFactory {

    /**
     * Factory method for creating new Providers instance
     * base on selected properties. Returns a concrete provider
     * exposed only {@link IProvider IProvider} interface
     * @param providerProperties input provider properties which
     *                           are necessary for initialize this
     *                           specific provider
     * @return a new instance of any of the concrete
     * providers which exposed by {@link IProvider IProvider} interface
     * @throws NullPointerException
     */
    public static IProvider createAuthProvider
            (ProviderProperties providerProperties) throws
            NullPointerException {

        // pulls provider type from providerProperties
        ProviderType providerType =
                providerProperties.getProviderType();

        switch (providerType){
            case MAIL:
                return new MailProvider(providerProperties);
            case GOOGLE:
                return new GoogleProvider(providerProperties);
            case FACEBOOK:
                return new FacebookProvider(providerProperties);
            default:
                return NULL_PROVIDER;
        }
    }

    /**
     * Null provider static instance. Default provider when providers
     * are not initialized.
     */
    public static IProvider NULL_PROVIDER = new NullProvider(null){

    };
}
