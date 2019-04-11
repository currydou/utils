package com.curry.file.otherutils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.ImageView;

import com.curry.file.BaseApplication;
import com.curry.file.FileIOActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;

/**
 * @Description: 拍照相关--ldz
 */
public class PhotoUtil {

    /**
     * 把图片写出去
     *
     * @param file
     * @param stream
     * @return
     */
    public static File getImageFile(File file, ByteArrayOutputStream stream) {
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

    public static byte[] readStream(InputStream inStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;
    }


    /**
     * 用当前时间给取得的图片命名
     */
    public static String setFileName() {
        Date date = new Date(System.currentTimeMillis());
        return TimeUtil.getStringFromTime(date, TimeUtil.FORMAT_PICTURE) + ".png";
    }

    /**
     * 检查SD卡状态
     *
     * @return
     */
    public static boolean sdCardState() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取相册照片
     */
    public static void getPicture(Activity activity) {
        if (activity == null) {
            return;
        }
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        activity.startActivityForResult(intent, 40);
    }

    /**
     * 获取相机拍照
     */
    public static void getPhoto(File imageFile, Activity activity) {
        if (activity == null) {
            return;
        }
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            BaseApplication.ImagePath = setFileName();
            imageFile = new File(BaseApplication.SD_SAVEDIR, BaseApplication.ImagePath);
            Uri uri = Uri.fromFile(imageFile);
            intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            activity.startActivityForResult(intent, 20);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void goToCrop(String path, Activity activity) {
        goToCrop(path, activity, false);
    }

    public static void goToCrop(String path, Activity activity, boolean tag) {
        LogUtil.znaLog(path);
        if (activity == null) {
            return;
        }
        try {
            Intent intent = new Intent(activity, FileIOActivity.class);
            intent.putExtra("path", path);
            intent.putExtra("crop", tag);
            activity.startActivityForResult(intent, 20);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理从相册返回的数据
     *
     * @param activity
     * @param data
     */
    public static void fromAlbum(Activity activity, Intent data) {
        fromAlbum(activity, data, false);
    }

    /**
     * 处理从相册返回的数据
     *
     * @param activity
     * @param data
     */
    public static void fromAlbum(Activity activity, Intent data, boolean tag) {
        try {
            Uri uri = data.getData();
            if (!TextUtils.isEmpty(uri.getAuthority())) {
                Cursor cursor = activity.getContentResolver().query(uri,
                        new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                if (null == cursor) {
                    return;
                }
                cursor.moveToFirst();
                String path = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
                cursor.close();
                Intent intent1 = new Intent(activity, FileIOActivity.class);
                intent1.putExtra("path", path);
                intent1.putExtra("crop", tag);
                activity.startActivityForResult(intent1, 20);
            } else {
                Intent intent2 = new Intent(activity, FileIOActivity.class);
                intent2.putExtra("path", uri.getPath());
                intent2.putExtra("crop", tag);
                activity.startActivityForResult(intent2, 20);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩从相册返回的数据
     *
     * @param context
     * @param data
     */
    public static String CropAlbum(Context context, Intent data, ImageView iv, int width, int height) {
        String path = null;
        try {
            Uri uri = data.getData();
            if (!TextUtils.isEmpty(uri.getAuthority())) {
                Cursor cursor = context.getContentResolver().query(uri,
                        new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                if (null == cursor) {
                    return null;
                }
                cursor.moveToFirst();
                path = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
                cursor.close();
            } else {
                path = uri.getPath();
            }
        } catch (Exception e) {
            return null;
        } finally {
            ThumbnailCropUtil.CropImage(context, path, width, height, iv);
            return path;
        }
    }


    /**
     * 处理裁剪页面回来的数据
     *
     * @param data
     * @param imageFile
     * @param iv        显示裁剪图片的iv
     * @param tag       是否显示
     * @return
     */
    public static File fromCrop(Intent data, File imageFile, ImageView iv, boolean tag) {

        try {
            final String path = data.getStringExtra("path");
            Bitmap photo = BitmapFactory.decodeFile(path);
            imageFile = new File(path);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);
            imageFile = getImageFile(imageFile, stream);
            String newPath = BaseApplication.SD_SAVEDIR + File.separator + setFileName();
            imageFile.renameTo(new File(newPath));
            imageFile = new File(newPath);
            if (tag) {
                Uri uri = Uri.parse("file://" + newPath);

//                ImageLoad.loadFile(iv,newPath);       会报错，所以注释
            }
            photo.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageFile;
    }

    /**
     * 处理裁剪页面回来的数据
     *
     * @param imageFile
     * @param path
     * @return
     */
    public static File CompressPath(String path, File imageFile) {

        try {
            imageFile = new File(BaseApplication.SD_SAVEDIR + File.separator + setFileName());
            Bitmap photo = BitmapFactory.decodeFile(path);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);
            imageFile = getImageFile(imageFile, stream);
            String newPath = BaseApplication.SD_SAVEDIR + File.separator + setFileName();
            imageFile.renameTo(new File(newPath));
            imageFile = new File(newPath);
            photo.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageFile;
    }

    /**
     * 将imageview中的图像保存到file文件中
     *
     * @return
     */
    public static File saveImageView(ImageView ivMainPic, File imageFile) {
        try {
            ivMainPic.buildDrawingCache();
            ivMainPic.setDrawingCacheEnabled(true);
            Bitmap photo = Bitmap.createBitmap(ivMainPic.getDrawingCache());
            ivMainPic.setDrawingCacheEnabled(false);
            BaseApplication.ImagePath = setFileName();
            imageFile = new File(BaseApplication.SD_SAVEDIR, BaseApplication.ImagePath);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);
            imageFile = getImageFile(imageFile, stream);
            imageFile.renameTo(new File(BaseApplication.SD_SAVEDIR + File.separator
                    + "mainImage.png"));
            imageFile = new File(BaseApplication.SD_SAVEDIR + File.separator + "mainImage.png");
        } catch (Exception e) {
            return null;
        }
        return imageFile;

    }
}
