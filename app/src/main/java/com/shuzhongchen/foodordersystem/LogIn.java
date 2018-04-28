package com.shuzhongchen.foodordersystem;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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

    FirebaseAuth myAuth;
    ProgressBar loginprogBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        myAuth = FirebaseAuth.getInstance();

        initControls();
        loginWithFB();

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                editPassword.onEditorAction(EditorInfo.IME_ACTION_DONE);
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
        System.out.println("check admin");
        if (editEmail.getText().toString().equalsIgnoreCase("admin")
            && editPassword.getText().toString().equalsIgnoreCase("admin")) {

            System.out.println("email" + editEmail.getText().toString() + "\n" + "password" + editPassword.getText().toString());
            Intent goAdminActivity = new Intent(this, AdminDashboardActivity.class);
            startActivity(goAdminActivity);
            finish();
        }
    }

    private void CheckCustomerLogin() {

        String userEmail = editEmail.getText().toString().trim();
        String userPW = editPassword.getText().toString().trim();

        if(TextUtils.isEmpty(userEmail)) {
            //email is empty
            editEmail.setError("Email is required");
            editEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            editEmail.setError("Please enter a valid email");
            editEmail.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(userPW)) {

            editPassword.setError("Password is required");
            editPassword.requestFocus();
            return;
        }


        if(userPW.length() < 6) {
            editPassword.setError("Password should be at least 6 characters");
            editPassword.requestFocus();
            return;
        }

        loginprogBar.setVisibility(View.VISIBLE);

        myAuth.signInWithEmailAndPassword(userEmail, userPW).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                loginprogBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                        //jump to order page

                    Toast.makeText(getApplicationContext(), "Log in successfully", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });
        
    }

    private void initControls() {
        callbackManager = CallbackManager.Factory.create();
        fbLoginButton = findViewById(R.id.fbLogin);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        fbLoginButton.setReadPermissions(Arrays.asList(EMAIL));
        editEmail = (EditText)findViewById(R.id.edtEmail);
        editPassword = (EditText)findViewById(R.id.edtPassword);

        customerTypeSwitch = (Switch)findViewById(R.id.CutomerTypeSwitch);

        loginprogBar = findViewById(R.id.logInBar);
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
