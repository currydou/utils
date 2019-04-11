package com.curry.io.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 将流转换成字符串--ms
 * Created by curry on 2016/2/11.
 */
public class StreamUtils {
    public static String readFromStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int len = 0;
        byte[] buffer = new byte[1024];
        while ((len = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, len);
        }
        String result = byteArrayOutputStream.toString();
        inputStream.close();
        byteArrayOutputStream.close();
        return result;
    }
}
