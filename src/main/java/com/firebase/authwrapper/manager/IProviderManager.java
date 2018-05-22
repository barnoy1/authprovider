package com.firebase.authwrapper.manager;

import com.firebase.authwrapper.providers.common.base.ProviderBase;
import com.firebase.authwrapper.providers.common.enums.ProviderType;
import com.firebase.authwrapper.providers.common.properties.ProviderProperties;
import com.firebase.authwrapper.providers.delegate.Provider;
import com.firebase.authwrapper.providers.types.IProvider;

/**
 * {@link ProviderManager ProviderManager} API exposed to users.
 *
 * @author ron barnoy
 * @version 1.0
 * @since 10-5-2018
 */
public interface IProviderManager {

     /**
      * Configure all available providers using curr_provider properties
      * {@link ProviderProperties providerProperties}
      * @param providerProperties target properties need for initialization of
      *                          all providers
      */
     void Configure (ProviderProperties providerProperties);

     /**
      * invoke current provider to sign out
      * @throws Exception this exception is thrown in case there the current
      * provider is not initialized
      */
     void SignOut() throws Exception;

     /**
      * Gets the matching {@link ProviderBase curr_provider } based on
      * {@link ProviderType ProviderType}
      * @param providerType the target curr_provider type
      * @return the matching {@link ProviderBase curr_provider }
      */
     IProvider getProvider(ProviderType providerType);

     /**
      * method for exposing the {@link Provider Provider}
      * object to client applications.
      * @return an {@link IProvider IProvider} which
      * attached to specific {@link ProviderBase
      * Provider } derived object based on
      * {@link ProviderType ProviderType} property
      */
     IProvider getProvider();

     /**
      * sets the current provider
      * @param provider a provider object reference
      */
     void setProvider(IProvider provider);

     /**
      * sets the current provider
      * @param providerProperties allocates new provider according to properties
      */
     void setProvider(ProviderProperties providerProperties);

     /**
      * sets current provider by supplying a {@link ProviderType providerType}
      * @param providerType allocates new provider according to
      *                     {@link ProviderType providerType enum value}
      */
     void setProvider(ProviderType providerType);
}
