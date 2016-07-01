package hono.app.happybirthday;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by hnk_1031 on 16/06/29.
 */
public class ListAdapter extends ArrayAdapter<CustomContent>{
    private ArrayList<CustomContent> list = new ArrayList<>();

    static class ViewHolder{
        TextView title;
        TextView birthday;
        TextView age;

    }

    public ListAdapter(Context context, int layoutId, ArrayList<CustomContent> lists) {
        super(context, layoutId, lists);

        list = lists;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.Title);
            viewHolder.birthday=(TextView)convertView.findViewById(R.id.Birthday1);
            viewHolder.age=(TextView)convertView.findViewById(R.id.Age1);
            convertView.setTag(viewHolder);
        }

        CustomContent customContent = list.get(position);

        Log.e("TAG",customContent.getBirthday().substring(5, 10));

        viewHolder.title.setText(customContent.getTitle());
        viewHolder.birthday.setText(customContent.getBirthday());
        viewHolder.age.setText(customContent.getAge());

        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String today = "";
        if (month < 10) {
            if (day < 10) {
                today = "0" + String.valueOf(month) + "-" + "0" + String.valueOf(day);
            } else {
                today = "0" + String.valueOf(month) + "-" + String.valueOf(day);
            }
        } else {
            if (day < 10) {
                today = String.valueOf(month) + "-" + "0" + String.valueOf(day);
            } else {
                today = String.valueOf(month) + "-" + String.valueOf(day);
            }
        }

        if (customContent.getBirthday().substring(5, 10).equals(today)) {
            viewHolder.title.setTextColor(Color.parseColor("#EF6C00"));
            viewHolder.title.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            viewHolder.title.setTextColor(Color.parseColor("#FFA500"));
            viewHolder.title.setTypeface(Typeface.DEFAULT);
        }

        return convertView;
    }

}
