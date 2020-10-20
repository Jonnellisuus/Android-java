package com.example.helloworld.ui.notifications;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.helloworld.R;

public class NotificationsFragment extends Fragment implements View.OnClickListener {

    private NotificationsViewModel notificationsViewModel;

    NumberPicker pickedNumber;
    private String[] timerSeconds;

    Button startTimerButton;
    Button pauseTimerButton;
    Button restartTimerButton;

    TextView remainingTime;
    TextView timerFinishText;
    TextView swipeResetTimer;

    int minValue = 0;
    int maxValue = 60;
    int i;
    int timeLeft;
    int pickedSecond;
    float firstCoordinateX, secondCoordinateX;
    int coordinateDistance = 100;
    boolean isTimerRunning;

    CountDownTimer countDownTimer;
    Animation timerFinishAnimation;
    Ringtone defaultRingtone;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        pickedNumber = root.findViewById(R.id.selectTime);
        pickedNumber.setMinValue(minValue);
        pickedNumber.setMaxValue(maxValue);
        timerSeconds = new String[pickedNumber.getMaxValue() + 1];
        for (i = 0; i < pickedNumber.getMaxValue() + 1; i++) {
            timerSeconds[i] = i + " s";
        }
        pickedNumber.setDisplayedValues(timerSeconds);

        pickedNumber.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                int secondPicker = pickedNumber.getValue();
                Log.e("picker value", timerSeconds[secondPicker]);
            }
        });

        startTimerButton = root.findViewById(R.id.timerStartButton);
        startTimerButton.setOnClickListener(this);

        pauseTimerButton = root.findViewById(R.id.timerPauseButton);
        pauseTimerButton.setOnClickListener(this);

        restartTimerButton = root.findViewById(R.id.timerRestartButton);
        restartTimerButton.setOnClickListener(this);

        timerFinishText = root.findViewById(R.id.timerFinishedText);
        remainingTime = root.findViewById(R.id.timerRemainingTime);
        swipeResetTimer = root.findViewById(R.id.resetTimerSwipeText);

        timerFinishAnimation = AnimationUtils.loadAnimation(requireActivity().getApplicationContext(), R.anim.timer_finish_animation);
        defaultRingtone = RingtoneManager.getRingtone(getActivity(), Settings.System.DEFAULT_RINGTONE_URI);

        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        firstCoordinateX = event.getX();
                        break;

                    case MotionEvent.ACTION_UP:
                        secondCoordinateX = event.getX();
                        float valueX = secondCoordinateX - firstCoordinateX;

                        if (Math.abs(valueX) > coordinateDistance) {
                            if (secondCoordinateX > firstCoordinateX) {
                                swipeResetTimer();
                            }

                            else {
                                swipeResetTimer();
                            }
                        }
                }
                return true;
            }
        });

        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.timerStartButton:
                pickedSecond = pickedNumber.getValue();
                Log.e("What is picked time?", String.valueOf(pickedSecond));

                countDownTimer = new CountDownTimer(pickedSecond * 1000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        startTimerButton.setEnabled(false);
                        remainingTime.setText(String.valueOf(pickedSecond));
                        pickedSecond--;
                    }

                    public void onFinish() {
                        isTimerRunning = false;
                        startTimerButton.setEnabled(false);
                        pauseTimerButton.setEnabled(false);
                        remainingTime.setVisibility(View.INVISIBLE);
                        timerFinishText.setVisibility(View.VISIBLE);
                        swipeResetTimer.setVisibility(View.VISIBLE);
                        timerFinishText.startAnimation(timerFinishAnimation);
                        defaultRingtone.play();
                    }
                }.start();
                isTimerRunning = true;
                break;

            case R.id.timerPauseButton:
                if (isTimerRunning == true) {
                    isTimerRunning = false;
                    countDownTimer.cancel();
                    pauseTimerButton.setText(R.string.timerPlayButton);
                }

                else {
                    isTimerRunning = true;
                    pauseTimerButton.setText(R.string.timerPauseButton);
                    timeLeft = Integer.parseInt(String.valueOf(remainingTime.getText()));

                    countDownTimer = new CountDownTimer(timeLeft * 1000, 1000) {
                        public void onTick(long millisUntilFinished) {
                            remainingTime.setText(String.valueOf(timeLeft));
                            timeLeft--;
                        }

                        public void onFinish() {
                            pauseTimerButton.setEnabled(false);
                            remainingTime.setVisibility(View.INVISIBLE);
                            timerFinishText.setVisibility(View.VISIBLE);
                            swipeResetTimer.setVisibility(View.VISIBLE);
                            timerFinishText.startAnimation(timerFinishAnimation);
                            defaultRingtone.play();
                        }
                    }.start();
                }
                break;

            case R.id.timerRestartButton:
                defaultRingtone.stop();
                countDownTimer.cancel();
                getParentFragmentManager().beginTransaction().detach(this).attach(this).commit();
                break;
        }
    }

    public void swipeResetTimer() {
        defaultRingtone.stop();
        countDownTimer.cancel();
        getParentFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }
}