package com.example.jisung.myself;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jisung on 2017-05-22.
 */

public class SchAdapterMain extends BaseAdapter {
    Context context;
    ArrayList<Schedule> data;

    public SchAdapterMain(Context context, ArrayList<Schedule> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);

        if(convertView==null);
        convertView = inflater.inflate(R.layout.schedule_todo_list,null);
        TextView t1 = (TextView) convertView.findViewById(R.id.t1);
        t1.setText(data.get(position).getName());
        TextView dtime = (TextView)convertView.findViewById(R.id.totime);
        dtime.setText(data.get(position).getTime());
        return convertView;

    }
}
