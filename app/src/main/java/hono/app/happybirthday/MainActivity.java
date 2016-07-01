package hono.app.happybirthday;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RemoteViews;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private final int GMAIL =0;
    private final int FACEBOOK =1;
    private final int TWITTER =2;
    private final String[] sharePackages = {"com.google.android.gm","jp.naver.line.android","com.facebook.katana","com.twitter.android"};


    ArrayList<NavigationListContent> mPlanetTitles;
    ListView mDrawerList;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    Toolbar mToolbar;
    NavigationCustomAdapter mCustomAdapter;
    ListView listView;
    ListAdapter listAdapter;
    ArrayList<CustomContent> arrayList;
    ArrayList<String> mDate;
    ArrayList<String> mName;
    static ArrayList<String> mBirthday;
    String now;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDate = new ArrayList<String>();
        mName = new ArrayList<String>();
        mBirthday = new ArrayList<String>();
        arrayList = new ArrayList<CustomContent>();


        Intent intent = getIntent();




        if (intent.getStringArrayListExtra("birth") != null) {

            mBirthday.addAll(intent.getStringArrayListExtra("birth"));
        }

        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);


        mToolbar.setTitle("Birthday");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);


        mPlanetTitles = new ArrayList<NavigationListContent>();
        mPlanetTitles.add(new NavigationListContent("GMAIL", R.drawable.gmail));
        mPlanetTitles.add(new NavigationListContent("Facebook", R.drawable.facebook));
        mPlanetTitles.add(new NavigationListContent("Twitter", R.drawable.twitter));
        mCustomAdapter = new NavigationCustomAdapter(this, R.layout.custom_layout, mPlanetTitles);

        mDrawerList.setAdapter(mCustomAdapter);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                mToolbar,
                R.string.open,
                R.string.close
        ) {
        };

        Uri uri = ContactsContract.Data.CONTENT_URI;
        String[] projection = new String[] { ContactsContract.CommonDataKinds.Event.CONTACT_ID,
                ContactsContract.CommonDataKinds.Event.DISPLAY_NAME, ContactsContract.CommonDataKinds.Event.DATA, ContactsContract.CommonDataKinds.Event.TYPE };
        String selection = ContactsContract.Contacts.Data.MIMETYPE + "=? AND (" + ContactsContract.CommonDataKinds.Event.TYPE + "=? OR "
                + ContactsContract.CommonDataKinds.Event.TYPE + "=?) ";
        String[] selectionArgs = new String[] { ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE,
                String.valueOf(ContactsContract.CommonDataKinds.Event.TYPE_ANNIVERSARY),
                String.valueOf(ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY) };

        Cursor c1 = getContentResolver().query(uri, projection, selection,
                selectionArgs, null);

        final Calendar mCalendar = Calendar.getInstance();
        final int mYear = mCalendar.get(Calendar.YEAR);
        final int mMonth = mCalendar.get(Calendar.MONTH)+1;
        final int mDay = mCalendar.get(Calendar.DAY_OF_MONTH);

        if (c1 != null) {
            try {
                while (c1.moveToNext()) {
                    // コンタクトユーザのリストを作成
                    String displayName = c1.getString(c1
                            .getColumnIndex(ContactsContract.CommonDataKinds.Event.DISPLAY_NAME));
                    String date = c1.getString(c1.getColumnIndex(ContactsContract.CommonDataKinds.Event.DATA));
                    Log.e("TAG",date);

                    if (mMonth <10 && mDay<10){
                        now =String.valueOf(mYear)+"0"+String.valueOf(mMonth)+"0"+String.valueOf(mDay);
                    } else if (mMonth <10 && mDay >=10) {
                        now =String.valueOf(mYear)+"0"+String.valueOf(mMonth)+String.valueOf(mDay);
                    } else if (mMonth >=10 && mDay<10) {
                        now =String.valueOf(mYear)+String.valueOf(mMonth)+"0"+String.valueOf(mDay);
                    } else {
                        now =String.valueOf(mYear)+String.valueOf(mMonth)+String.valueOf(mDay);
                    }


                    mDate.add(date);
                    mName.add(displayName);

                    arrayList.add(new CustomContent(displayName,date,getAge(date,now)));



                }
            } finally {
                c1.close();
            }

        }



        //アプリintent 
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override  
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) { 
                if (i == 0) { 
                    if (isShareAppInstall(GMAIL)) { 
                        Intent intent2 = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:")); 
                        intent2.setPackage(sharePackages[GMAIL]); 
                        intent2.putExtra(Intent.EXTRA_SUBJECT, "メールの題名"); 
                        intent2.putExtra(Intent.EXTRA_TEXT, "メールの内容"); 
                        startActivity(intent2); 
                    } else { 
                        shareAppDl(GMAIL); 
                    } 
                }  
                if (i == 1) { 
                    if (isShareAppInstall(FACEBOOK)) { 
                        Intent intent3 = new Intent(); 
                        intent3.setAction(Intent.ACTION_SEND); 
                        intent3.setPackage(sharePackages[FACEBOOK]); 
                        intent3.setType("text/plain"); 

                        intent3.putExtra(Intent.EXTRA_TEXT, "http://www.yahoo.co.jp/"); 
                        startActivity(intent3); 
                    } else { 
                        shareAppDl(FACEBOOK); 
                    } 
                } 
                if (i == 2) { 
                    if (isShareAppInstall(TWITTER)) { 
                        Intent intent4 = new Intent(); 
                        intent4.setAction(Intent.ACTION_SEND); 
                        intent4.setPackage(sharePackages[TWITTER]); 
                        intent4.setType("image/png"); 
                        intent4.putExtra(Intent.EXTRA_TEXT, "Twitter本文"); 
                        startActivity(intent4); 
                    } else { 
                        shareAppDl(TWITTER); 
                    } 
                } 
            } 
        }); 


    }
       private Boolean isShareAppInstall(int shareId) { 
        try { 
            PackageManager pm = getPackageManager(); 
            pm.getApplicationInfo(sharePackages[shareId], PackageManager.GET_META_DATA); 
            return true; 
        } catch (PackageManager.NameNotFoundException e) { 
            return false; 
        }
    }
          private void shareAppDl(int shareId){ 
        Uri uri = Uri.parse("market://details?id="+sharePackages[shareId]); 
        Intent intent = new Intent(Intent.ACTION_VIEW, uri); 
        startActivity(intent); }





    public String getAge(String date,String now){
        StringBuilder stringBuilder=new StringBuilder(date);
        stringBuilder.deleteCharAt(4);
        stringBuilder.deleteCharAt(6);

        String birth = stringBuilder.toString();

        int b = Integer.parseInt(birth);
        int n =Integer.parseInt(now);

        int age = (n - b)/10000;

        return String.valueOf(age);

    }

    public class BirthdayFormat {
        final Boolean logstatus = false;

        // 今日の日付取得
        final Calendar mCalendar = Calendar.getInstance();
        final int mYear = mCalendar.get(Calendar.YEAR);
        final int mMonth = mCalendar.get(Calendar.MONTH);
        final int mDay = mCalendar.get(Calendar.DAY_OF_MONTH);

        @SuppressWarnings("unused")
        public String DateCheck(String date) {
            int check_year = 0;
            int check_month = 0;
            int check_day = 0;
            int check_date = 0;
            String date_tmp = null;

            if (date.length() > 10) { // 文字数が多い場合は対象外
                return null;

            } else if (date.length() == 10) { // 文字数は正しい
                if (date.indexOf("-") != -1 && date.substring(4, 5).equals("-")
                        && date.substring(7, 8).equals("-")) {

                    try {
                        check_year = Integer.parseInt(date.substring(0, 4));
                        check_month = Integer.parseInt(date.substring(5, 7));
                        check_day = Integer.parseInt(date.substring(8, 10));

                        if (check_month == 0 || check_month > 12) {
                            return null;
                        } else if (check_day == 0 || check_day > 31) {
                            return null;
                        } else {
                            return date; // 正しいのでそのまま返す
                        }

                    } catch (Exception e) {
                        Log.e("Birth2Cal", e + " [target] " + date);
                        return null;
                    }

                } else { // 10文字だけど”-”がない
                    try {
                        check_year = Integer.parseInt(date.substring(0, 4));
                        check_month = Integer.parseInt(date.substring(5, 7));
                        check_day = Integer.parseInt(date.substring(8, 10));

                        if (check_month == 0 || check_month > 12) {
                            return null;
                        } else if (check_day == 0 || check_day > 31) {
                            return null;
                        } else {
                            date_tmp = date.substring(0, 4) + "-"
                                    + date.substring(5, 7) + "-"
                                    + date.substring(8, 10);
                            return date_tmp;
                        }
                    } catch (Exception e) {
                        Log.e("Birth2Cal", e + " [target] " + date);
                        return null;
                    }
                }

            } else if (date.length() < 10) { // 文字数が正しくない
                int hit[] = { 0, 0, 0 };
                int hit_point = 0;

                for (int i = 0; i < date.length(); i++) {
                    try {
                        // 1文字ずつ拾ってきて文字か数値か判定。文字ならエラーになる。
                        check_date = Integer.parseInt(date.substring(i, i + 1));
                    } catch (Exception e) {
                        // 文字ならヒットした位置とヒットした数を記録
                        try {
                            hit[hit_point] = i;
                            hit_point++;

                            // ヒットが3つ以上になった場合は対象外
                            if (hit_point > 2) {
                                return null;
                            }
                        } catch (Exception e2) {
                            Log.e("Birth2Cal", e + " [target] " + date);
                            return null; // 範囲外になったときように念のため
                        }
                    }
                }

                if (hit_point == 2) {
                    try {
                        if (hit[0] < 4 || (hit[1] - hit[0]) < 1
                                || date.length() - hit[1] < 1) {
                            return null; // 年が4桁以下か月/日が0桁以下のとき
                        } else {
                            NumberFormat nf1 = new DecimalFormat("0000");
                            NumberFormat nf2 = new DecimalFormat("00");
                            check_year = Integer
                                    .parseInt(date.substring(0, hit[0]));
                            check_month = Integer.parseInt(date.substring(
                                    hit[0] + 1, hit[1]));
                            check_day = Integer.parseInt(date.substring(hit[1] + 1,
                                    date.length()));

                            if (check_month == 0 || check_month > 12) {
                                return null;
                            } else if (check_day == 0 || check_day > 31) {
                                return null;
                            } else {
                                date_tmp = nf1.format(check_year) + "-"
                                        + nf2.format(check_month) + "-"
                                        + nf2.format(check_day);
                                return date_tmp;
                            }
                        }
                    } catch (Exception e) {
                        Log.e("Birth2Cal", e + " [target] " + date);
                        return null;
                    }

                } else if (hit_point == 0 && date.length() == 8) { // 区切り文字がないけど20000101なので判定できる
                    try {
                        check_year = Integer.parseInt(date.substring(0, 4));
                        check_month = Integer.parseInt(date.substring(4, 6));
                        check_day = Integer.parseInt(date.substring(6, 8));

                        if (check_month == 0 || check_month > 12) {
                            return null;
                        } else if (check_day == 0 || check_day > 31) {
                            return null;
                        } else {
                            date_tmp = date.substring(0, 4) + "-"
                                    + date.substring(4, 6) + "-"
                                    + date.substring(6, 8);
                            return date_tmp;
                        }
                    } catch (Exception e) {
                        Log.e("Birth2Cal", e + " [target] " + date);
                        return null;
                    }
                } else { // 2000111みたいな1月11日なのか11月1日なのか判定不可
                    return null;
                }
            } else {
                return null;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("test", "onResume");
        listView =(ListView) findViewById(R.id.listView);
        listAdapter =new ListAdapter(this,android.R.layout.simple_list_item_1,arrayList);

        listView.setAdapter(listAdapter);
    }


    public void reload(View v) {
        // 今日の日付取得
        final Calendar mCalendar = Calendar.getInstance();
        final int mMonth = mCalendar.get(Calendar.MONTH);
        final int mDay = mCalendar.get(Calendar.DAY_OF_MONTH);

        Log.e("now1",now);
        String nowDate = new String(now);
        StringBuilder nowStringBuilder = new StringBuilder(nowDate);
        nowStringBuilder.delete(0,4);
        String nowDay =nowStringBuilder.toString();
        Log.e("now2",nowDay);
        mBirthday.clear();



        for (int i =0 ; i < mDate.size();i++){

            StringBuilder stringBuilder =new StringBuilder(mDate.get(i));
            stringBuilder.deleteCharAt(4);
            stringBuilder.deleteCharAt(6);
            stringBuilder.delete(0,4);
            String birth =stringBuilder.toString();
            Log.e("birth",birth);

            if (nowDay.equals(birth)) {
                mBirthday.add(mName.get(i));
            }

        }





            //初期設定
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
            builder.setSmallIcon(R.drawable.icon);


            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            month++;
            int day = cal.get(Calendar.DAY_OF_MONTH);


            //CustomLayout
            RemoteViews customView = new RemoteViews(getPackageName(), R.layout.notification);
            customView.setTextViewText(R.id.text1, String.valueOf(mBirthday.size()) + "人");
            customView.setTextViewText(R.id.text2, "Today's Birthday");
            customView.setTextViewText(R.id.text3, year + "/" + month + "/" + day);
            customView.setTextColor(R.id.text1, Color.WHITE);
            customView.setTextColor(R.id.text2, Color.WHITE);
            customView.setTextColor(R.id.text3, Color.WHITE);
            customView.setImageViewResource(R.id.imageView, R.drawable.birthday);
            builder.setContent(customView);

            //クリック時の処理
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            intent.putStringArrayListExtra("birth",mBirthday);
            PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);
            builder.setAutoCancel(true);

            //作成
            NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
            manager.notify(0, builder.build());



    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
}

