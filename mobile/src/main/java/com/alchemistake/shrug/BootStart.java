package com.alchemistake.shrug;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootStart extends BroadcastReceiver {
    public void onReceive(Context context, Intent arg1) {
        Intent intent = new Intent(context, CallDetectService.class);
        context.startService(intent);
    }
}
