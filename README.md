
<h1 align="center">
  <br>
  <img src="https://github.com/barnoy1/authprovider/wiki/images/logo.png" 
  alt="Auth Provider" width="200">
</h1>

<h4 align="center">A minimal Authentication Library built on 
top of <a href="https://firebase.google.com" target="_blank">Firebase</a>.</h4>

<p align="center">
  <img src="https://img.shields.io/badge/version-1.0-blue.svg"
           alt="Gitter">
</p>

## Table of content

1. [Introduction](#intro)
2. [Prerequisite](#prerequisite)
3. [Setup](#setup)
4. [License](#license)
5. [Links](#links)

## Introduction
When using firebase authentication providers it 


## Prerequisite
In order for the target application to work properly it must fully integrated with firebase authentication mail, facebook and goolge providers.  
* First, the target application must be integrated with firebase SDK. See the following tutorial [Add Firebase to Your Android Project](https://firebase.google.com/docs/android/setup).
* Since this library is based on *com.google.firebase:firebase-auth* dependency, the target application must add Firebase Authentication package. Please follow the guide [Add Firebase Authentication to your app](https://firebase.google.com/docs/auth/android/start/)
*  In order to enable Authenticate Using Google Sign-In feature needed for Google provider follow the next guide [ Authenticate Using Google Sign-In](https://firebase.google.com/docs/auth/android/google-signin). This feature requires SHA1 key generation. See the following Youtube tutorial in order to obtain a key for the target application [How to get the SHA1 fingerprint certificate in android studio for debug mode](https://www.youtube.com/watch?v=aakXkUY6MYU) 
* In order to enable Authenticate Using Facebook Sign-In feature needed for Facebook provider the target application must be integrated  with facebook SDK. follow the next guide [Add Facebook Login to Your App or Website](https://developers.facebook.com/docs/facebook-login). This step requires Hash key generation. See the following Youtube tutorial in order to obtain a key for the target application [Facebook Android Generate Key Hash](https://stackoverflow.com/questions/5306009/facebook-android-generate-key-hash)

## Setup
* Put **authprovider-release.aar** into the libs folder
* Insert in the target application build.grade file in the dependencies cluster the following:
  
  In Android Studio 3.0+:
  ```
  compile files('libs/authprovider-release.jar')
  ```
  
  In Android Studio 3.0+:
  ```
  implementation files('libs/authprovider-release.jar')
  ```

## Usage
* implement AuthListener callbacks in the target application:

```
public class SignInActivity extends AppCompatActivity implements
        View.OnClickListener, AuthenticationListener  {
        
...
@Override
    public void OnAuthenticationComplete(FirebaseUser user) {
        // Insert your code here to update UIThread with current user data
    }

    @Override
    public void OnAuthenticationFailed(Exception ex) {
        // Handle expections that are raise during authentication process
    }

...
}
```

* In OnCreate method insert the configure section of the provider manager singleton using ProviderProperties object. The ProviderProperties contains the target activity.

```
 @Override
    protected void onCreate(Bundle savedInstanceState) {
     
    ...
    ProviderProperties authProviderConfigurations = new
                    ProviderProperties.AuthProviderPropertiesBuilder()
                    .targetActivity(SignInActivity.this)
                    .build();

            mProviderManager.Configure(authProviderConfigurations);
    ...
    }
    
```

* Now you can use the provider manager object to invoked different provider types that registered to the same target activity callbacks such as facebook, google and email providers. Here is an example of sign in using different firebase providers (facebook and google) which are wrapped in provider manager. The user can select the wanted provider and the SignIn process will be registered in AuthenticationListener callbacks regardless of the provider type. 

```
 findViewById(R.id.btnSignWithFacebook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IProvider provider = mProviderManager
                        .getProvider(ProviderEnum.ProviderType.FACEBOOK);

                try {
                    provider.SignIn();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

  findViewById(R.id.btnSignWithGoogle).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

          IProvider provider = mProviderManager
                  .getProvider(ProviderEnum.ProviderType.GOOGLE);
          try {
              provider.SignIn();
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      }
  });
```

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/barnoy1/authprovider/releases). 

## Authors

* **Ron Bar-Noy** - *Initial work* 

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Docomentation

For more detailed information regarding exposed API see [authprovider JavaDocs](http://htmlpreview.github.io/?https://github.com/barnoy1/authprovider/blob/master/docs/index.html)

