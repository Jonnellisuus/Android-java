package com.example.helloworld.ui.notifications;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.helloworld.GuessGame;
import com.example.helloworld.MainActivity;
import com.example.helloworld.R;

import java.util.Locale;
import java.util.Objects;

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

        /*
        final TextView textView = root.findViewById(R.id.text_notifications);
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
         */
        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.timerStartButton:
                int secondPicker = pickedNumber.getValue();

                if (secondPicker == 0) {
                    Log.e("picker value", "You selected 1");
                    getTime = getTime + 1;

                    countDownTimer = new CountDownTimer(1000, 1000) {
                        public void onTick(long millisUntilFinished) {
                            remainingTime.setText(String.valueOf(getTime));
                            getTime--;
                        }

                        public void onFinish() {
                            remainingTime.setVisibility(View.INVISIBLE);
                            timerFinishText.setVisibility(View.VISIBLE);
                            timerFinishText.startAnimation(timerFinishAnimation);
                            defaultRingtone.play();
                        }
                    }.start();

                } else if (secondPicker == 1) {
                    Log.e("picker value", "You selected 2");
                    getTime = getTime + 2;

                    countDownTimer = new CountDownTimer(2000, 1000) {
                        public void onTick(long millisUntilFinished) {
                            remainingTime.setText(String.valueOf(getTime));
                            getTime--;
                        }

                        public void onFinish() {
                            remainingTime.setVisibility(View.INVISIBLE);
                            timerFinishText.setVisibility(View.VISIBLE);
                            timerFinishText.startAnimation(timerFinishAnimation);
                            defaultRingtone.play();
                        }
                    }.start();

                } else if (secondPicker == 2) {
                    Log.e("picker value", "You selected 3");
                    getTime = getTime + 3;

                    countDownTimer = new CountDownTimer(3000, 1000) {
                        public void onTick(long millisUntilFinished) {
                            remainingTime.setText(String.valueOf(getTime));
                            getTime--;
                        }

                        public void onFinish() {
                            remainingTime.setVisibility(View.INVISIBLE);
                            timerFinishText.setVisibility(View.VISIBLE);
                            timerFinishText.startAnimation(timerFinishAnimation);
                            defaultRingtone.play();
                        }
                    }.start();

                } else if (secondPicker == 3) {
                    Log.e("picker value", "You selected 4");
                    getTime = getTime + 4;

                    countDownTimer = new CountDownTimer(4000, 1000) {
                        public void onTick(long millisUntilFinished) {
                            remainingTime.setText(String.valueOf(getTime));
                            getTime--;
                        }

                        public void onFinish() {
                            remainingTime.setVisibility(View.INVISIBLE);
                            timerFinishText.setVisibility(View.VISIBLE);
                            timerFinishText.startAnimation(timerFinishAnimation);
                            defaultRingtone.play();
                        }
                    }.start();

                } else if (secondPicker == 4) {
                    Log.e("picker value", "You selected 5");
                    getTime = getTime + 5;

                    countDownTimer = new CountDownTimer(5000, 1000) {
                        public void onTick(long millisUntilFinished) {
                            remainingTime.setText(String.valueOf(getTime));
                            getTime--;
                        }

                        public void onFinish() {
                            remainingTime.setVisibility(View.INVISIBLE);
                            timerFinishText.setVisibility(View.VISIBLE);
                            timerFinishText.startAnimation(timerFinishAnimation);
                            defaultRingtone.play();
                        }
                    }.start();

                } else if (secondPicker == 5) {
                    Log.e("picker value", "You selected 6");
                    getTime = getTime + 6;

                    countDownTimer = new CountDownTimer(6000, 1000) {
                        public void onTick(long millisUntilFinished) {
                            remainingTime.setText(String.valueOf(getTime));
                            getTime--;
                        }

                        public void onFinish() {
                            remainingTime.setVisibility(View.INVISIBLE);
                            timerFinishText.setVisibility(View.VISIBLE);
                            timerFinishText.startAnimation(timerFinishAnimation);
                            defaultRingtone.play();
                        }
                    }.start();

                } else if (secondPicker == 6) {
                    Log.e("picker value", "You selected 7");
                    getTime = getTime + 7;

                    countDownTimer = new CountDownTimer(7000, 1000) {
                        public void onTick(long millisUntilFinished) {
                            remainingTime.setText(String.valueOf(getTime));
                            getTime--;
                        }

                        public void onFinish() {
                            remainingTime.setVisibility(View.INVISIBLE);
                            timerFinishText.setVisibility(View.VISIBLE);
                            timerFinishText.startAnimation(timerFinishAnimation);
                            defaultRingtone.play();
                        }
                    }.start();

                } else if (secondPicker == 7) {
                    Log.e("picker value", "You selected 8");
                    getTime = getTime + 8;

                    countDownTimer = new CountDownTimer(8000, 1000) {
                        public void onTick(long millisUntilFinished) {
                            remainingTime.setText(String.valueOf(getTime));
                            getTime--;
                        }

                        public void onFinish() {
                            remainingTime.setVisibility(View.INVISIBLE);
                            timerFinishText.setVisibility(View.VISIBLE);
                            timerFinishText.startAnimation(timerFinishAnimation);
                            defaultRingtone.play();
                        }
                    }.start();

                } else if (secondPicker == 8) {
                    Log.e("picker value", "You selected 9");
                    getTime = getTime + 9;

                    countDownTimer = new CountDownTimer(9000, 1000) {
                        public void onTick(long millisUntilFinished) {
                            remainingTime.setText(String.valueOf(getTime));
                            getTime--;
                        }

                        public void onFinish() {
                            remainingTime.setVisibility(View.INVISIBLE);
                            timerFinishText.setVisibility(View.VISIBLE);
                            timerFinishText.startAnimation(timerFinishAnimation);
                            defaultRingtone.play();
                        }
                    }.start();

                } else {
                    Log.e("picker value", "You selected 10");
                    getTime = getTime + 10;

                    countDownTimer = new CountDownTimer(10000, 1000) {
                        public void onTick(long millisUntilFinished) {
                            remainingTime.setText(String.valueOf(getTime));
                            getTime--;
                        }

                        public void onFinish() {
                            remainingTime.setVisibility(View.INVISIBLE);
                            timerFinishText.setVisibility(View.VISIBLE);
                            timerFinishText.startAnimation(timerFinishAnimation);
                            defaultRingtone.play();
                        }
                    }.start();
                }
                break;

            case R.id.timerPauseButton:
                countDownTimer.cancel();
                break;

            case R.id.timerRestartButton:
                /*
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                if (Build.VERSION.SDK_INT >= 26) {
                    ft.setReorderingAllowed(false);
                }
                ft.detach(this).attach(this).commit();

                 */

                defaultRingtone.stop();
                getFragmentManager().beginTransaction().detach(this).attach(this).commit();

                // remainingTime.setText(R.string.timerResetedTime);
                // timerFinishText.setVisibility(View.INVISIBLE);

                break;
        }
    }
}