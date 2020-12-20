package com.jonne.kiukas;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class InternetReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork == null) {
            // not connected to the internet
            Toast.makeText(context, (R.string.noInternetConnection), Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(context, (R.string.internetConnected), Toast.LENGTH_SHORT).show();
        }
    }
}