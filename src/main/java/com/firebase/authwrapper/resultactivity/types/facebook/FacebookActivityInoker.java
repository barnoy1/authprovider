package com.firebase.authwrapper.resultactivity.types.facebook;

import com.facebook.login.LoginManager;
import com.firebase.authwrapper.resultactivity.common.ActivityInvoker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ron on 1/5/2018.
 */

public class FacebookActivityInoker extends ActivityInvoker {

    private FacebookActivityInoker() {}

    private static FacebookActivityInoker facebookActivityInoker = new
            FacebookActivityInoker();

    private LoginManager loginManager;
    private List<String> permissionsList = new ArrayList<>();

    public static FacebookActivityInoker getInstance(){
        return facebookActivityInoker;
    }
//
//    public void register(final FacebookProvider provider){
//        super.register(provider);
//    }

    public List<String> getPermissionsList() {
        return permissionsList;
    }

    public void setPermissionsList(List<String> permissionsList) {
        this.permissionsList = permissionsList;
    }

    public void setLoginManager(LoginManager loginManager){
        this.loginManager = loginManager;
    }

    public  LoginManager getLoginManager(){
        return this.loginManager;
    }

}
