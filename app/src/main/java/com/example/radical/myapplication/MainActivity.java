package com.example.radical.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private String str;
    private Intent intent;
    private final static int SCANNIN_GREQUEST_CODE = 1;
    @BindView(R.id.multiLang)
    Button multiL;
    @BindView(R.id.btn_tab)
    Button tabTest;

    @OnClick(R.id.multiLang)
    void setMultiL() {
        intent = new Intent(MainActivity.this, ButterActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_tab)
    void tabTest() {
        intent = new Intent(MainActivity.this, TabTestActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_toolbar)
    void toolBarTest() {
        intent = new Intent(MainActivity.this, ToolbarActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_basic)
    void basicTest() {
        intent = new Intent(MainActivity.this, BasicDemoActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_login)
    void loginTest() {
        intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_setting)
    void settingTest() {
        intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_nav)
    void navigationTest() {
        intent = new Intent(MainActivity.this, NavigationActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_scroll)
    void scrollTest() {
        intent = new Intent(MainActivity.this, ScrollingActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_tabbed)
    void tabbedTest() {
        intent = new Intent(MainActivity.this, TabbedActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_swipeback)
    void swipeBackTest() {
        intent = new Intent(MainActivity.this, SwipeBackActivity.class);
        startActivity(intent);
    }


    /**
     * 显示扫描结果
     */
    private TextView mTextView;
    /**
     * 显示扫描拍的图片
     */
    private ImageView mImageView;

    /*@Override
    protected void onDestroy() {
        super.onDestroy();
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Main onCreate", "" + getTaskId());
        ButterKnife.bind(this);
        mTextView = (TextView) findViewById(R.id.result);
        mImageView = (ImageView) findViewById(R.id.qrcode_bitmap);

        //点击按钮跳转到二维码扫描界面，这里用的是startActivityForResult跳转
        //扫描完了之后调到该界面
        Button mButton = (Button) findViewById(R.id.scan);
        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                intent = new Intent();
                intent.setClass(MainActivity.this, CaptureActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
            }
        });
        //System.out.println(str.equals("any string"));
        //throw new RuntimeException("自定义异常：这是自己抛出的异常");
    }

    //    public void dialogshow(View view) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Dialog")
//                .setMessage("Dialog Content")
//                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                })
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                })
//                .show();
//    }
//
//    public void snackshow(View view) {
//        Snackbar.make(view, "data has deleted", Snackbar.LENGTH_SHORT)
//                .setAction("Undo", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(MainActivity.this, "data has recovered",Toast.LENGTH_SHORT);
//                    }
//                })
//                .show();
//    }

    public void multiLang(View view) {
        Log.i("Multi", "clicked");
        Intent multi = new Intent(MainActivity.this, MultiLangActivity.class);
        startActivity(multi);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    //显示扫描到的内容
                    mTextView.setText(bundle.getString("result"));
                    //显示
                    mImageView.setImageBitmap((Bitmap) data.getParcelableExtra("bitmap"));
                }
                break;
        }
    }
}
