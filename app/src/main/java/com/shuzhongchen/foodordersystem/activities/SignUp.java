package com.shuzhongchen.foodordersystem.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import com.shuzhongchen.foodordersystem.R;

public class SignUp extends AppCompatActivity {

    EditText edtFirstName, edtLastName, edtEmail, edtPassword;
    Button btnRegister;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

       firebaseAuth = FirebaseAuth.getInstance();

        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnRegister = findViewById(R.id.btnRegister);

        progressBar = findViewById(R.id.logInBar);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
                edtPassword.onEditorAction(EditorInfo.IME_ACTION_DONE);
            }
        });

    }

    private void registerUser() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String firstName = edtFirstName.getText().toString();
        String lastName = edtLastName.getText().toString();

        if(TextUtils.isEmpty(firstName)) {
            edtFirstName.setError("First name is required");
            edtFirstName.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(lastName)) {
            edtLastName.setError("First name is required");
            edtLastName.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(email)) {
            //email is empty
            edtEmail.setError("Email is required");
            edtEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Please enter a valid email");
            edtEmail.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(password)) {

            edtPassword.setError("Password is required");
            edtPassword.requestFocus();
            return;
        }


        if(password.length() < 6) {
            edtPassword.setError("Password should be at least 6 characters");
            edtPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

       firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    //jump to log in page
                    Toast.makeText(getApplicationContext(), "Register Successfully", Toast.LENGTH_SHORT).show();
                    saveUserInfo();
                    Intent intent = new Intent(SignUp.this, LogIn.class);
                    startActivity(intent);

                }
                else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private void saveUserInfo() {
        String firstName = edtFirstName.getText().toString();
        String lastName = edtLastName.getText().toString();
        String displayName = firstName + " " + lastName;

        final FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null) {
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder().setDisplayName(displayName).build();
            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d("sign up", "onComplete: " + user.getDisplayName());
                    }
                }
            });

        }

    }


}
