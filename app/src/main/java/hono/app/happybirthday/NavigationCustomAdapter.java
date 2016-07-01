package hono.app.happybirthday;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hnk_1031 on 16/06/29.
 */
public class NavigationCustomAdapter extends ArrayAdapter<NavigationListContent>{

    static class ViewHolder {
        TextView text;
        ImageView image;
    }

    public NavigationCustomAdapter(Context context, int layoutId, ArrayList<NavigationListContent> lists) {
        super(context, layoutId, lists);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) convertView.findViewById(R.id.textView);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(viewHolder);

        }

        final NavigationListContent listContent = getItem(position);


        viewHolder.text.setText(listContent.getText());
        viewHolder.image.setImageResource(listContent.getResId());


        return convertView;
    }

}
