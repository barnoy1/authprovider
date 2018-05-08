package com.firebase.authwrapper.providers.types;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.game.authprovider.R;
import com.firebase.authwrapper.resultactivity.types.facebook.FacebookActivityInoker;
import com.firebase.authwrapper.providers.common.properties.ProviderProperties;
import com.firebase.authwrapper.providers.common.base.ProviderBase;
import com.firebase.authwrapper.resultactivity.enums.ResultActivityEnum;
import com.firebase.authwrapper.resultactivity.types.facebook.FacebookResultActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class FacebookProvider extends ProviderBase implements IProvider,
        FacebookCallback<LoginResult>{

    private CallbackManager callbackManager;
    private final static String TAG = FacebookProvider.class.getSimpleName();
    private static final String EMAIL = "email";
    private static final String PUBLIC_PROFILE = "public_profile";
    private LoginManager loginManager = LoginManager.getInstance();
    private List<String> permissionsList = new ArrayList<>();

    protected FacebookActivityInoker FacebookActivityInoker;

    public FacebookProvider(ProviderProperties providerProperties) {
        super(providerProperties);

        FacebookActivityInoker = FacebookActivityInoker.getInstance();

        permissionsList.clear();
        permissionsList.add(EMAIL);
        permissionsList.add(PUBLIC_PROFILE);

        FacebookActivityInoker.register(this);
        FacebookActivityInoker.setLoginManager(loginManager);
        FacebookActivityInoker.setPermissionsList(permissionsList);

    }

    @Override
    public void SignIn() {

        callbackManager = CallbackManager.Factory.create();
        loginManager.registerCallback(callbackManager, this);

        Intent intent = new Intent (context, FacebookResultActivity.class);

        String ActionTypePropertyName = context.getString(R.string
                .activity_action_type);
        intent.putExtra(ActionTypePropertyName,
                ResultActivityEnum.ActionType
                        .SIGN_IN.ordinal());

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    @Override
    public void SignOut() {
        super.SignOut();
        LoginManager.getInstance().logOut();
        BroadcastOnComplete(null);
    }

    @Override
    public void OnActivityResultReceived(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        loginManager.unregisterCallback(callbackManager);

    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(providerProperties.getTargetActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            BroadcastOnComplete(user);

                        } else {
                            HandleAuthProviderError(task.getException());
                        }

                        FacebookResultActivity.fa.finish();
                    }
                });


    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        AccessToken token = loginResult.getAccessToken();
        handleFacebookAccessToken(token);
    }

    @Override
    public void onCancel() {
        FacebookResultActivity.fa.finish();
    }

    @Override
    public void onError(FacebookException error) {
        FacebookResultActivity.fa.finish();
    }
}
