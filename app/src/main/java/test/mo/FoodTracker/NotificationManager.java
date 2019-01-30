package test.mo.FoodTracker;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Date;

import test.mo.FoodTracker.Model.Food;

public class NotificationManager {
    private static final String NOTIF_TITLE = "Item Expiring!";
    private static final String GROUP = "foodtracker-group";
    private static final String TAG = "NotificationManager";
    private final String CHANNEL_ID;
    private Context c;


    public NotificationManager(Context c, String channel_id) {
        this.c = c;
        this.CHANNEL_ID = channel_id;
    }

    public void scheduleNotification(Food food) {
        if (System.currentTimeMillis() >= food.getExpiryDate())
            return;
        Notification notification = getNotification(food.getFoodName(), food.getCategory());

        Intent intent = new Intent(c, ExpiryPublisher.class);
        int uniqueId = (int) (food.getId() + SystemClock.elapsedRealtime());
        intent.putExtra("unique_id", uniqueId);
        intent.putExtra("notification", notification);

        PendingIntent pend = PendingIntent.getBroadcast(c, uniqueId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // one day before the expiration date
        long timeInFuture = food.getExpiryDate() - 86400000;

        Log.d(TAG, "scheduleNotification: Time being passed: " + timeInFuture);


        Date d2 = new Date(food.getExpiryDate());
        Date d = new Date(timeInFuture);
        Date d3 = new Date(SystemClock.elapsedRealtime());

        Log.d(TAG, "scheduleNotification: date " + d.toString());
        Log.d(TAG, "scheduleNotification: expiration date  " + d2.toString());
        Log.d(TAG, "scheduleNotification: android time  " + d3.toString());
        AlarmManager manager = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
        manager.set(AlarmManager.RTC, timeInFuture, pend);
    }

    private Notification getNotification(String name, String category) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(c, CHANNEL_ID)
                .setSmallIcon(R.drawable.notif_icon)
                .setLargeIcon(BitmapFactory.decodeResource(c.getResources(), DrawableManager.getDrawable(category)))
                .setContentTitle(NOTIF_TITLE)
                .setContentText(String.format("Your item %s will Expire in One Day!", name))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setGroup(GROUP)
                .setAutoCancel(true);

        return builder.build();
    }
}
