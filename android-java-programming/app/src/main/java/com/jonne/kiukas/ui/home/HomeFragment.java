package com.jonne.kiukas.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jonne.kiukas.AppInfo;
import com.jonne.kiukas.GuessGame;
import com.jonne.kiukas.R;
import com.jonne.kiukas.SearchCompany;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel homeViewModel;

    public static final String TAG = "HelloWorldMessage";

    Button button, guessGameButton, searchCompanyButton;
    // TextView helloText, appCreatedText;

    float firstCoordinateX, secondCoordinateX;
    int coordinateDistance = 100;
    // boolean buttonClick = true;

    EditText editText;
    String searchWord;

    public static final String companyKeyword = "companyKeyword";

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        Log.i(TAG, "This message will appear when the emulator has been started.");
        Log.e(TAG, "This message will also appear and this is how an error message will look like.");

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // helloText = root.findViewById(R.id.helloTextID);
        // appCreatedText = root.findViewById(R.id.appCreatedText);

        button = root.findViewById(R.id.infoButton);
        button.setOnClickListener(this);

        guessGameButton = root.findViewById(R.id.guessGameButton);
        guessGameButton.setOnClickListener(this);

        searchCompanyButton = root.findViewById(R.id.searchCompanyButton);
        searchCompanyButton.setOnClickListener(this);

        editText = root.findViewById(R.id.editTextSearchCompany);

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
                                startActivity(new Intent(getActivity(), GuessGame.class));
                                Toast toast = Toast.makeText(getContext(), R.string.gestureRightSwipe, Toast.LENGTH_LONG);
                                toast.show();
                            }

                            else {
                                startActivity(new Intent(getActivity(), GuessGame.class));
                                Toast toast = Toast.makeText(getContext(), R.string.gestureLeftSwipe, Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }
                }
                return true;
            }
        });

        /* One way to do a click event.
        Button button = (Button) findViewById(R.id.textButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("test", "Button has been clicked.");
            }
        });
         */

        return root;
    }

    /*
    public void helloTextView() {
        helloText.setVisibility(helloText.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }
     */

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            /*
            case R.id.textButton:
                Log.e("errorMSG", "Button has been clicked.");

                // Calling the function which will set "Hello" text visible and invisible.
                helloTextView();


                The code below would also set "Hello" text visible or invisible
                when button has been clicked. Remove code from the comment at the line 34.

                if (buttonClick == true) {
                    helloText.setVisibility(View.GONE);
                    buttonClick = false;
                }
                else {
                    helloText.setVisibility(View.VISIBLE);
                    buttonClick = true;
                }
                 */

                /*
                The code below would also set "Hello" text visible or invisible
                when button has been clicked.

                if (helloText.getText().length() == 0){
                    helloText.setText(R.string.appText);
                }
                else {
                    helloText.setText("");
                }

                break;
                 */

            case R.id.infoButton:
                startActivity(new Intent(getActivity(), AppInfo.class));
                break;

            case R.id.guessGameButton:
                startActivity(new Intent(getActivity(), GuessGame.class));
                break;

            case R.id.searchCompanyButton:
                searchWord = editText.getText().toString();

                Intent intent = new Intent(getActivity(), SearchCompany.class);
                intent.putExtra(companyKeyword, searchWord);
                startActivity(intent);
        }
    }
}