package com.firebase.authwrapper.providers.types;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.game.authprovider.R;
import com.firebase.authwrapper.providers.common.properties.ProviderProperties;
import com.firebase.authwrapper.providers.common.base.ProviderBase;
import com.firebase.authwrapper.providers.common.callbacks.AuthenticationListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by ron on 11/11/2017.
 */

public class MailProvider extends ProviderBase implements IProvider {

    private final String TAG = MailProvider.class.getSimpleName();
    private AuthenticationListener.Email emailCallback;

    public MailProvider(ProviderProperties providerProperties) {
        super(providerProperties);
    }

    @Override
    public void SendEmailVerification(FirebaseUser firebaseuser) {

        // Send verification email
        final FirebaseUser user = firebaseuser;
        final AuthenticationListener.Email callback = this.emailCallback;

        user.sendEmailVerification()
                .addOnCompleteListener(providerProperties.getTargetActivity(),
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    callback.OnVerficationMailSent();
                                } else {
                                    HandleAuthProviderError(task.getException());
                                }

                            }
                        });

    }

    /**
     * signing user by using a derived provider
     */
    @Override
    public void SignIn() throws InterruptedException {

        throw new IllegalArgumentException
                (context.getString(R.string.email_provider_signin_exception));
    }

    @Override
    public void SignIn(String email, String password, AuthenticationListener
            .Email emailCallback) throws IllegalArgumentException {

        Throwable throwable = validateForm(email, password);
        if (throwable != null){
            HandleAuthProviderError(new Exception(throwable));
        }

        this.emailCallback = emailCallback;
        final AuthenticationListener.Email callback = this.emailCallback;
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(providerProperties.getTargetActivity(),
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    callback.OnNewUserCreated(user);
                                } else {
                                    HandleAuthProviderError
                                            (task.getException());
                                }
                            }
                        });
    }


    @Override
    public void OnActivityResultReceived(int requestCode, int resultCode, Intent data) {

    }

    private Throwable validateForm(String email, String password) {

        String message = "";

        if (TextUtils.isEmpty(email)) {
            message = "Email Required.";
            return new Throwable(message);
        }
        if (TextUtils.isEmpty(password)) {
            return new Throwable(message);
        }

        return null;
    }
}
