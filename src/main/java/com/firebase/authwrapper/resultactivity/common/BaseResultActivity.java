package com.firebase.authwrapper.resultactivity.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;

import com.firebase.authwrapper.providers.common.base.ProviderBase;
import com.firebase.authwrapper.resultactivity.callback.ActivityResultCallback;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

/**
 * <p>
 * The base class of google/facebook result activity. This base class contain
 * all mutual methods and properties related to the derived object, including
 * the binding of a result activity to the target {@link ProviderBase ProviderBase}
 * </p>
 * <p>
 * In addition, this base class manage the progress bar which is presented while
 * the authentication provider (facebook/google) tries to reteived data from
 * remote servers.
 * </p>
 *
 * @author ron barnoy
 * @version 1.0
 * @since 10-5-2018
 * @see BaseResultActivity
 * @see ProviderBase
 */
public class BaseResultActivity extends AppCompatActivity
        implements IResultActivity
{
    @VisibleForTesting
    /**
     * progress bar which is presented while a target provider tries to
     * retrieve remote data
     */
    public ProgressDialog mProgressDialog;


    protected ActivityResultCallback authActivityResultCallback;
    protected String loadingMessage;
    protected Context context;
    protected FirebaseAuth mAuth;
    protected static com.firebase.authwrapper.resultactivity.common.ActivityInvoker activityInvoker;
    protected static final int MILLISEC_SLEEP_BEFORE_FINISH = 1500;

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

            try {
                Thread.sleep(MILLISEC_SLEEP_BEFORE_FINISH);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mProgressDialog.dismiss();
        }
    }

    /**
     * Register this result activity with an
     * {@link ActivityInvoker ActivityInvoker}. This allows the result activity
     * to be connected with the target {@link ProviderBase provider}
     * @param activityResultCallback the target callback of a
     * {@link ActivityInvoker ActivityInvoker}
     */
    @Override
    public void Register(ActivityResultCallback activityResultCallback) {
        this.authActivityResultCallback = activityResultCallback;
    }


    @Override protected void onStart() {
        super.onStart();
        setVisible(true);
    }


}
