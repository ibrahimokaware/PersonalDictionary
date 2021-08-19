package com.rivierasoft.personaldictionary.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.rivierasoft.personaldictionary.R;
import com.rivierasoft.personaldictionary.databinding.ActivitySplachBinding;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplachBinding binding;

    private Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplachBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Declare an imageView to show the animation.
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        // Create the animation.
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // MainActivity.class is the activity to go after showing the splash screen.
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        binding.splashIV.startAnimation(anim);
    }
}