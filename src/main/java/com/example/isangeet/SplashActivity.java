package com.example.isangeet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.airbnb.lottie.Lottie;
import com.airbnb.lottie.LottieAnimationView;

import java.time.Instant;

public class SplashActivity extends AppCompatActivity {
    LottieAnimationView animationView;
    TextView wel,learning;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        wel = findViewById(R.id.textview1);
        learning = findViewById(R.id.textview2);


        Intent iHome = new Intent(SplashActivity.this,MainActivity.class);

      new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
              startActivity(iHome);
              finish();
          }
      },4000
      );

      Animation myanimation = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.animation1);
      wel.startAnimation(myanimation);

        Animation myanimation2 = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.animation2);
        learning.startAnimation(myanimation2);


    }
}