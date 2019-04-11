package com.curry.file.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by curry on 2017/12/17.
 */

public class PhotoUtils {
    /**
     * 把bitmap转换成String
     *
     * @param filePath
     * @return
     */
    public static String bitmapToString(String filePath) {
        Bitmap bm = BitmapFactory.decodeFile(filePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 60, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static Bitmap stringToBitmap(String str) {
        byte[] bytes = Base64.decode(str, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }


    /*-----------------------------------------完整的压缩图片的方法-------------------------------------------*/
    public File compressImage(File file) {
        String tmpPath = file.getPath() + "tmp";
        Bitmap bitmap = BitMapUtil.getBitmap(file.getPath(), 1000, 1000);
        File tmpFile = BitMapUtil.saveBitmap(tmpPath, bitmap);
        if (tmpFile != null && tmpFile.exists() && tmpFile.length() > 0) {
            tmpFile.renameTo(file);
        }
        return file;
    }

    /*---------------------------------------需要整理查看的方法----------------------------------------------*/


    /**
     * 根据图片的bitmap对象进行压缩处理
     *
     * @param image
     * @return
     */
    public static byte[] comp(Bitmap image) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
                baos.reset();// 重置baos即清空baos
                image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
            }
            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
            newOpts.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
            newOpts.inJustDecodeBounds = false;
            int w = newOpts.outWidth;
            int h = newOpts.outHeight;
            // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
            float hh = 800f;// 这里设置高度为800f
            float ww = 480f;// 这里设置宽度为480f
            // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            int be = 1;// be=1表示不缩放
            if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
                be = (int) (newOpts.outWidth / ww);
            } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
                be = (int) (newOpts.outHeight / hh);
            }
            if (be <= 0)
                be = 1;
            newOpts.inSampleSize = be;// 设置缩放比例
            // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
            isBm = new ByteArrayInputStream(baos.toByteArray());
            bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
            return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据图片的bitmap对象进行压缩处理
     *
     * @param image
     * @return
     */
    public static byte[] compressImage(Bitmap image) {
        return compressImage(image, 480, 800, 85);
    }

    public static byte[] compressImage(Bitmap image, float targetw, float targeth, int compRatio) {
        try {
            File tmpFile = new File(System.currentTimeMillis() + ".tmp");
            FileOutputStream fos = new FileOutputStream(tmpFile);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            byte[] b = getScaledImageDataFromFile(tmpFile.getAbsolutePath(), targetw, targeth, compRatio);
            tmpFile.delete();
            return b;
        } catch (Exception e1) {
            e1.printStackTrace();
            return null;
        }
    }


    public static byte[] getScaledImageDataFromFile(String srcPath, float targetw, float targeth, int compRatio) {
        try {
            if (srcPath == null)
                return null;

            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
            newOpts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(srcPath, newOpts);

            // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            int be = 1;// be=1表示不缩放
            if (newOpts.outWidth > newOpts.outHeight) {
                if (newOpts.outWidth > targetw) {
                    be = (int) (newOpts.outWidth / targetw);
                }
            } else {
                if (newOpts.outHeight > targeth) {
                    be = (int) (newOpts.outHeight / targeth);
                }
            }
            if (be <= 0)
                be = 1;
            newOpts.inSampleSize = be;// 设置缩放比例
            newOpts.inJustDecodeBounds = false;
            // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
            Bitmap image = BitmapFactory.decodeFile(srcPath, newOpts);
            if (image == null) {
                return null;
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, compRatio, baos);
            image.recycle();
            return baos.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }
}
