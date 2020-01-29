package com.alchemistake.shrug;

import android.content.IntentFilter;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class TestActivity extends WearableActivity {

    private TextView mTextView;
    private Vibrator vibrator;
    VibrationEffect vibrationEffect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mTextView = (TextView) findViewById(R.id.text);

        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);
        MessageReceiver messageReceiver = new MessageReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, messageFilter);

        vibrationEffect = VibrationEffect.createWaveform(new long[]{0, 2500, 500}, 1);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        assert vibrator != null;
        vibrator.vibrate(vibrationEffect);
    }

    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            vibrator.cancel();
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        vibrator.cancel();
        super.onDestroy();
    }
}
