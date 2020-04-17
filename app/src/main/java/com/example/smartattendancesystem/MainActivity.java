package com.example.smartattendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int SPLASH_OUT = 3000;
        progressBar=findViewById(R.id.progressBar);



        //--------------------SPLASH SCREEN DISAPPEARS AFTER *SPLASH_OUT TIME----------------------//
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent logInPage= new Intent(MainActivity.this, LogInPage.class);
                startActivity(logInPage);
                finish();
            }
        }, SPLASH_OUT);
    }
}
