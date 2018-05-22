package com.firebase.authwrapper.resultactivity.types.facebook;

import com.facebook.login.LoginManager;
import com.firebase.authwrapper.providers.types.FacebookProvider;
import com.firebase.authwrapper.resultactivity.common.ActivityInvoker;
import com.firebase.authwrapper.resultactivity.common.BaseResultActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This class is the facebook implementation of class {@link ActivityInvoker
 * ActivityInvoker} object. It contains method and properties relevant for
 * authentication by using Facebook API.
 * </p>
 * <p>
 * This singleton class is used for binding the
 * {@link FacebookProvider FacebookProvider} to
 * {@link FacebookResultActivity FacebookResultActivity}.
 * </p>
 *
 * @author ron barnoy
 * @version 1.0
 * @since 10-5-2018
 * @see ActivityInvoker
 */
public class FacebookActivityInvoker extends ActivityInvoker {

    private FacebookActivityInvoker() {}

    private static FacebookActivityInvoker facebookActivityInoker = new
            FacebookActivityInvoker();

    private LoginManager loginManager;
    private List<String> permissionsList = new ArrayList<>();

    /**
     * Getter of this class singleton instance
     * @return the single instance of this class
     */
    public static FacebookActivityInvoker getInstance(){
        return facebookActivityInoker;
    }

    /**
     * Getter of facebook permissionList
     * @return facebook permission list object for a specific firebase user
     */
    public List<String> getPermissionsList() {
        return permissionsList;
    }

    /**
     * Setter of facebook permissionList
     * @param permissionsList sets the user target permission
     */
    public void setPermissionsList(List<String> permissionsList) {
        this.permissionsList = permissionsList;
    }

    /**
     *  Setter of facebook loginManager
     * @param loginManager sets this class LoginManager object.
     */
    public void setLoginManager(LoginManager loginManager){
        this.loginManager = loginManager;
    }

    /**
     * Getter of facebook LoginManager
     * @return facebook LoginManager object
     */
    public  LoginManager getLoginManager(){
        return this.loginManager;
    }

}
