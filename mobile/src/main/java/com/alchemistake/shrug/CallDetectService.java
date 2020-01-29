package com.alchemistake.shrug;

import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;

import java.util.List;

public class CallDetectService extends NotificationListenerService {
    Context context;
    boolean vibrate;
    protected Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        vibrate = false;
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        vibeCheck(sbn, true);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        vibeCheck(sbn, false);
    }

    void vibeCheck(StatusBarNotification sbn, boolean posted) {
        String packageName = sbn.getPackageName();
        if (packageName.contains("telegram")) {
            if (sbn.getNotification().getChannelId().equals("incoming_calls20")) {
                Log.v("CANER", String.valueOf(posted));
                vibrate = posted;
                new SendThread(vibrate).start();
            }
        }


        if (packageName.contains("whatsapp")) {
            AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

            assert am != null;
            final int mode = am.getMode();

            if (mode == AudioManager.MODE_RINGTONE) {
                if (!vibrate) {
                    vibrate = true;
                    new SendThread(vibrate).start();
                }
            } else {
                if (vibrate) {
                    vibrate = false;
                    new SendThread(vibrate).start();
                }
            }
        }
    }

    class SendThread extends Thread {
        boolean status;

        //constructor
        SendThread(boolean status) {
            this.status = status;
        }

        public void run() {
            String path = this.status ? "/shrug_start" : "/shrug_stop";
            Task<List<Node>> nodeListTask =
                    Wearable.getNodeClient(getApplicationContext()).getConnectedNodes();
            try {
                List<Node> nodes = Tasks.await(nodeListTask);

                for (Node node : nodes) {
                    Task<Integer> sendMessageTask =
                            Wearable.getMessageClient(CallDetectService.this).sendMessage(node.getId(), path, "".getBytes());

                    Integer result = Tasks.await(sendMessageTask);

                }
            } catch (Exception exception) {

            }
        }
    }
}
