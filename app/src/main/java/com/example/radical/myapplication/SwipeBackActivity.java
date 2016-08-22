package com.example.radical.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioGroup;

import com.example.radical.myapplication.utils.Utils;

import me.imid.swipebacklayout.lib.SwipeBackLayout;

public class SwipeBackActivity extends me.imid.swipebacklayout.lib.app.SwipeBackActivity implements View.OnClickListener{

    private static final int VIBRATE_DURATION = 20;

    private int[] mBgColors;

    private static int mBgIndex = 0;

    private String mKeyTrackingMode;

    private RadioGroup mTrackingModeGroup;

    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_back);
        findViews();
        mKeyTrackingMode = getString(R.string.key_tracking_mode);
        mSwipeBackLayout = getSwipeBackLayout();

        mTrackingModeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int edgeFlag;
                switch (checkedId) {
                    case R.id.mode_left:
                        edgeFlag = SwipeBackLayout.EDGE_LEFT;
                        break;
                    case R.id.mode_right:
                        edgeFlag = SwipeBackLayout.EDGE_RIGHT;
                        break;
                    case R.id.mode_bottom:
                        edgeFlag = SwipeBackLayout.EDGE_BOTTOM;
                        break;
                    default:
                        edgeFlag = SwipeBackLayout.EDGE_ALL;
                }
                // 设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL,EDGE_BOTTOM
                mSwipeBackLayout.setEdgeTrackingEnabled(edgeFlag);
                saveTrackingMode(edgeFlag);
            }
        });
        mSwipeBackLayout.addSwipeListener(new SwipeBackLayout.SwipeListener() {
            @Override
            public void onScrollStateChange(int state, float scrollPercent) {

            }

            @Override
            public void onEdgeTouch(int edgeFlag) {
                vibrate(VIBRATE_DURATION);
            }

            @Override
            public void onScrollOverThreshold() {
                vibrate(VIBRATE_DURATION);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                startActivity(new Intent(SwipeBackActivity.this, SwipeBackActivity.class));
                break;
            case R.id.btn_finish:
                scrollToFinishActivity();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        restoreTrackingMode();
    }

    private void saveTrackingMode(int flag) {
        Utils.setPrefInt(getApplicationContext(), mKeyTrackingMode, flag);
    }

    private void restoreTrackingMode() {
        int flag = Utils.getPrefInt(getApplicationContext(), mKeyTrackingMode,
                SwipeBackLayout.EDGE_LEFT);
        mSwipeBackLayout.setEdgeTrackingEnabled(flag);
        switch (flag) {
            case SwipeBackLayout.EDGE_LEFT:
                mTrackingModeGroup.check(R.id.mode_left);
                break;
            case SwipeBackLayout.EDGE_RIGHT:
                mTrackingModeGroup.check(R.id.mode_right);
                break;
            case SwipeBackLayout.EDGE_BOTTOM:
                mTrackingModeGroup.check(R.id.mode_bottom);
                break;
            case SwipeBackLayout.EDGE_ALL:
                mTrackingModeGroup.check(R.id.mode_all);
                break;
        }
    }

    private void findViews() {
        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_finish).setOnClickListener(this);
        mTrackingModeGroup = (RadioGroup) findViewById(R.id.tracking_mode);
    }

    private int[] getColors() {
        if (mBgColors == null) {
            Resources resource = getResources();
            mBgColors = new int[] {
                    resource.getColor(R.color.androidColorA),
                    resource.getColor(R.color.androidColorB),
                    resource.getColor(R.color.androidColorC),
                    resource.getColor(R.color.androidColorD),
                    resource.getColor(R.color.androidColorE),
            };
        }
        return mBgColors;
    }

    private void vibrate(long duration) {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {
                0, duration
        };
        vibrator.vibrate(pattern, -1);
    }
}
