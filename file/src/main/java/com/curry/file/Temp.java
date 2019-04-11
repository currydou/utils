package com.curry.file;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by curry.zhang on 5/12/2017.
 */

public class Temp {

    private FileOutputStream fileOutputStream;

    public void save(boolean sdSaving, ContextWrapper contextWrapper) throws IOException {
        if (sdSaving) {
            //sd卡存储
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                File file = new File(Environment.getExternalStorageDirectory(), "fileName");
                //
                fileOutputStream = new FileOutputStream(file);
            }
        } else {
            fileOutputStream = contextWrapper.openFileOutput("fileName", Context.MODE_PRIVATE);
        }
        fileOutputStream.write("content".getBytes());
        //保存成功
        fileOutputStream.close();

    }


}
