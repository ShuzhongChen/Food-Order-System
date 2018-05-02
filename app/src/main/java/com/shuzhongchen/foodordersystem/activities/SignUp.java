package com.shuzhongchen.foodordersystem.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.shuzhongchen.foodordersystem.R;

public class SignUp extends AppCompatActivity {

    EditText edtFirstName, edtLastName, edtEmail, edtPassword;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);

        btnRegister = findViewById(R.id.btnRegister);

    }
}
