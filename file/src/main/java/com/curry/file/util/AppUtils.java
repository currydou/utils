package com.curry.file.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by curry on 2017/12/17.
 */

public class AppUtils {


    public static boolean isExistSD() {
        return Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    public static String getSDPath() {
        File sdDir = null;
        if (isExistSD()) {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
        } else {
            sdDir = Environment.getRootDirectory();
        }
        return sdDir.toString();
    }
}
