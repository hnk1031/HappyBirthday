package com.example.hnk_1031.happybirthday;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by hnk_1031 on 16/06/30.
 */
public class AlarmReceiver extends BroadcastReceiver{
    Context mContext;


    @Override
    public void onReceive(Context context, Intent intent) {



        int messageId = intent.getIntExtra("intent_alarm_id_key", 0);

        Intent intent1 = new Intent(context, MainActivity.class);
        intent1.setAction("intent_action");
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        Bitmap largeIcon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.birthday);
        Notification.Builder builder = new Notification.Builder(mContext);
        builder.setContentTitle(mContext.getString(R.string.msg_message_alarm_title));
        builder.setContentText(mContext.getString(R.string.msg_message_alarm_content));
        builder.setSmallIcon(R.drawable.birthday);
        builder.setLargeIcon(largeIcon);
        builder.setTicker(mContext.getString(R.string.msg_message_alarm_ticker));
        builder.setAutoCancel(true);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) mContext
                .getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
