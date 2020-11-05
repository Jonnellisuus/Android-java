package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.helloworld.ui.home.HomeFragment;

public class CompanyNotFound extends AppCompatActivity {
    TextView companyNotFoundText, returnHomeText;
    Button returnHomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_not_found);

        companyNotFoundText = findViewById(R.id.companyNotFoundTextView);
        returnHomeText = findViewById(R.id.returnHomeTextView);

        returnHomeButton = findViewById(R.id.returnHomeButton);
        returnHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnHomeButton.setVisibility(View.GONE);
                FragmentManager fragmentManager = getSupportFragmentManager();
                HomeFragment homeFragment = new HomeFragment();
                fragmentManager.beginTransaction().replace(R.id.companyNotFoundPage, homeFragment);

                Intent myIntent = new Intent(CompanyNotFound.this, MainActivity.class);
                startActivity(myIntent);
            }
        });
    }
}