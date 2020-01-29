package com.alchemistake.shrug;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificationManager n = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (n.isNotificationPolicyAccessGranted()) {
            } else {
                startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));
            }
        }

        Intent intent = new Intent(MainActivity.this, CallDetectService.class);
        startService(intent);
    }
}
