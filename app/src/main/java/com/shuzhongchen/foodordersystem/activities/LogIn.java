package com.shuzhongchen.foodordersystem.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.shuzhongchen.foodordersystem.R;
import com.shuzhongchen.foodordersystem.activities.AdminDashboardActivity;

import java.util.Arrays;

public class LogIn extends AppCompatActivity {

    LoginButton fbLoginButton;
    CallbackManager callbackManager;
    private static final String EMAIL = "email";
    private boolean customerType = false;
    private Button btnLogin;
    private Switch customerTypeSwitch;
    private EditText editEmail;
    private EditText editPassword;

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

                if (customerType) {
                    CheckCustomerLogin();
                } else {
                    CheckAdminLogin();
                }
            }

        });

    }

    private void CheckAdminLogin() {
        if (editEmail.getText().toString().equalsIgnoreCase("admin")
            && editPassword.getText().toString().equalsIgnoreCase("admin")) {

            System.out.println("email" + editEmail.getText().toString() + "\n" + "password" + editPassword.getText().toString());
            Intent goAdminActivity = new Intent(this, AdminDashboardActivity.class);
            startActivity(goAdminActivity);
            finish();
        }
    }

    private void CheckCustomerLogin() {

    }

    private void initControls() {
        callbackManager = CallbackManager.Factory.create();
        fbLoginButton = findViewById(R.id.fbLogin);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        fbLoginButton.setReadPermissions(Arrays.asList(EMAIL));
        editEmail = (EditText)findViewById(R.id.edtEmail);
        editPassword = (EditText)findViewById(R.id.edtPassword);

        customerTypeSwitch = (Switch)findViewById(R.id.CutomerTypeSwitch);
    }

    private void loginWithFB() {
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                    //code
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
