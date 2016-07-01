package hono.app.happybirthday;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by hnk_1031 on 16/06/30.
 */
public class TestService extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String text = intent.getStringExtra(Intent.EXTRA_TEXT);
        Log.e("TAG",text);

        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        Log.d("test", "onReceive");
//

        int messageId = intent.getIntExtra("intent_alarm_id_key", 0);




        Intent intent1 = new Intent(this, MainActivity.class);
        intent1.setAction("intent_action");
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        Bitmap largeIcon = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.birthday);
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        builder.setContentTitle(Integer.valueOf(MainActivity.mBirthday.size()) + "人");
        builder.setContentText("Today's Birthday");
        builder.setSmallIcon(R.drawable.birthday);
        builder.setLargeIcon(largeIcon);
        builder.setTicker("HappyBirthday");
        builder.setAutoCancel(true);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
        return TestService.START_NOT_STICKY;
    }
}