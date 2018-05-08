package com.firebase.authwrapper.providers.common.enums;

/**
 * Created by ron on 11/14/2017.
 */



public class ProviderEnum {

    public enum ProviderType {
        UNDEFINED,
        MAIL,
        GOOGLE,
        FACEBOOK;
    }

    public enum AuthStatus {
        UNDEFINED,
        ACTIVE,
        INACTIVE;
    }

}
