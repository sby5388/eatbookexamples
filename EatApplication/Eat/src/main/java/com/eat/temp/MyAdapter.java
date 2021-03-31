package com.eat.temp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eat.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator  on 2019/8/29.
 */
class MyAdapter extends BaseAdapter {
    private List<Man> mManList = new ArrayList<>();

    public void setManList(List<Man> manList) {
        mManList = manList;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mManList.size();
    }

    @Override
    public Man getItem(int position) {
        return mManList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_temp, parent, false);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTextViewName.setText(getItem(position).mName);
        return convertView;
    }

    private static class ViewHolder {
        final ImageView mImageView;
        private final TextView mTextViewName;

        ViewHolder(View view) {
            mTextViewName = (TextView) view.findViewById(R.id.textView_name_temp);
            mImageView = (ImageView) view.findViewById(R.id.image_view_temp);
        }
    }


    private static class Man {
        private final int mAge;
        private final String mName;

        private Man(int age, String name) {
            mAge = age;
            mName = name;
        }
    }
}