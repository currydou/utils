package com.curry.file.otherutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

/**
 * @author wangkai
 * @Description: 缩略图/裁剪
 * create at 2015/12/18 14:41
 */
public class ThumbnailCropUtil {
    private static int horizantalPadding = 0;
    private static int verticalPadding = 0;

    public static void CropImage(Context contexxt, String path, int ivWidth, int ivHeight, ImageView iv) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;// 如果值设为true，那么将不返回实际的bitmap，也不给其分配内存空间，这样就避免了内存溢出。
            Bitmap bitmap = BitmapFactory.decodeFile(path, options);
            options.inJustDecodeBounds = false;
            int width = options.outWidth;
            int height = options.outHeight;
            options.inSampleSize = Math.min(width / ivWidth, height / ivWidth);
            bitmap = BitmapFactory.decodeFile(path, options);
            width = options.outWidth;
            height = options.outHeight;
            int padding = Math.abs(width - height) / 2;
            if (width < ivWidth || height < ivHeight) {
                horizantalPadding = 0;
                verticalPadding = 0;
            } else {
                if (width > height) {
                    horizantalPadding = padding;
                    verticalPadding = 0;
                } else if (height > width) {
                    verticalPadding = padding;
                    horizantalPadding = 0;
                } else {
                    horizantalPadding = 0;
                    verticalPadding = 0;
                }
                bitmap = Bitmap.createBitmap(bitmap, horizantalPadding, verticalPadding, ivWidth, ivHeight);
            }
            iv.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
//
//    public static void ShowThumbnail(Context context, String path, int ivWidth, ImageView iv) {
//        try {
//            if (path.contains("drawable")) {
//                PortraitLoad.RoundLocalImage(path, iv, context, 0);
//                return;
//            }
//            Bitmap bitmap = BitmapFactory.decodeFile(path);
//            bitmap = ThumbnailUtils.extractThumbnail(bitmap, ivWidth, ivWidth);
//            iv.setImageBitmap(bitmap);
//        } catch (Exception e) {
//            PortraitLoad.RoundImage(path, iv, context, 0);
//        }
//
//    }

}
