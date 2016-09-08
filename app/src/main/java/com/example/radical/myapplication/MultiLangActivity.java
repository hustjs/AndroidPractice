package com.example.radical.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.radical.myapplication.utils.Utils;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Copyright (c)
 * Title.
 * <p/>
 * Description.
 *
 * @author radical
 * @version 1.0
 * @since 2016-08-12
 */
public class MultiLangActivity extends Activity {
    private String TAG = "MultiLangActivity";
    @BindView(R.id.tv_welcome)
    TextView welcom;
    @BindViews({R.id.tv_password, R.id.tv_username})
    List<EditText> nameViews;
    @BindView(R.id.btn_login)
    Button login;
    @BindColor(R.color.colorPrimary)
    int color_lg;
    @BindDimen(R.dimen.activity_vertical_margin)
    int margin_v;
    @BindString(R.string.welcome)
    String wel;
    @BindDrawable(R.drawable.bg_form_rounded)
    Drawable graphic;

    @OnClick(R.id.btn_login)
    public void login() {
        Toast.makeText(MultiLangActivity.this, "has signed in", Toast.LENGTH_SHORT).show();
        change();
    }

    @OnClick({R.id.btn_login, R.id.sign_up})
    public void lor(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                Toast.makeText(MultiLangActivity.this, "has signed in", Toast.LENGTH_SHORT).show();
                change();
                break;
            case R.id.sign_up:
                Toast.makeText(MultiLangActivity.this, "has signed up", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MultiLangActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private Unbinder unbinder;

    private void change() {
        login.setText(wel);
        login.setWidth(margin_v);
//        login.setBackgroundColor(color_lg);
        login.setBackground(graphic);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.material);
        unbinder = ButterKnife.bind(this);
        Log.i("Multi onCreate", "" + getTaskId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        Log.i(TAG, "onDestroy: ");
    }
}
