package com.jonne.kiukas;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;

public class ApmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        if (isAirplaneModeOn(context)) {
            Toast.makeText(context, (R.string.airplaneModeOn), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, (R.string.airplaneModeOff), Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isAirplaneModeOn(Context context) {
        return Settings.System.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
    }
}
