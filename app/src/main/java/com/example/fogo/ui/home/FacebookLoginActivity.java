package com.example.fogo.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fogo.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookActivity;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class FacebookLoginActivity extends AppCompatActivity {

    private LoginButton login;
    private CallbackManager callbackManager;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private AccessTokenTracker accessTokenTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_fb);

        firebaseAuth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());

        login = findViewById(R.id.login_button);
        login.setReadPermissions("email", "public_profile");

        callbackManager = CallbackManager.Factory.create();
        login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            sau khi gọi mành hình này lên nó nhảy vào ERROR. run thu di , mlogin di, t xem log thoi.ok
//            thấy không, xem đi, log rồi

            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("FACEBOOK_RESULT", "success");
                handleFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("FACEBOOK_RESULT", "cancel");
            }

            @Override
            public void onError(FacebookException e) {
                Log.d("FACEBOOK_RESULT", e + " ");
                e.printStackTrace();
//Đây day t fix het co~ roi run thu k dc nua thi chiu nhe :v mé
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull @NotNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    LoginManager.getInstance().logOut();
                }
            }
        };

        accessTokenTracker = new AccessTokenTracker() { //checktoken
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken
                    currentAccessToken) {
                if (currentAccessToken != null) {
                    LoginManager.getInstance().logOut();
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(123, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        // TODO: 11/06/2021 alo alo, có phải sửa trên web deverlop run lai di alo? run lai xem, t cung eo biet loi j @@ok :v chiu day m search loi xem @@
//
    }

    private void handleFacebookToken(AccessToken accessToken) {
        Log.d("FACEBOOK_RESULT", "handle login user");
        AuthCredential authCredential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(FacebookLoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("FACEBOOK_RESULT", "sign in with credential successful");
                    nextActivity();
                } else {
                    Log.d("FACEBOOK_RESULT", "sign in with credential failure", task.getException());
                }
            }
        });
    }

    private void nextActivity() {
//        int login_code = 1;
        startActivity(new Intent(FacebookLoginActivity.this, HomeActivity.class));
        FacebookLoginActivity.this.finish();
    }
}

