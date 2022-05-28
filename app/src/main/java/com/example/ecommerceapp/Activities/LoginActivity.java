package com.example.ecommerceapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.MainActivity;
import com.example.ecommerceapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText email,password;
    TextView register;
    Button login;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        login = findViewById(R.id.btn_login);
        register = findViewById(R.id.txt_register);
        auth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = email.getText().toString();
                String  userPassword= password.getText().toString();

                if (TextUtils.isEmpty(userEmail)){
                    Toast.makeText(LoginActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(userPassword)){
                    Toast.makeText(LoginActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (userPassword.length() < 6){
                    Toast.makeText(LoginActivity.this, "Password too short, Enter minimum 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.signInWithEmailAndPassword(userEmail,userPassword)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener< AuthResult >() {
                            @Override
                            public void onComplete(@NonNull Task< AuthResult > task) {

                                if (task.isSuccessful()){
                                    Toast.makeText(LoginActivity.this, " Login Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                }
                                else {
                                    Toast.makeText(LoginActivity.this, "Login Failed"+task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(i);
                finish();
            }
        });


    }
}