package ru.techpark.agregator;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import ru.techpark.agregator.network.EventApi;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

public class NotificationWorker extends Worker {
    private final static String CHANEL_ID = "2342";
    private final static String CHANEL_NAME = "CHANEL_NAME";
    public final static String ACTION_TO_OPEN = "ACTION_TO_OPEN";
    public final static String OPEN_FRAGMENT_ID = "OPEN_FRAGMENT_ID";

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel =
                    new NotificationChannel(CHANEL_ID, CHANEL_NAME, importance);
            String description = "A channel which shows notifications about events";
            channel.setDescription(description);
            NotificationManager notificationManager = (NotificationManager) getApplicationContext().
                    getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
        int event_id = getInputData().getInt(DetailedEventFragment.KEY_ID, 0);
        String event_title = getInputData().getString(DetailedEventFragment.KEY_TITLE);
        String event_date = getInputData().getString(DetailedEventFragment.KEY_DATE);
        String event_time = getInputData().getString(DetailedEventFragment.KEY_TIME);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setAction(ACTION_TO_OPEN);
        intent.putExtra(OPEN_FRAGMENT_ID, event_id);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(getApplicationContext(),  1, intent, FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getApplicationContext(), CHANEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(event_title)
                .setContentText("У вас запланировано событие " +event_date+ " в "+ event_time)
                .setStyle(new NotificationCompat.BigTextStyle()
                .bigText("У вас запланировано событие " +event_date+ " в "+ event_time))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(event_id, notificationBuilder.build());
        return Result.success();
    }
}
