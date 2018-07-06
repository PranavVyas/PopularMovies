package com.pro.vyas.pranav.popularmovies.extraUtils;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pro.vyas.pranav.popularmovies.MainActivity;
import com.pro.vyas.pranav.popularmovies.R;

import static com.pro.vyas.pranav.popularmovies.constantUtils.Constants.SPLASH_TIME_OUT;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
