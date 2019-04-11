package com.curry.file.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by curry on 2016/2/11.
 */
public class StreamUtils {

    public static String inputStreamToString(InputStream inputStream) throws IOException {
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

    public static File streamWriteToFile(File file, ByteArrayOutputStream stream) {
        byte[] bitmapData = stream.toByteArray();
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapData);
            fos.flush();
            fos.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return file;
    }
}
