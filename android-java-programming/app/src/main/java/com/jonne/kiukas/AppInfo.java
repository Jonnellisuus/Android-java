package com.jonne.kiukas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AppInfo extends AppCompatActivity {

    TextView firstAppInfoText, secondAppInfoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);

        firstAppInfoText = findViewById(R.id.firstInfoTextID);
        secondAppInfoText = findViewById(R.id.secondInfoTextID);
    }
}