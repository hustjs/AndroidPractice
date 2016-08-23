package com.example.radical.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TweenActivity extends AppCompatActivity {
    @BindView(R.id.tv_tween)
    TextView tv_tween;

    private String TAG = "TweenActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tween);
        ButterKnife.bind(this);
        AnimationSet as=new AnimationSet(true);
        as.setDuration(2000);
        AlphaAnimation aa = new AlphaAnimation(0, 1);
        aa.setDuration(1000);
        as.addAnimation(aa);
        TranslateAnimation ta = new TranslateAnimation(0, 200, 0, 200);
        ta.setDuration(800);
        ta.setStartOffset(800);
        as.addAnimation(ta);
        tv_tween.startAnimation(as);
        as.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.i(TAG, "onAnimationStart: ");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.i(TAG, "onAnimationEnd: ");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.i(TAG, "onAnimationRepeat: ");
            }
        });
    }
}
