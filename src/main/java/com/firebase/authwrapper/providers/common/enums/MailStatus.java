package com.firebase.authwrapper.providers.common.enums;

/**
 *
 * This class holds enums which relevant to {@link
 * com.firebase.authwrapper.providers.types.MailProvider
 * MailProvider}
 *
 * @author ron barnoy
 * @version 1.0
 * @since 10-5-2018
 */

/**
 * Enum for stating the mail provider state machine
 *
 */
public enum MailStatus {
    /**
     * create new mail user
     */
    CREATE_NEW_USER,

    /**
     * wait for user verfication mail to be received
     */
    WAIT_FOR_VERFICATION,

    /**
     * user has been found in firebase database
     */
    AUTHERIZED_USER

}