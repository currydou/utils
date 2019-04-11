package com.curry.io.utils;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 数据库拷贝--ms
 * Created by curry on 2016/10/15.
 */

public class CopyDb {
    private static void copyDB(Context context, String dbName) {
        File desFile = new File(context.getFilesDir(), dbName);
        FileOutputStream out = null;
        InputStream in = null;
        try {
            in = context.getAssets().open(dbName);
            out = new FileOutputStream(desFile);
            int len;
            byte[] buffer = new byte[1024];
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
