package com.coffeemeetsbagel.praneethambati.meettheteam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Praneeth Ambati on 2/14/2017.
 */
public class CustomListAdapter extends BaseAdapter {
    private List<PersonModel> listData;
    private LayoutInflater layoutInflater;

    public CustomListAdapter(Context context, List<PersonModel> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.activity_landing_listview_layout, null);
            holder = new ViewHolder();
            holder.icon = (ImageView)convertView.findViewById(R.id.listview_image);
            holder.text = (TextView)convertView.findViewById(R.id.listview_item_title);
            holder.subtext = (TextView)convertView.findViewById(R.id.listview_item_short_description);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text.setText(listData.get(position).getFirstName());
        holder.subtext.setText(listData.get(position).getTitle());

        if (holder.icon != null) {
            new BitmapWorkerTask(holder.icon).execute(listData.get(position).getAvatar());
        }
        return convertView;
    }

    class ViewHolder {
        ImageView icon;
        TextView text,subtext;

    }
}

