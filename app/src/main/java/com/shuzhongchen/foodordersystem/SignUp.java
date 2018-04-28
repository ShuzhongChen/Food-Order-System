package com.shuzhongchen.foodordersystem;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import java.security.Signature;

public class SignUp extends AppCompatActivity {

    EditText edtFirstName, edtLastName, edtEmail, edtPassword;
    Button btnRegister;
    FirebaseAuth firebaseAuth;


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

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

    }

    private void registerUser() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)) {
            //email is empty
            //Toast.makeText(getApplicationContext(), "Please enter email", Toast.LENGTH_SHORT).show();
            edtEmail.setError("Email can't be empty");
            return;
        }
        if(TextUtils.isEmpty(password)) {
            //Toast.makeText(getApplicationContext(), "Please enter password", Toast.LENGTH_SHORT).show();
            edtPassword.setError("Password can't be empty");
            return;
        }

        if(password.length() < 6) {
            edtPassword.setError("Password should be at least 6 characters");
            return;
        }

       firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Register Successfully", Toast.LENGTH_SHORT).show();
                }else {
                    FirebaseAuthException e = (FirebaseAuthException) task.getException();
                    Log.e("LoginActivity", "Failed Registration", e);
                    Toast.makeText(getApplicationContext(), "Register fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
