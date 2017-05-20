package com.example.jisung.myself;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jisung on 2017-05-20.
 */

public class alramAdapter extends BaseAdapter {
    Context context;
    ArrayList<toDo> data;

    public alramAdapter(Context context, ArrayList<toDo> data) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.alarm_list, null);
        TextView t1 = (TextView) convertView.findViewById(R.id.text1);
        CheckBox c1 = (CheckBox) convertView.findViewById(R.id.check1);
        t1.setText(data.get(position).getTitle());
        c1.setChecked(data.get(position).getAlram());
        c1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    data.get(position).setAlram(true);
                else
                    data.get(position).setAlram(false);
            }
        });
        return convertView;

    }

}
