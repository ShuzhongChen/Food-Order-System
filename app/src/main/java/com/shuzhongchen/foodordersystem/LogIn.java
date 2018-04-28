package com.shuzhongchen.foodordersystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class LogIn extends AppCompatActivity {

    LoginButton fbLoginButton;
    CallbackManager callbackManager;
    private static final String EMAIL = "email";
    private boolean customerType = false;
    private Button btnLogin;
    private Switch customerTypeSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        initControls();
        loginWithFB();

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                customerType = customerTypeSwitch.isChecked();
            }

        });


    }

    private void initControls() {
        callbackManager = CallbackManager.Factory.create();
        fbLoginButton = findViewById(R.id.fbLogin);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        fbLoginButton.setReadPermissions(Arrays.asList(EMAIL));

        customerTypeSwitch = (Switch)findViewById(R.id.CutomerTypeSwitch);
    }

    private void loginWithFB() {
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                    //code
                //Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }
}
