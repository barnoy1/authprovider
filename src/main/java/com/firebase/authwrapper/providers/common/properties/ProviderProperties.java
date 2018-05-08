package com.firebase.authwrapper.providers.common.properties;

import android.support.v7.app.AppCompatActivity;

import com.firebase.authwrapper.providers.common.enums.ProviderEnum;
import com.firebase.authwrapper.providers.common.factory.ProviderFactory;

/**
 * Properties class with a builder. A builder is need
 * for
 *
 * {@link ProviderFactory#createAuthProvider(ProviderProperties)
 * createAuthProvider} in order to build all different provider,
 * each one with different configuration.
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
    private ProviderEnum.ProviderType providerType;

    //need by google provider
    private AppCompatActivity targetActivity;

    //======
    //getters
    //======

    /**
     * method for returning the {@link ProviderEnum.ProviderType ProviderType}
     * property.
     * @return current provider type
     */
    public ProviderEnum.ProviderType getProviderType(){
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
        private ProviderEnum.ProviderType providerType =
                ProviderEnum.ProviderType.UNDEFINED;

        //google provider specific properties
        private AppCompatActivity targetActivity;

        //================
        // builder setters
        //================

        /**
         * method for setting the
         * {@link ProviderEnum.ProviderType ProviderType} property.
         * @param providerType the type of the provider that will be
         *                     generated in
         *                     {@link ProviderFactory ProviderFactory}
         * @return this builder
         */
        public AuthProviderPropertiesBuilder providerType
                (ProviderEnum.ProviderType providerType){
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
