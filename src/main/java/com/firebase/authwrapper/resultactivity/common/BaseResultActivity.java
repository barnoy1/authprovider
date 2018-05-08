package com.firebase.authwrapper.resultactivity.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;

import com.firebase.authwrapper.resultactivity.callback.ActivityResultCallback;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by ron on 12/16/2017.
 */

public class BaseResultActivity extends AppCompatActivity
        implements IResultActivity, GoogleApiClient.OnConnectionFailedListener
{
    @VisibleForTesting
    public ProgressDialog mProgressDialog;


    protected ActivityResultCallback authActivityResultCallback;
    protected String loadingMessage;
    protected Context context;
    protected FirebaseAuth mAuth;
    protected static com.firebase.authwrapper.resultactivity.common.ActivityInvoker activityInvoker;

    protected static void setActivityInvoker
            (com.firebase.authwrapper.resultactivity.common.ActivityInvoker currentActivityInvoker){
        activityInvoker = currentActivityInvoker;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setFinishOnTouchOutside(false);
        this.context = getApplicationContext();
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        activityInvoker.raiseOnActivityResultCallback
                        (requestCode, resultCode, data);
    }


    protected void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(loadingMessage);
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    protected void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void Register(ActivityResultCallback activityResultCallback) {
        this.authActivityResultCallback = activityResultCallback;
    }


    @Override protected void onStart() {
        super.onStart();
        setVisible(true);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                String errMessage = String.format("error " +
                        "code:{0}\nMessage{1}", connectionResult
                        .getErrorCode
                        (), connectionResult.getErrorMessage());
        activityInvoker.raiseOnBroadcastFailed(new Exception(errMessage));
    }
}
