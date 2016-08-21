package com.example.radical.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;*/

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
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";
    private static final boolean DEBUG = true;

    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/yinuo/log/";
    private static final String FILE_NAME = "crash";

    //用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();
    // 用于格式化日期,作为日志文件名的一部分
    private SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    //log文件的后缀名
    private static final String FILE_NAME_SUFFIX = ".trace";

    //系统默认的异常处理（默认情况下，系统会终止当前的异常程序）
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;

    private Context mContext;
    private String token;
    private String urls="http://api.yinuo-tech.com/v1/connect";
    private String urladd="http://api.yinuo-tech.com/v1/app-crash/android/add";
    private String uri = "/app-crash/android/add";
    private SharedPreferences sharedPreferences;

    static class CrashHandlerHolder {
        static CrashHandler sInstance = new CrashHandler();
    }

    //构造方法私有，防止外部构造多个实例，即采用单例模式
    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return CrashHandlerHolder.sInstance;
    }

    /**
     * @param thread
     * @param throwable
     */
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
            //handleException(throwable);
            //收集设备参数信息
            collectDeviceInfo(mContext);
            //保存日志文件
            saveCrashInfo2File(throwable);
            //把日志传到服务器
            //upload2server(mContext, throwable);
            //导出异常信息到SD卡中
            //dumpExceptionToSDCard(throwable);
            //这里可以通过网络上传异常信息到服务器，便于开发人员分析日志从而解决bug
            //uploadExceptionToServer();

        //打印出当前调用栈信息
        //throwable.printStackTrace();
//        if (!handleException(throwable) && mDefaultCrashHandler != null) {
        if (mDefaultCrashHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultCrashHandler.uncaughtException(thread, throwable);
        } else {
            /*try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            }*/
            //退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 这里主要完成初始化工作
     *
     * @param context
     */
    public void init(Context context) {
        //获取系统默认的异常处理器
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        //将当前实例设为系统默认的异常处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        //获取Context，方便内部使用
        //mContext = context.getApplicationContext();
        mContext = context;
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private void handleException(Throwable ex){
        /*if (ex == null) {
            return false;
        }*/
        //使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
        //收集设备参数信息   
        collectDeviceInfo(mContext);
        //保存日志文件   
        saveCrashInfo2File(ex);
        //把日志传到服务器
//        upload2server(mContext, ex);
//        return true;
    }

    /*private void upload2server(Context context, Throwable throwable) {
        if (isNetworkAvailable(context)) {
            String time = formatter.format(new Date());
            String message = throwable.getMessage();
            Log.i("Time", time);
            Log.i("Message", throwable.getMessage());
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            throwable.printStackTrace(printWriter);
            String stack = writer.toString();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    token = gettoken();
                }
            }).start();
            String jsonData=null;
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("ats", time);
                jsonObject.put("stacks", message);
                jsonData = jsonObject.toString();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("Stack", stack);
            Log.i("Json", jsonData);
            Log.i("Stack", "do upload here");
            Log.i("token", token);
            upload(urladd,jsonData);
            *//*sharedPreferences = context.getSharedPreferences("test", Activity.MODE_PRIVATE);
            token=sharedPreferences.getString("token", "");*//*
        }
    }
    private String Content_Type = "application/json";
    public static final MediaType JSONType
            = MediaType.parse("application/json; charset=utf-8");
    private final OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private void upload(String urladd, String jsonData) {
        Log.i("UPLOAD", "start");
        System.out.println("Upload start");
        RequestBody body = RequestBody.create(JSON, jsonData);
        Request request = new Request.Builder()
                .url(urladd)
                .header("Content-Type",Content_Type)
                .addHeader("Authorization", "Bearer "+token)
                .addHeader("Charset", "UTF-8")
                .post(body)
                .build();
        Log.i("UPLOAD", request.toString()+request.header("Authorization")+request.method());
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //Log.i("UPLOAD", "SUCCESS");
                String rebody = response.body().string();
                Log.i("UPLOAD", rebody);
                try {
                    int code = new JSONObject(rebody).getInt("code");
                    System.out.print("code"+code);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }*/

    private String gettoken() {
        Log.i("get", "start");
        System.out.println("gettoken start");
        String getoken=null;
        try {
            URL url = new URL(urls);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            Log.i("con", "open" + con.getRequestMethod());
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            con.setRequestMethod("POST");
            con.setRequestProperty("Charset", "UTF-8");
            con.setRequestProperty("Content-Type",
                    "application/json");
            con.setRequestProperty("Authorization",
                    "Basic YWI5NTM3MzQ4ODo3ZHNoMGpzZG9wanc1andxM2hqdzU=");
                      /* 取得Response内容 */
            Log.i("AAA", ""+con.getResponseCode());
            InputStream is = con.getInputStream();
            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);

            }
            Log.i("AAA", b.toString());
            JSONObject jsonObject = new JSONObject(b.toString().trim());
            getoken = jsonObject.getString("token");
            Log.i("AAA", getoken);
            con.disconnect();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SharedPreferences mySharedPreferences = mContext.getSharedPreferences("test",
                Activity.MODE_PRIVATE);
        // 实例化SharedPreferences.Editor对象（第二步）
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        // 用putString的方法保存数据
        editor.putString("token", getoken);
        editor.commit();
        return getoken;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = mgr.getActiveNetworkInfo();
        /*//should works(stackoverflow  recommends)
        return info != null&&info.isConnectedOrConnecting();*/
        if (info != null && info.isConnected()) {
            return true;
        }
        return false;
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex){

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            long timestamp = System.currentTimeMillis();
            String time = formatter.format(new Date());
            String fileName = "crash-" + time + "-" + timestamp + ".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/yinuo/log/crashlog";
                Log.i("CrashHandler", path + fileName + "..sper" + File.separator);
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(new File(dir, fileName));
                fos.write(sb.toString().getBytes());
                fos.close();
            } else {
                if (DEBUG) {
                    Log.w(TAG, "sdcard unmounted,skip dump exception");
                    return null;
                }
            }
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }
        return null;
    }

}
