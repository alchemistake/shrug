package com.alchemistake.shrug;

import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public class ListenerService extends WearableListenerService {
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        if (messageEvent.getPath().equals("/shrug_start")) {
            Intent dialogIntent = new Intent(this, TestActivity.class);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(dialogIntent);
        } else if (messageEvent.getPath().equals("/shrug_stop")) {
            Intent messageIntent = new Intent();
            messageIntent.setAction(Intent.ACTION_SEND);
            messageIntent.putExtra("path", messageEvent.getPath());
            LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent);
        }
    }

}
