package com.curry.file.util;

import android.util.Log;

/**
 * Created by curry on 2016/10/15.
 */

public class LogUtil {


    /**
     * 打印空日志&&超过4k的分开打印
     *
     * @param text
     */
    public static void Logd4000(String text) {
        text = ">>" + text.trim();
        int index = 0;
        int maxLength = 4000;
        String sub;
        while (index < text.length()) {
            // java的字符不允许指定超过总的长度end
            if (text.length() <= index + maxLength) {
                sub = text.substring(index);
            } else {
                sub = text.substring(index, index + maxLength);
            }

            index += maxLength;
            Log.d("Log4000", "-->" + sub.trim());
        }
    }
}
