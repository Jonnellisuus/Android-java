package com.example.helloworld;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class GuessGame extends AppCompatActivity implements View.OnClickListener {

    CardView firstCard;
    CardView secondCard;
    CardView thirdCard;
    CardView fourthCard;

    Animation inCorrectChoice;
    Button restartButton;

    TextView guessGameScore;
    TextView guessGameCurrentTries;

    ImageView firstDiamondImage;
    ImageView secondDiamondImage;
    ImageView thirdDiamondImage;
    ImageView fourthDiamondImage;

    int randomNumber;
    int clickCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_game);

        firstCard = findViewById(R.id.guessGameFirstCard);
        secondCard = findViewById(R.id.guessGameSecondCard);
        thirdCard = findViewById(R.id.guessGameThirdCard);
        fourthCard = findViewById(R.id.guessGameFourthCard);

        firstCard.setOnClickListener(this);
        secondCard.setOnClickListener(this);
        thirdCard.setOnClickListener(this);
        fourthCard.setOnClickListener(this);

        firstDiamondImage = findViewById(R.id.firstDiamond);
        secondDiamondImage = findViewById(R.id.secondDiamond);
        thirdDiamondImage = findViewById(R.id.thirdDiamond);
        fourthDiamondImage = findViewById(R.id.fourthDiamond);

        restartButton = findViewById(R.id.restartGuessGame);
        restartButton.setOnClickListener(this);

        guessGameScore = findViewById(R.id.guessGameScoreText);
        guessGameCurrentTries = findViewById(R.id.guessGameCurrentTries);

        inCorrectChoice = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.guess_game_incorrect_answer);

        /*
        This would be used to generate random number if onStart method was not used.

        randomNumber = generateRandomNumber();
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

    public void writeScoreToFile() {
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

    public void readScoreFromFile() {
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
                    firstDiamondImage.setVisibility(View.VISIBLE);
                    guessGameScore.setVisibility(View.VISIBLE);

                    clickCount = clickCount+1;
                    guessGameCurrentTries.setText(String.valueOf(clickCount));

                    writeScoreToFile();
                    readScoreFromFile();
                }

                else {
                    firstCard.startAnimation(inCorrectChoice);
                    clickCount = clickCount+1;
                    guessGameCurrentTries.setText(String.valueOf(clickCount));
                    writeScoreToFile();
                }
                break;

            case R.id.guessGameSecondCard:
                if (randomNumber == 1) {
                    secondCard.setVisibility(View.GONE);
                    secondDiamondImage.setVisibility(View.VISIBLE);
                    guessGameScore.setVisibility(View.VISIBLE);

                    clickCount = clickCount+1;
                    guessGameCurrentTries.setText(String.valueOf(clickCount));

                    writeScoreToFile();
                    readScoreFromFile();
                }

                else {
                    secondCard.startAnimation(inCorrectChoice);
                    clickCount = clickCount+1;
                    guessGameCurrentTries.setText(String.valueOf(clickCount));
                    writeScoreToFile();
                }
                break;

            case R.id.guessGameThirdCard:
                if (randomNumber == 2) {
                    thirdCard.setVisibility(View.GONE);
                    thirdDiamondImage.setVisibility(View.VISIBLE);
                    guessGameScore.setVisibility(View.VISIBLE);

                    clickCount = clickCount+1;
                    guessGameCurrentTries.setText(String.valueOf(clickCount));

                    writeScoreToFile();
                    readScoreFromFile();
                }

                else {
                    thirdCard.startAnimation(inCorrectChoice);
                    clickCount = clickCount+1;
                    guessGameCurrentTries.setText(String.valueOf(clickCount));
                    writeScoreToFile();
                }
                break;

            case R.id.guessGameFourthCard:
                if (randomNumber == 3) {
                    fourthCard.setVisibility(View.GONE);
                    fourthDiamondImage.setVisibility(View.VISIBLE);
                    guessGameScore.setVisibility(View.VISIBLE);

                    clickCount = clickCount+1;
                    guessGameCurrentTries.setText(String.valueOf(clickCount));

                    writeScoreToFile();
                    readScoreFromFile();
                }

                else {
                    fourthCard.startAnimation(inCorrectChoice);
                    clickCount = clickCount+1;
                    guessGameCurrentTries.setText(String.valueOf(clickCount));
                    writeScoreToFile();
                }
                break;

            case R.id.restartGuessGame:
                finish();
                startActivity(getIntent());
                break;

            default:
                break;
        }
    }
}