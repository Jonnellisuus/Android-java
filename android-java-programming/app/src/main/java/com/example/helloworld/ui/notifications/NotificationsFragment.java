package com.example.helloworld.ui.notifications;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.helloworld.R;

public class NotificationsFragment extends Fragment implements View.OnClickListener {

    private NotificationsViewModel notificationsViewModel;

    TextClock textClock;

    NumberPicker pickedNumber;
    private String[] timerSeconds;

    Button startTimerButton;
    Button pauseTimerButton;
    Button restartTimerButton;

    TextView remainingTime;
    TextView timerFinishText;

    int getTime;
    int getTimeRemain;
    int timeLeft;
    int pickedSecond;
    int formattedPickedSecond;
    boolean isTimerRunning;

    CountDownTimer countDownTimer;
    Animation timerFinishAnimation;
    Ringtone defaultRingtone;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        textClock = root.findViewById(R.id.textClock1);

        pickedNumber = root.findViewById(R.id.selectTime);
        pickedNumber.setMaxValue(9);
        pickedNumber.setMinValue(0);
        timerSeconds  = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
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

        timerFinishAnimation = AnimationUtils.loadAnimation(requireActivity().getApplicationContext(), R.anim.timer_finish_animation);

        defaultRingtone = RingtoneManager.getRingtone(getActivity(), Settings.System.DEFAULT_RINGTONE_URI);

        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.timerStartButton:
                pickedSecond = pickedNumber.getValue();
                formattedPickedSecond = pickedSecond + 1;
                Log.e("What is picked time?", String.valueOf(formattedPickedSecond));

                countDownTimer = new CountDownTimer(formattedPickedSecond * 1000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        startTimerButton.setEnabled(false);
                        remainingTime.setText(String.valueOf(formattedPickedSecond));
                        formattedPickedSecond--;
                    }

                    public void onFinish() {
                        isTimerRunning = false;
                        remainingTime.setVisibility(View.INVISIBLE);
                        timerFinishText.setVisibility(View.VISIBLE);
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

                    Log.e("Is timer paused or not?", "The time is not running anymore.");
                    Log.e("What is value?", String.valueOf(isTimerRunning));
                }
                else {
                    isTimerRunning = true;
                    pauseTimerButton.setText(R.string.timerPauseButton);
                    pauseTimerButton.setEnabled(false);

                    Log.e("Is timer paused or not?", "The timer is running again.");
                    Log.e("What is value?", String.valueOf(isTimerRunning));

                    timeLeft = Integer.parseInt(String.valueOf(remainingTime.getText()));
                    Log.e("What is remaining time?", String.valueOf(Integer.valueOf(timeLeft)));

                    getTimeRemain = getTimeRemain + timeLeft;
                    countDownTimer = new CountDownTimer(timeLeft * 1000, 1000) {
                        public void onTick(long millisUntilFinished) {
                            startTimerButton.setEnabled(false);
                            remainingTime.setText(String.valueOf(getTimeRemain));
                            getTimeRemain--;
                        }

                        public void onFinish() {
                            isTimerRunning = false;
                            remainingTime.setVisibility(View.INVISIBLE);
                            timerFinishText.setVisibility(View.VISIBLE);
                            timerFinishText.startAnimation(timerFinishAnimation);
                            defaultRingtone.play();
                        }
                    }.start();
                }
                break;

            case R.id.timerRestartButton:
                defaultRingtone.stop();
                countDownTimer.cancel();
                getTime = 0;
                getParentFragmentManager().beginTransaction().detach(this).attach(this).commit();
                break;
        }
    }
}