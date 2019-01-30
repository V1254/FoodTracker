package test.mo.FoodTracker;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;


public class ExpiryPublisher extends BroadcastReceiver {
    private static final String TAG = "ExpiryPublisher";
    public static int NOTIF_ID = 0;
    public static String notif = "Expiry Notification";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        Notification notification = intent.getParcelableExtra("notification");
        int id = intent.getIntExtra("unique_id", NOTIF_ID);
        Log.d(TAG, "onReceive: id is " + id);
        notificationManager.notify(id, notification);
        notificationManager.notify(-1, getSummaryNotification(context));

    }


    private Notification getSummaryNotification(Context c) {
        Notification summaryNotification =
                new NotificationCompat.Builder(c, "foodtracker-id")
                        .setContentTitle("Food Expiration's")
                        //set content text to support devices running API level < 24
                        .setContentText("Items about to Expire")
                        .setSmallIcon(R.drawable.notif_icon)
                        //build summary info into InboxStyle template
                        .setStyle(new NotificationCompat.InboxStyle()
                                .setSummaryText("Multiple Items about to Expire!"))
                        //specify which group this notification belongs to
                        .setGroup("foodtracker-group")
                        //set this notification as the summary for the group
                        .setGroupSummary(true).build();

        return summaryNotification;

    }

}
