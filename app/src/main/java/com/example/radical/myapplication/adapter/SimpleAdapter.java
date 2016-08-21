package com.example.radical.myapplication.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.radical.myapplication.R;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c)
 * Title.
 * <p/>
 * Description.
 *
 * @author radical
 * @version 1.0
 * @since 2016-08-20
 */
public class SimpleAdapter extends BaseAdapter {
    private static final String[] CONTENTS = "The quick brown fox jumps over the lazy dog".split(" ");
    private final LayoutInflater inflater;
    @BindDrawable(R.drawable.bg_gradient)
    Drawable bg;

    public SimpleAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return CONTENTS.length;

    }

    @Override
    public String getItem(int i) {
        return CONTENTS[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = inflater.inflate(R.layout.list_item, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        String word = getItem(i);
        holder.word.setText("Word: " + word);
        holder.length.setText("Length: " + word.length());
        holder.position.setText("Position: " + i);
        holder.imageView.setImageDrawable(bg);
        // Note: don't actually do string concatenation like this in an adapter's getView.

        return view;
    }

    static final class ViewHolder {
        @BindView(R.id.word)
        TextView word;
        @BindView(R.id.length)
        TextView length;
        @BindView(R.id.position)
        TextView position;
        @BindView(R.id.bg)
        ImageView imageView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
