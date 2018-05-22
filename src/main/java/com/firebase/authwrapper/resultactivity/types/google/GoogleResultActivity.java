package com.firebase.authwrapper.resultactivity.types.google;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.firebase.authwrapper.providers.types.GoogleProvider;
import com.game.authprovider.R;
import com.firebase.authwrapper.resultactivity.common.BaseResultActivity;
import com.firebase.authwrapper.resultactivity.enums.ResultActivityEnum;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.Status;

/**
 * <p>
 * This class is the google result activity implementation of
 * {@link BaseResultActivity BaseResultActivity}. This class holds all the
 * relevant method required to authenticate user via google API and error
 * handling.
 * </p>
 * <p>
 * The type of action to be (SignIn/SignOut) is determine by the target
 * {@link GoogleProvider GoogleProvider}. In OnCreate method this data is
 * extracted by reading the activity result bundle. Based on the selected
 * option, this activity will invoke one of the two actions and passed the
 * activity result to the target provider by
 * {@link GoogleActivityInvoker GoogleActivityInvoker}
 * </p>
 *
 * @author ron barnoy
 * @version 1.0
 * @since 10-5-2018
 * @see BaseResultActivity
 * @see GoogleProvider
 * @see GoogleActivityInvoker
 */
public class GoogleResultActivity extends BaseResultActivity
        implements GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 9001;
    private ResultActivityEnum.ActionType actionType;
    private GoogleSignInOptions gso;
    private GoogleApiClient googleApiClient;

    /**
     * public instance of this activity so it can be called and destroyed
     * after onActivityResult successfully arrived to the {@link
     * GoogleProvider GoogleProvider}
     */
    public static Activity fa;

    private final static String TAG = GoogleResultActivity.class
            .getSimpleName();

    /**
     * OnCreate implementation of the google result activity. In this stage
     * the desired action determined in the target provider is being extracted
     * by {@link GoogleActivityInvoker GoogleActivityInvoker} and executed
     * @param savedInstanceState the bundle contains data which is transferred
     *                          from {@link GoogleProvider GoogleProvider}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fa =  this;
        BaseResultActivity.setActivityInvoker
                (GoogleActivityInvoker.getInstance());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String ActionTypePropertyName = context.getString(R.string
                    .activity_action_type);
            int actionId = extras.getInt(ActionTypePropertyName);
            actionType = ResultActivityEnum.ActionType.values()[actionId];
        }


         gso = (GoogleSignInOptions) getIntent()
                .getParcelableExtra(context
                        .getString(R.string.google_signin_options));


         googleApiClient = new GoogleApiClient.Builder(context)
                .enableAutoManage(this /* FragmentActivity */,
                        this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        GoogleActivityInvoker.getInstance()
                .setGoogleApiClient(googleApiClient);

        switch (actionType){
            case SIGN_IN:
                SignIn();
				break;
				
            case SIGN_OUT:
                SignOut();
				break;
        }

    }

    private void SignIn()
    {

        this.loadingMessage =
                this.getResources().getString(R.string.signin_google_message);
        showProgressDialog();

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void SignOut(){

        this.loadingMessage =
                this.getResources().getString(R.string.signout_google_message);



        googleApiClient.connect();
        googleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {

                mAuth.signOut();
                if (googleApiClient.isConnected()){


                    Auth.GoogleSignInApi.signOut(googleApiClient)
                            .setResultCallback(new ResultCallbacks<Status>() {
                                @Override
                                public void onSuccess(@NonNull Status status) {
                                    GoogleActivityInvoker.getInstance()
                                            .raiseOnBroadcastComplete(null);
                                    hideProgressDialog();
                                    finish();
                                }

                                @Override
                                public void onFailure(@NonNull Status status) {


                                    String errMessage = String.format("error " +
                                            "code:{0}\nMessage{1}", status
                                            .getStatusCode(),
                                            status.getStatusMessage());

                                    GoogleActivityInvoker.getInstance()
                                            .raiseOnBroadcastFailed
                                                    (new Exception(errMessage));

                                    hideProgressDialog();
                                    finish();
                                }
                            });

                }
            }

            @Override
            public void onConnectionSuspended(int i) {
                Log.w(TAG, "onConnectionSuspended: " + String.valueOf(i));
            }
        });

    }

    /**
     * Implementation of {@link GoogleApiClient.OnConnectionFailedListener
     * GoogleApiClient.OnConnectionFailedListener }. This callback is triggered
     * when an exception is caught during connection process
     * @param connectionResult the connection result which was caught in case
     *                         of failure
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        String errMessage = String.format("error " +
                "code:{0}\nMessage{1}", connectionResult
                .getErrorCode
                        (), connectionResult.getErrorMessage());

        activityInvoker.raiseOnBroadcastFailed(new Exception(errMessage));
    }

}
