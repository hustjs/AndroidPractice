package com.example.radical.myapplication.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.radical.myapplication.R;
import com.example.radical.myapplication.fragment.BlankFragment;

/**
 * Copyright (c)
 * Title.
 * <p/>
 * Description.
 *
 * @author radical
 * @version 1.0
 * @since 2016-08-21
 */
public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
    final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[]{"微信", "通讯录", "发现","我"};
    private Context context;

    private int[] imageResId = {
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher
    };

    public FragmentPagerAdapter(FragmentManager fm,Context context) {
        super(fm);
        this.context=context;
    }

    @Override
    public Fragment getItem(int position) {
        return BlankFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        // return tabTitles[position];

//            Drawable image = context.getResources().getDrawable(imageResId[position]);
//            image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
//            SpannableString sb = new SpannableString(" ");
//            ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
//            sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            return sb;
        return null;

    }

    /**
     * set a custom view to TabLayout
     * @param position
     * @return
     */
    public View addTabView(int position){
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        View view = LayoutInflater.from(context).inflate(R.layout.tab_item, null);
        TextView tv= (TextView) view.findViewById(R.id.textView);
        tv.setText(tabTitles[position]);
        ImageView img = (ImageView) view.findViewById(R.id.imageView);
        img.setImageResource(imageResId[position]);
        return view;
    }
}
