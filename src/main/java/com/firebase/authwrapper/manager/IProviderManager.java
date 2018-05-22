package com.firebase.authwrapper.manager;

import com.firebase.authwrapper.providers.common.enums.ProviderType;
import com.firebase.authwrapper.providers.common.properties.ProviderProperties;
import com.firebase.authwrapper.providers.types.IProvider;

/**
 * {@link ProviderManager ProviderManager} API exposed to users.
 *
 * @author ron barnoy
 * @version 1.0
 * @since 10-5-2018
 */
public interface IProviderManager {

     IProvider getProvider();
     void setProvider(ProviderType providerType);
     void setProvider(ProviderProperties providerProperties);
     void setProvider(IProvider authProvider);
}
