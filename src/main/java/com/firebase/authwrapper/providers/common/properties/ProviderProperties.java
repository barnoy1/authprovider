package com.firebase.authwrapper.providers.common.properties;

import android.support.v7.app.AppCompatActivity;

import com.firebase.authwrapper.providers.common.enums.ProviderType;
import com.firebase.authwrapper.providers.common.factory.ProviderFactory;
import com.firebase.authwrapper.providers.types.IProvider;

/**
 * <p>
 * This Properties class hold all the properties needed for
 * initialize and define instances of different {@link IProvider
 * Provider}
 *</p>
 *
 * <p>
 * The properties are needed when creating new providers instance
 * using the factory method
 * {@link ProviderFactory#createAuthProvider(ProviderProperties)
 * createAuthProvider} in order to build all different provider,
 * each one with different configuration.
 * </p>
 *
 * @author rbarnoy
 * @version 1.0
 * @see ProviderFactory
 * @since 11-25-2017
 */
public class ProviderProperties {

    /**
     * ProviderProperties ctor based on
     * {@link AuthProviderPropertiesBuilder AuthProviderPropertiesBuilder}
     * @param builder configured input builder
     */
    public ProviderProperties(AuthProviderPropertiesBuilder builder){

        // global provider settings
        this.providerType = builder.providerType;

        // google provider settings
        this.targetActivity = builder.targetActivity;
    }

    //==============
    //private fields
    //==============

    //need for factory in order to create target provider
    private ProviderType providerType;

    //need by google provider
    private AppCompatActivity targetActivity;

    //======
    //getters
    //======

    /**
     * method for returning the {@link ProviderType ProviderType}
     * property.
     * @return current provider type
     */
    public ProviderType getProviderType(){
        return this.providerType;
    }

    /**
     * method for returning the {@link AppCompatActivity ProviderType}
     * property.
     * @return current provider type
     */
    public AppCompatActivity getTargetActivity(){
        return this.targetActivity;
    }


    //======
    //builder
    //======

    /**
     * builder class of provider properties
     */
    public static class AuthProviderPropertiesBuilder{

        /**
         * building {@link ProviderProperties ProviderProperties }
         * based on builder settings.
         * @return new instance of ProviderProperties
         */
        public ProviderProperties build(){
            return new ProviderProperties(this);
        }

        //===============
        // default fields
        //===============

        // base provider properties
        private ProviderType providerType =
                ProviderType.UNDEFINED;

        //google provider specific properties
        private AppCompatActivity targetActivity;

        //================
        // builder setters
        //================

        /**
         * method for setting the
         * {@link ProviderType ProviderType} property.
         * @param providerType the type of the provider that will be
         *                     generated in
         *                     {@link ProviderFactory ProviderFactory}
         * @return this builder
         */
        public AuthProviderPropertiesBuilder providerType
                (ProviderType providerType){
            this.providerType = providerType;
            return this;
        }

        /**
         * method for setting the
         * {@link AppCompatActivity targetActivity} property.
         * @param targetActivity the type of the provider that will be
         *                     generated in
         *                     {@link ProviderFactory ProviderFactory}
         * @return this builder
         */
        public AuthProviderPropertiesBuilder targetActivity
        (AppCompatActivity targetActivity){
            this.targetActivity = targetActivity;
            return this;
        }

    }
}
