package com.firebase.authwrapper.providers.types;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.firebase.authwrapper.providers.delegate.Provider;
import com.firebase.authwrapper.resultactivity.callback.ActivityResultCallback;
import com.game.authprovider.R;
import com.firebase.authwrapper.providers.common.properties.ProviderProperties;
import com.firebase.authwrapper.providers.common.base.ProviderBase;
import com.firebase.authwrapper.providers.common.callbacks.AuthenticationListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;


/**
 * Facebook Provider concrete class. This class implements
 * {@link IProvider IProvider} interface and
 * {@link ProviderBase ProviderBase} abstract methods based on
 * email/firebase provider functions
 *
 * @author ron barnoy
 * @version 1.0
 * @since 10-5-2018
 * @see Provider
 */
public class MailProvider extends ProviderBase implements IProvider {

    private final String TAG = MailProvider.class.getSimpleName();
    private AuthenticationListener.Email emailCallback;

    /**
     * ctor. Concrete constructor of mail provider
     * @param providerProperties target providerProperties needed for
     *                           initialization of this provider
     */
    public MailProvider(ProviderProperties providerProperties) {
        super(providerProperties);
    }

    /**
     * @param firebaseuser target user for mail verification required
     *             for {@link MailProvider}
     * @throws IllegalArgumentException
     * @see ProviderBase#SendEmailVerification(FirebaseUser)
     */
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
                                }
                                else{
                                    HandleAuthProviderError(task.getException());
                                }

                            }
                        });

    }


    /**
     * Implementation of email sign in provider using firebasework
     * This implementation based on abstract method found in
     * {@link ProviderBase#SignIn(String, String, AuthenticationListener.Email)}
     * @param email the firebase user email address
     * @param password the firebase user password
     * @param emailCallback the registered callback. This callback will be
     *                      notified of the signin procedure
     * @throws InterruptedException
     */
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
                                    return;
                                }

                                try {
                                    if (task.getException() != null)
                                    {
                                        String message = task.getException()
                                                .getMessage();


                                        if (message.equals(getContext()
                                                .getString(R.string
                                                        .email_account_already_exists_exception))) {
                                            callback.OnAccountExists();
                                        }
                                        else
                                        {
                                            HandleAuthProviderError
                                                    (task.getException());
                                        }
                                    }
                                }
                                catch (Exception ex)
                                {
                                    HandleAuthProviderError
                                            (ex);
                                }
                            }
                        });
    }

    /**
     * Implementation of handling the activity result which retrieved from the
     * the inner result activity. Not relevant for this provider
     * @see ActivityResultCallback
     */
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
