package com.firebase.authwrapper.manager;

import android.content.Context;
import android.util.Log;

import com.firebase.authwrapper.providers.common.enums.ProviderType;
import com.game.authprovider.R;
import com.firebase.authwrapper.providers.delegate.Provider;
import com.firebase.authwrapper.providers.common.properties.ProviderProperties;
import com.firebase.authwrapper.providers.common.base.ProviderBase;
import com.firebase.authwrapper.providers.types.IProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * this class is a singleton used for wrapping the provider delegate
 * data in case users sent this application to background. It allows
 * the user to chose a current provider from a list of avaliable providers.
 *
 * @author ron barnoy
 * @version 1.0
 * @since 10-5-2018
 * @see IProviderManager
 */
public class ProviderManager implements IProviderManager {

    private static ProviderManager authManager = new ProviderManager();
    private final static String TAG = ProviderManager.class.getSimpleName();

    private IProvider provider = null;
    private ProviderProperties providerPropertiesConfig;
    private List<IProvider> providerList = new
            ArrayList<IProvider>();

    /**
     * A private ctor prevents any other
     * class from instantiating.
     */
    private ProviderManager() {};

    /**
     * method for returning the static instance of the singleton
     * auth manager
     * @return the private static instance of this class
     */
    public static ProviderManager getInstace(){
        return authManager;
    }

    /**
     * Configure all available providers using
     * {@link ProviderProperties providerProperties}
     * @param providerProperties
     */
    public void Configure (ProviderProperties providerProperties){
        providerPropertiesConfig = providerProperties;

        for (ProviderType providerType : ProviderType.values()) {

            if (providerType == ProviderType.UNDEFINED)
                   // || providerType == ProviderEnum.ProviderType.MAIL)
                continue;


            ProviderProperties currentAuthProviderConfigurations = new
                    ProviderProperties.AuthProviderPropertiesBuilder()
                    .targetActivity(providerPropertiesConfig.getTargetActivity())
                    .providerType(providerType)
                    .build();

            IProvider provider = new Provider
                    (currentAuthProviderConfigurations);
            provider.SignOut();
            providerList.add(provider);
        }
    }

    /**
     * Sign out current provider
     */
    public void SignOut()
    {
        this.provider.SignOut();
    }
    public IProvider getProvider(ProviderType providerType)
    {
        for (IProvider provider : providerList) {

            boolean found = providerType.equals(provider
                    .getProviderType());

            if (found){
                return provider;
            }
        }
        return null;
    }
    /**
     * method for exposing the {@link Provider Provider}
     * object to client applications.
     * @return an {@link IProvider IProvider} which
     * attached to specific {@link ProviderBase
     * Provider } derived object based on
     * {@link ProviderType ProviderType} property
     */
    @Override
    public IProvider getProvider() {
        if (provider == null){
            Context context = providerPropertiesConfig.getTargetActivity();
            String message = context
                    .getString(R.string.provider_manager_provider_exception);

            Log.e(TAG, message);
            throw new NullPointerException(message);
        }
        return this.provider;
    }

    /**
     * sets the current provider
     * @param provider a provider object referance
     */
    @Override
    public void setProvider(IProvider provider){
        this.provider = provider;
    }

    /**
     * sets the current provider
     * @param providerProperties allocates new provider according to properties
     */
    @Override
    public void setProvider(ProviderProperties providerProperties){
        this.provider = new Provider(providerProperties);
    }

    /**
     * sets current provider by supplying a {@link ProviderType providerType}
     * @param providerType allocates new provider according to
     *                     {@link ProviderType providerType enum value}
     */
    @Override
    public void setProvider(ProviderType providerType){

        ProviderProperties currentAuthProviderConfigurations = new
                ProviderProperties.AuthProviderPropertiesBuilder()
                .targetActivity(providerPropertiesConfig.getTargetActivity())
                .providerType(providerType)
                .build();

        this.provider = new Provider(currentAuthProviderConfigurations);
    }

}
