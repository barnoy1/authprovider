package com.firebase.authwrapper.manager;

import com.firebase.authwrapper.providers.common.properties.ProviderProperties;
import com.firebase.authwrapper.providers.common.enums.ProviderEnum;
import com.firebase.authwrapper.providers.types.IProvider;

/**
 * {@link ProviderManager ProviderManager} API exposed to users.
 */
public interface IProviderManager {

     IProvider getProvider();
     void setProvider(ProviderEnum.ProviderType providerType);
     void setProvider(ProviderProperties providerProperties);
     void setProvider(IProvider authProvider);
}
