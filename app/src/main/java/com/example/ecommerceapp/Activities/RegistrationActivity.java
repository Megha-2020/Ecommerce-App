package com.example.ecommerceapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class RegistrationActivity extends AppCompatActivity {

    EditText name,email,password;
    TextView login;
    Button register;
    private FirebaseAuth auth;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null){
            startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
            finish();
        }
        name = findViewById(R.id.et_name);
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        register = findViewById(R.id.btn_register);
        login = findViewById(R.id.txt_login);

        /*sharedPreferences = getSharedPreferences("SplashActivity",MODE_PRIVATE);
        boolean isFirstTime = sharedPreferences.getBoolean("firstTime",true);

        if (isFirstTime){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstTime",false);
            editor.commit();

            Intent i = new Intent(RegistrationActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }*/


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName = name.getText().toString();
                String userEmail = email.getText().toString();
                String  userPassword= password.getText().toString();
                if (TextUtils.isEmpty(userName)){
                    Toast.makeText(RegistrationActivity.this, "Enter Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(userEmail)){
                    Toast.makeText(RegistrationActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(userPassword)){
                    Toast.makeText(RegistrationActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (userPassword.length() < 6){
                    Toast.makeText(RegistrationActivity.this, "Password too short, Enter minimum 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(userEmail,userPassword)
                        .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener< AuthResult >() {
                            @Override
                            public void onComplete(@NonNull Task< AuthResult > task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(RegistrationActivity.this, "Successfully Register", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                                }
                                else {
                                    Toast.makeText(RegistrationActivity.this, "Registration failed"+task.getException(), Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();
            }
        });


    }
}