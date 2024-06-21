package com.example.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class home extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        Activity activity = home.this;
        LinearLayout Mapbtn = (LinearLayout) findViewById(R.id.map);
        Mapbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(home.this, maps.class);
                activity.startActivity(intent);
            }
        });

        LinearLayout Meteobtn = (LinearLayout) findViewById(R.id.meteo);
        Meteobtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(home.this, Weather.class);
                activity.startActivity(intent);
            }
        });
    }
}
