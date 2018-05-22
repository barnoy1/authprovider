package com.firebase.authwrapper.providers.types;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.firebase.authwrapper.providers.delegate.Provider;
import com.firebase.authwrapper.resultactivity.callback.ActivityResultCallback;
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


/**
 * Facebook Provider concrete class. This class implements
 * {@link IProvider IProvider} interface and
 * {@link ProviderBase ProviderBase} abstract methods based on
 * google/firebase provider functions
 *
 * @author ron barnoy
 * @version 1.0
 * @since 10-5-2018
 * @see Provider
 */
public class GoogleProvider extends ProviderBase implements
        IProvider {

    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = GoogleProvider.class.getSimpleName();
    private GoogleSignInOptions gso;

    protected GoogleActivityInvoker googleActivityInvoker;

    /**
     * ctor. Concrete constructor of google provider
     * @param providerProperties target providerProperties needed for
     *                           initialization of this provider
     */
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

    /**
     * Implementation of google sign in provider using firebasework
     * This implementation based on abstract method found in
     * {@link ProviderBase#SignIn()}
     * @throws InterruptedException
     */
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

    /**
     * Implementation of handling the activity result which retrieved from the
     * the inner result activity. Used for process user login data retrieved
     * form the provider service before passing it to to target application.
     * This method is an abstract method
     * implementation of {@link ProviderBase#OnActivityResultReceived(int, int, Intent)}
     *
     * @see ActivityResultCallback
     */
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

    /**
     * Implementation of google sign out provider using firebasework
     * This implementation based on abstract method found in
     * {@link ProviderBase#SignOut()}
     * @throws InterruptedException
     */
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
