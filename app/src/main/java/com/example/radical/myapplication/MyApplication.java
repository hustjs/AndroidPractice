package com.example.radical.myapplication;

import android.app.Application;

/**
 * Copyright (c)
 * Title.
 * <p/>
 * Description.
 *
 * @author radical
 * @version 1.0
 * @since 2016-07-30
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        /*CrashHandler handler = CrashHandler.getInstance();
        handler.init(getApplicationContext());
        Thread.setDefaultUncaughtExceptionHandler(handler);*/
    }
}
