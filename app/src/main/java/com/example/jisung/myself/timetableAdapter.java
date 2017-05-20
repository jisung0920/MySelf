package com.example.jisung.myself;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by jisung on 2017-05-21.
 */

public class timetableAdapter extends BaseAdapter {
    Context context;
    ArrayList<Schedule> data;

    public timetableAdapter(Context context, ArrayList<Schedule> data) {
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
        if (convertView == null)
            convertView = inflater.inflate(R.layout.schedule_todo_list, null);
        return null;
    }
}
