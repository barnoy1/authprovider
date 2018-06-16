package com.firebase.authwrapper.manager;

import android.content.Context;
import android.util.Log;

import com.firebase.authwrapper.providers.common.enums.ProviderType;
import com.firebase.authwrapper.providers.types.NullProvider;
import com.game.authprovider.R;
import com.firebase.authwrapper.providers.delegate.Provider;
import com.firebase.authwrapper.providers.common.properties.ProviderProperties;
import com.firebase.authwrapper.providers.common.base.ProviderBase;
import com.firebase.authwrapper.providers.types.IProvider;

import java.util.ArrayList;
import java.util.List;

import static com.firebase.authwrapper.providers.common.factory.ProviderFactory.NULL_PROVIDER;

/**
 * this class is a singleton used for wrapping the curr_provider delegate
 * data in case users sent this application to background. It allows
 * the user to chose a current curr_provider from a list of available providers.
 *
 * @author ron barnoy
 * @version 1.0
 * @since 10-5-2018
 * @see IProviderManager
 */
public class ProviderManager implements IProviderManager {

    private static ProviderManager providerManager = new ProviderManager();
    private final static String TAG = ProviderManager.class.getSimpleName();

    private IProvider curr_provider = NULL_PROVIDER;
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
        return providerManager;
    }

    /**
     * Configure all available providers using curr_provider properties
     * {@link ProviderProperties providerProperties}
     * @param providerProperties target properties need for initialization of
     *                          all providers
     */
    @Override
    public void Configure (ProviderProperties providerProperties){
        providerPropertiesConfig = providerProperties;

        for (ProviderType providerType : ProviderType.values()) {

            if (providerType == ProviderType.UNDEFINED)
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

        if (!providerList.isEmpty()){
           this.curr_provider =  providerList.get(0);
        }
    }

    /**
     * Sign out current provider
     */
    @Override
    public void SignOut() throws Exception {
        if (this.curr_provider == null){

            Context context = providerPropertiesConfig.getTargetActivity();
            String message = context
                    .getString(R.string. null_provider_error);
            throw new Exception(message);
        }


        this.curr_provider.SignOut();
    }

    /**
     * Gets the matching {@link ProviderBase curr_provider } based on
     * {@link ProviderType ProviderType}
     * @param providerType the target curr_provider type
     * @return the matching {@link ProviderBase curr_provider }
     */
    @Override
    public IProvider getProvider(ProviderType providerType)
    {
        for (IProvider provider : providerList) {

            boolean found = providerType.equals(provider
                    .getProviderType());

            if (found){
                this.curr_provider = provider;
            }
        }

        return this.curr_provider;
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
        if (curr_provider == null){
            Context context = providerPropertiesConfig.getTargetActivity();
            String message = context
                    .getString(R.string.provider_manager_provider_exception);

            Log.e(TAG, message);
            throw new NullPointerException(message);
        }
        return this.curr_provider;
    }

    /**
     * sets the current provider
     * @param provider a provider object reference
     */
    @Override
    public void setProvider(IProvider provider){
        this.curr_provider = provider;
    }

    /**
     * sets the current provider
     * @param providerProperties allocates new provider according to properties
     */
    @Override
    public void setProvider(ProviderProperties providerProperties){
        this.curr_provider = new Provider(providerProperties);
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

        this.curr_provider = new Provider(currentAuthProviderConfigurations);
    }

    /**
     * @return the provider manager configuration state. If the
     * configuration procedure was successfully executed - this method will
     * return True. Otherwise, False.
     */
    @Override
    public boolean IsConfigured() {
        return !(providerList.isEmpty());
    }

}
