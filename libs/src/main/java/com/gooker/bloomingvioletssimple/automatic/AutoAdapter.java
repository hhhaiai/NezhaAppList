package com.gooker.bloomingvioletssimple.automatic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gooker.bloomingvioletssimple.R;

import java.util.ArrayList;

/**
 * Created by socoy on 2016/12/10.
 */

public class AutoAdapter extends BaseAdapter {
    private ArrayList<String> Data;
    private LayoutInflater inFlater;

    public AutoAdapter(Context context, ArrayList<String> data) {
        this.Data = data;
        inFlater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return Data.size();
    }

    @Override
    public Object getItem(int position) {
        return Data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inFlater.inflate(R.layout.auto_list_item, parent, false);
            viewHolder.Data = (TextView) convertView.findViewById(R.id.DATA);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.Data.setText(Data.get(position));
        return convertView;
    }


    private class ViewHolder {
        TextView Data;
    }

}
