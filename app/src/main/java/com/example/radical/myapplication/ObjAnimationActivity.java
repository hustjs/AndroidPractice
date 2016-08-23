package com.example.radical.myapplication;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ObjAnimationActivity extends AppCompatActivity {

    @BindView(R.id.tv_objAnim)
    TextView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obj_animation);
        ButterKnife.bind(this);
        ObjectAnimator animator=ObjectAnimator.ofFloat(view,"translateX",600);
        animator.setDuration(3000);
        animator.start();
    }
}
