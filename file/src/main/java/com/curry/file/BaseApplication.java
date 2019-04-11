package com.curry.file;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;


/**
 * @author wangkai
 * @Description: BaseApplication
 * create at 2016/1/17 23:32
 */
public class BaseApplication extends Application {

    public static BaseApplication instance = null;
    public static boolean isRestart = false;
    private static LayoutInflater mInflater;
    private static SharedPreferences mSharePreference;

    private Context mContext;
    public static String SHARENAME = "shuifu";
    public static final String SD_SAVEDIR = Environment.getExternalStorageDirectory() + "/.LingDaoZhe";
    public static final String PIC_CACHE_PATH = SD_SAVEDIR + "/" + "cache";
    public static String PERSONAL_PATH = SD_SAVEDIR;
    public static Handler mDelivery;
    public static String ImagePath = "";
    public static Dialog dialog;


    public static BaseApplication getInstance() {
        if (instance == null) {
            synchronized (BaseApplication.class) {
                if (instance == null) {
                    instance = new BaseApplication();
                }
            }
        }
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = this;
    }



}
