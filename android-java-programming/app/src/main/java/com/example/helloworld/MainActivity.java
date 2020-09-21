package com.example.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "HelloWorldMessage";

    Button button;
    TextView helloText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "This message will appear when the emulator has been started.");

        button = findViewById(R.id.textButton);
        button.setOnClickListener(this);

        helloText = findViewById(R.id.helloTextID);

        /* One way to do a click event.
        Button button = (Button) findViewById(R.id.textButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("test", "Button has been clicked.");
            }
        });
         */
    }

    public void helloTextView() {
        helloText.setVisibility(helloText.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textButton:
                Log.e("errorMSG", "Button has been clicked.");

                /*
                The code below would also set "Hello" text visible or invisible
                when button has been clicked.

                if (helloText.getText().length() == 0){
                    helloText.setText(R.string.appText);
                }
                else {
                    helloText.setText("");
                }
                */

                helloTextView();
                break;

            case R.id.guessGameButton:
                startActivity(new Intent(MainActivity.this, GuessGame.class));
                break;
        }
    }
}