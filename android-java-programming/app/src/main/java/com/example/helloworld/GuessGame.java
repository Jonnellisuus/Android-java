package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.IntBuffer;
import java.util.Random;

public class GuessGame extends AppCompatActivity implements View.OnClickListener {

    CardView firstCard;
    CardView secondCard;
    CardView thirdCard;
    CardView fourthCard;

    Animation inCorrectChoice;
    int randomNumber;
    Button refreshButton;
    // Intent refresh;

    Intent intent;
    // int highScore;
    // private String best = "HighScore";

    TextView guessGameScore;
    TextView guessGameCurrentTries;

    int clickCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_game);

        firstCard = findViewById(R.id.guessGameFirstCard);
        secondCard = findViewById(R.id.guessGameSecondCard);
        thirdCard = findViewById(R.id.guessGameThirdCard);
        fourthCard = findViewById(R.id.guessGameFourthCard);

        refreshButton = findViewById(R.id.refreshGuessGame);
        refreshButton.setOnClickListener(this);

        guessGameScore = findViewById(R.id.guessGameScoreText);
        guessGameCurrentTries = findViewById(R.id.guessGameCurrentTries);

        inCorrectChoice = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.guess_game_incorrect_answer);

        firstCard.setOnClickListener(this);
        secondCard.setOnClickListener(this);
        thirdCard.setOnClickListener(this);
        fourthCard.setOnClickListener(this);

        /*
        intent = new Intent(this, GuessGame.class);
        intent.putExtra(best, highScore);

        Bundle extras = getIntent().getExtras();
        if( extras == null) {
            highScore = 0;
        } else {
            highScore = extras.getInt(best);
            guessGameHighscore.setText(Integer.toString(highScore));
            // guessGameHighscore.setText(String.valueOf(highScore));
            // guessGameHighscore.setText(Integer.toString(highScore));
        }

         */

        /*
        This would be used to generate random number if onStart method was not used.

        randomNumber = generateRandomNumber();
         */

        /*
        final CardView firstCard = (CardView) findViewById(R.id.guessGameFirstCard);
        firstCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation inCorrectChoice = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.guess_game_incorrect_answer);
                firstCard.startAnimation(inCorrectChoice);
            }
        });

        final CardView secondCard = (CardView) findViewById(R.id.guessGameSecondCard);
        secondCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation inCorrectChoice = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.guess_game_incorrect_answer);
                secondCard.startAnimation(inCorrectChoice);
            }
        });

        final CardView thirdCard = (CardView) findViewById(R.id.guessGameThirdCard);
        thirdCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation inCorrectChoice = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.guess_game_incorrect_answer);
                thirdCard.startAnimation(inCorrectChoice);
            }
        });

        final CardView fourthCard = (CardView) findViewById(R.id.guessGameFourthCard);
        fourthCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation inCorrectChoice = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.guess_game_incorrect_answer);
                fourthCard.startAnimation(inCorrectChoice);
            }
        });

         */

        /*
        final CardView firstCardAnimation = findViewById(R.id.guessGameFirstCard);
        firstCardAnimation.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
               YoYo.with(Techniques.FlipInX)
                       .duration(1000)
                       .playOn(firstCardAnimation);
           }
        });

         */

    }

    @Override
    public void onStart() {
        super.onStart();
        randomNumber = generateRandomNumber();
    }

    public int generateRandomNumber() {
        Random randomNumber = new Random();
        return randomNumber.nextInt(4);
    }

    public void writeFile() {
        String textToSave = guessGameCurrentTries.getText().toString();

        try {
            FileOutputStream fileOutputStream = openFileOutput("saved_score.txt", MODE_PRIVATE);
            fileOutputStream.write(textToSave.getBytes());
            fileOutputStream.close();

            guessGameCurrentTries.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFile() {
        try {
            FileInputStream fileInputStream = openFileInput("saved_score.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();

            String score;
            while ((score = bufferedReader.readLine()) != null) {
                stringBuffer.append(score).append("\n");
            }

            guessGameScore.setText(stringBuffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.guessGameFirstCard:
                if (randomNumber == 0) {
                    firstCard.setVisibility(View.GONE);
                    guessGameScore.setVisibility(View.VISIBLE);
                    clickCount=clickCount+1;
                    guessGameCurrentTries.setText(String.valueOf(clickCount));
                    writeFile();
                    readFile();
                }

                else {
                    firstCard.startAnimation(inCorrectChoice);
                    clickCount=clickCount+1;
                    guessGameCurrentTries.setText(String.valueOf(clickCount));
                    writeFile();
                }
                break;

            case R.id.guessGameSecondCard:
                if (randomNumber == 1) {
                    secondCard.setVisibility(View.GONE);
                    guessGameScore.setVisibility(View.VISIBLE);
                    clickCount=clickCount+1;
                    guessGameCurrentTries.setText(String.valueOf(clickCount));
                    writeFile();
                    readFile();
                }

                else {
                    secondCard.startAnimation(inCorrectChoice);
                    clickCount=clickCount+1;
                    guessGameCurrentTries.setText(String.valueOf(clickCount));
                    writeFile();
                }
                break;

            case R.id.guessGameThirdCard:
                if (randomNumber == 2) {
                    thirdCard.setVisibility(View.GONE);
                    guessGameScore.setVisibility(View.VISIBLE);
                    clickCount=clickCount+1;
                    guessGameCurrentTries.setText(String.valueOf(clickCount));
                    writeFile();
                    readFile();
                }

                else {
                    thirdCard.startAnimation(inCorrectChoice);
                    clickCount=clickCount+1;
                    guessGameCurrentTries.setText(String.valueOf(clickCount));
                    writeFile();
                }
                break;

            case R.id.guessGameFourthCard:
                if (randomNumber == 3) {
                    fourthCard.setVisibility(View.GONE);
                    guessGameScore.setVisibility(View.VISIBLE);
                    clickCount=clickCount+1;
                    guessGameCurrentTries.setText(String.valueOf(clickCount));
                    writeFile();
                    readFile();
                }

                else {
                    fourthCard.startAnimation(inCorrectChoice);
                    clickCount=clickCount+1;
                    guessGameCurrentTries.setText(String.valueOf(clickCount));
                    writeFile();
                }
                break;

            case R.id.refreshGuessGame:
                /*
                refresh = new Intent(this, GuessGame.class);
                startActivity(refresh);
                 */

                finish();
                startActivity(getIntent());

                //intent.putExtra(best, highScore);
                break;

            default:
                break;
        }
    }
}