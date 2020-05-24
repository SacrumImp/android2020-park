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

import ru.techpark.agregator.fragments.ApiDetailedFragment;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

public class NotificationWorker extends Worker {
    final static String ACTION_TO_OPEN = "ACTION_TO_OPEN";
    final static String OPEN_FRAGMENT_ID = "OPEN_FRAGMENT_ID";
    private final static String CHANEL_ID = "2342";
    private final static String CHANEL_NAME = "CHANEL_NAME";
    private Context context;

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
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
        int event_id = getInputData().getInt(ApiDetailedFragment.KEY_ID, 0);
        String event_title = getInputData().getString(ApiDetailedFragment.KEY_TITLE);
        String event_date = getInputData().getString(ApiDetailedFragment.KEY_DATE);
        String event_time = getInputData().getString(ApiDetailedFragment.KEY_TIME);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setAction(ACTION_TO_OPEN);
        intent.putExtra(OPEN_FRAGMENT_ID, event_id);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(getApplicationContext(), 1, intent, FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getApplicationContext(), CHANEL_ID)
                        .setSmallIcon(R.mipmap.ic_stat_directions_walk)
                        .setContentTitle(event_title)
                        .setContentText(context.getString(R.string.notification_text_first_part)
                                + event_date + context.getString(R.string.notification_text_second_part) + event_time)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(context.getString(R.string.notification_text_first_part) + event_date +
                                        context.getString(R.string.notification_text_second_part) + event_time))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(event_id, notificationBuilder.build());
        return Result.success();
    }
}
