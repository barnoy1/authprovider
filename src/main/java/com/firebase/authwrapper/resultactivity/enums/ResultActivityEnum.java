package com.firebase.authwrapper.resultactivity.enums;

import com.firebase.authwrapper.providers.common.base.ProviderBase;
import com.firebase.authwrapper.resultactivity.common.BaseResultActivity;

/**
 * This class contains all enums relevant for result activities
 *
 * @author ron barnoy
 * @version 1.0
 * @since 10-5-2018
 * @see BaseResultActivity
 */
public class ResultActivityEnum {

    /**
     * The type of the action required to be performed in onCreate method
     * when the activity class is initialized.
     */
    public enum ActionType {
        /**
         * Initiate SignIn authentication procedure
         */
        SIGN_IN,

        /**
         * Initiate SignOut authentication procedure
         */
        SIGN_OUT,
    }
}
