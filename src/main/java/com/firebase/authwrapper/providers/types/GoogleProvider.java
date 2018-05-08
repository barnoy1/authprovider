package com.firebase.authwrapper.providers.types;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.game.authprovider.R;
import com.firebase.authwrapper.providers.common.properties.ProviderProperties;
import com.firebase.authwrapper.resultactivity.types.google.GoogleActivityInvoker;
import com.firebase.authwrapper.providers.common.base.ProviderBase;
import com.firebase.authwrapper.resultactivity.enums.ResultActivityEnum;
import com.firebase.authwrapper.resultactivity.types.google.GoogleResultActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class GoogleProvider extends ProviderBase implements
        IProvider {

    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = GoogleProvider.class.getSimpleName();
    private GoogleSignInOptions gso;

    protected GoogleActivityInvoker googleActivityInvoker;

    public GoogleProvider(ProviderProperties providerProperties){
        super(providerProperties);

        googleActivityInvoker = GoogleActivityInvoker.getInstance();
        googleActivityInvoker.register(this);


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context
                        .getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

    }

    @Override
    public void SignIn() throws InterruptedException {

        Intent intent = new Intent (context, GoogleResultActivity.class);
        intent.putExtra(context.getString(R.string.google_signin_options), gso);

        String ActionTypePropertyName = context.getString(R.string
                .activity_action_type);
        intent.putExtra(ActionTypePropertyName,
                ResultActivityEnum.ActionType
                        .SIGN_IN.ordinal());

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    @Override
    public void OnActivityResultReceived(int requestCode, int resultCode, Intent data) {
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                HandleAuthProviderError(new Exception(result.getStatus()
                        .getStatusMessage()));
            }
        }

        GoogleResultActivity.fa.finish();
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(providerProperties.getTargetActivity(), new
                        OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    BroadcastOnComplete(user);

                                } else {
                                    HandleAuthProviderError(task.getException());
                                }

                            }
                        });
    }

    @Override
    public void SignOut(){
        super.SignOut();

        Intent intent = new Intent (context, GoogleResultActivity.class);
        intent.putExtra(context.getString(R.string.google_signin_options), gso);

        String ActionTypePropertyName = context.getString(R.string
                .activity_action_type);
        intent.putExtra(ActionTypePropertyName,
                ResultActivityEnum.ActionType
                        .SIGN_OUT.ordinal());

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

}
