package com.example.jisung.myself;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jisung on 2017-05-10.
 */

public class schAdapter extends BaseAdapter {
    Context context;
    ArrayList<toDo> data;

    public schAdapter(Context context, ArrayList<toDo> data) {
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
            t1.setText(data.get(position).getTitle());
            TextView dtime = (TextView)convertView.findViewById(R.id.totime);
            dtime.setText(data.get(position).getCreateDate());
            return convertView;

    }
}
