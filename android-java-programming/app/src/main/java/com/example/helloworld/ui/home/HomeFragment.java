package com.example.helloworld.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.helloworld.GuessGame;
import com.example.helloworld.R;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel homeViewModel;

    public static final String TAG = "HelloWorldMessage";

    Button button;
    TextView helloText;

    Button guessGameButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        Log.i(TAG, "This message will appear when the emulator has been started.");

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        helloText = root.findViewById(R.id.helloTextID);

        button = root.findViewById(R.id.textButton);
        button.setOnClickListener(this);

        guessGameButton = root.findViewById(R.id.guessGameButton);
        guessGameButton.setOnClickListener(this);

        /* One way to do a click event.
        Button button = (Button) findViewById(R.id.textButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("test", "Button has been clicked.");
            }
        });
         */

        /* This would set text "This is home fragment".
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
         */
        return root;
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
                startActivity(new Intent(getActivity(), GuessGame.class));
                break;
        }
    }
}