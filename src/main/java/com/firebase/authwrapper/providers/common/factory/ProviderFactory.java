package com.firebase.authwrapper.providers.common.factory;

import com.firebase.authwrapper.providers.common.properties.ProviderProperties;
import com.firebase.authwrapper.providers.common.enums.ProviderEnum;
import com.firebase.authwrapper.providers.common.base.ProviderBase;
import com.firebase.authwrapper.providers.types.FacebookProvider;
import com.firebase.authwrapper.providers.types.GoogleProvider;
import com.firebase.authwrapper.providers.types.IProvider;
import com.firebase.authwrapper.providers.types.MailProvider;
import com.firebase.authwrapper.providers.types.NullProvider;

/**
 * @author rbarnoy
 * @version 1.0
 * @see ProviderBase#ProviderBase(ProviderProperties)
 * @since 11-25-2017
 */
public class ProviderFactory {


    public static IProvider createAuthProvider
            (ProviderProperties providerProperties) throws
            NullPointerException {

        // pulls provider type from providerProperties
        ProviderEnum.ProviderType providerType =
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

    public static IProvider NULL_PROVIDER = new NullProvider(null){

    };
}
