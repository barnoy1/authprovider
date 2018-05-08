package com.firebase.authwrapper.resultactivity.types.facebook;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.facebook.login.LoginManager;
import com.game.authprovider.R;
import com.firebase.authwrapper.resultactivity.common.BaseResultActivity;
import com.firebase.authwrapper.resultactivity.enums.ResultActivityEnum;

import java.util.List;

public class FacebookResultActivity extends BaseResultActivity {

    private ResultActivityEnum.ActionType actionType;

    public static Activity fa;

    private final static String TAG = FacebookResultActivity.class
            .getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fa =  this;
        BaseResultActivity.setActivityInvoker
                (FacebookActivityInoker.getInstance());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String ActionTypePropertyName = context.getString(R.string
                    .activity_action_type);
            int actionId = extras.getInt(ActionTypePropertyName);
            actionType = ResultActivityEnum.ActionType.values()[actionId];
        }

        switch (actionType){
            case SIGN_IN:
                SignIn();
				break;
				
            case SIGN_OUT:

				break;
        }

    }

    private void SignIn()
    {

        this.loadingMessage =
                this.getResources().getString(R.string.signin_facebook_message);
        showProgressDialog();

        LoginManager loginManager =
                FacebookActivityInoker.getInstance()
                .getLoginManager();

        List<String> permissionsList =
                FacebookActivityInoker.getInstance().getPermissionsList();

        loginManager.logInWithReadPermissions(
                this,
                permissionsList);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
