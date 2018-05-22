package com.firebase.authwrapper.resultactivity.types.facebook;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.facebook.login.LoginManager;
import com.firebase.authwrapper.providers.types.FacebookProvider;
import com.game.authprovider.R;
import com.firebase.authwrapper.resultactivity.common.BaseResultActivity;
import com.firebase.authwrapper.resultactivity.enums.ResultActivityEnum;

import java.util.List;

/**
 * <p>
 * This class is the google result activity implementation of
 * {@link BaseResultActivity BaseResultActivity}. This class holds all the
 * relevant method required to authenticate user via facebook API and error
 * handling.
 * </p>
 * <p>
 * The type of action to be (SignIn/SignOut) is determine by the target
 * {@link FacebookProvider FacebookProvider}. In OnCreate method this data is
 * extracted by reading the activity result bundle. Based on the selected
 * option, this activity will invoke one of the two actions and passed the
 * activity result to the target provider by
 * {@link FacebookActivityInvoker FacebookActivityInvoker}
 * </p>
 *
 * @author ron barnoy
 * @version 1.0
 * @since 10-5-2018
 * @see BaseResultActivity
 * @see FacebookProvider
 * @see FacebookActivityInvoker
 */
public class FacebookResultActivity extends BaseResultActivity {

    private ResultActivityEnum.ActionType actionType;

    /**
     * public instance of this activity so it can be called and destroyed
     * after onActivityResult successfully arrived to the {@link
     * FacebookProvider FacebookProvider}
     */
    public static Activity fa;

    private final static String TAG = FacebookResultActivity.class
            .getSimpleName();


    /**
     * OnCreate implementation of the google result activity. In this stage
     * the desired action determined in the target provider is being extracted
     * by {@link FacebookActivityInvoker GoogleActivityInvoker} and executed
     * @param savedInstanceState the bundle contains data which is transferred
     *                          from {@link FacebookProvider GoogleProvider}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fa =  this;
        BaseResultActivity.setActivityInvoker
                (FacebookActivityInvoker.getInstance());

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
                FacebookActivityInvoker.getInstance()
                .getLoginManager();

        List<String> permissionsList =
                FacebookActivityInvoker.getInstance().getPermissionsList();

        loginManager.logInWithReadPermissions(
                this,
                permissionsList);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
