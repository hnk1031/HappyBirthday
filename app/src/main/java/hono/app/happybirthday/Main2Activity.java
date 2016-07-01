package hono.app.happybirthday;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Calendar;

public class Main2Activity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private static final String COUNTDOWN_OPERATION = "CountdownOperation";
    private static final long INTERVAL_COUNTDOWN = 1000;
    private static int COUNTER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        Intent it = new Intent(this, TestService.class);
        it.putExtra(Intent.EXTRA_TEXT, "@@@@");
        int requestCode = 1;
        PendingIntent pendingIntent = PendingIntent.getService(this, requestCode, it, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager manager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        manager.set(AlarmManager.ELAPSED_REALTIME, 15000, pendingIntent);

    }




}


