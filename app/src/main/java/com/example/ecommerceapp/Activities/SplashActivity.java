package com.example.ecommerceapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;
import com.example.ecommerceapp.MainActivity;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.onBoardingActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    FirebaseAuth auth;


    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        lottieAnimationView = findViewById(R.id.animation);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null){
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }else {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(SplashActivity.this, onBoardingActivity.class);
                    startActivity(i);
                    finish();
                }
            },5000);

        }
    }
}