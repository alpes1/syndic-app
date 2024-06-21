package com.example.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;


import androidx.activity.EdgeToEdge;
import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //hello there
        ImageView logoimage;
        Activity activity = MainActivity.this;
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splashscreen);

        logoimage=findViewById(R.id.logo);
        Animation rota = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        logoimage.startAnimation(rota);

        Thread thread = new Thread( new Runnable() {
            @Override
            public void run() {
                try
                {
                    Thread.sleep(3000);

                Intent intent = new Intent(MainActivity.this, login.class);
                activity.startActivity(intent);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                finish();
            }
        }
    });

    thread.start();



    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.e("act", "debut activité.");

    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("act", "stop activité");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.e("act", "pause activité");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("act", "resume activité");

    }




}