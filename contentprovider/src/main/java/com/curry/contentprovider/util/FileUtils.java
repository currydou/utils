package com.curry.contentprovider.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.curry.contentprovider.R;
import com.curry.contentprovider.entity.FileInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by curry.zhang on 6/7/2017.
 */

public class FileUtils {

    /**
     * 小数的格式化
     */
    public static final DecimalFormat FORMAT = new DecimalFormat("####.##");
    public static final DecimalFormat FORMAT_ONE = new DecimalFormat("####.#");
    /**
     * 自定义文件类型
     */
    public static final int TYPE_APK = 1;
    public static final int TYPE_JPEG = 2;
    public static final int TYPE_MP3 = 3;
    public static final int TYPE_MP4 = 4;


    /**
     * 存储卡获取 指定文件
     *
     * @param context
     * @param extension
     * @return
     */
    public static List<FileInfo> getSpecificTypeFiles(Context context, String[] extension) {
        List<FileInfo> fileInfoList = new ArrayList<FileInfo>();

        //内存卡文件的Uri。    content://media/external/file
        Uri fileUri = MediaStore.Files.getContentUri("external");// TODO: 6/14/2017  data是什么数据，有扩展名的文件？是路径
        LogUtil.d(fileUri + "");
        //筛选列，这里只筛选了：文件路径和含后缀的文件名
        String[] projection = new String[]{
                MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.TITLE
        };

        //构造筛选条件语句
        String selection = "";
        for (int i = 0; i < extension.length; i++) {
            if (i != 0) {
                selection = selection + " OR ";
            }
            //模糊查询后缀名
            selection = selection + MediaStore.Files.FileColumns.DATA + " LIKE '%" + extension[i] + "'";
        }
        //按时间降序条件
        String sortOrder = MediaStore.Files.FileColumns.DATE_MODIFIED;
        //查询类似sql语句  SELECT     _ID, word, locale       FROM words WHERE word =         <userinput>         ORDER BY word ASC;
        Cursor cursor = context.getContentResolver().query(fileUri, projection, selection, null, sortOrder);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                try {
                    String data = cursor.getString(0);
                    FileInfo fileInfo = new FileInfo();
                    fileInfo.setFilePath(data);

                    long size = 0;
                    try {
                        File file = new File(data);
                        size = file.length();
                        fileInfo.setSize(size);
                    } catch (Exception e) {

                    }
                    fileInfoList.add(fileInfo);
                } catch (Exception e) {
                    Log.i("FileUtils", "------>>>" + e.getMessage());
                }

            }
        }
        cursor.close();
        Log.i(TAG, "getSize ===>>> " + fileInfoList.size());
        return fileInfoList;
    }


    /**
     * 转化完整信息的FileInfo
     *
     * @param context
     * @param fileInfoList
     * @param type
     * @return
     */
    public static List<FileInfo> getDetailFileInfos(Context context, List<FileInfo> fileInfoList, int type) {

        if (fileInfoList == null || fileInfoList.size() <= 0) {
            return fileInfoList;
        }

        for (FileInfo fileInfo : fileInfoList) {
            if (fileInfo != null) {
                fileInfo.setName(getFileName(fileInfo.getFilePath()));
                fileInfo.setSizeDesc(getFileSize(fileInfo.getSize()));
                if (type == FileInfo.TYPE_APK) {
                    fileInfo.setBitmap(FileUtils.drawableToBitmap(FileUtils.getApkThumbnail(context, fileInfo.getFilePath())));
                } else if (type == TYPE_MP4) {
                    fileInfo.setBitmap(FileUtils.getScreenshotBitmap(context, fileInfo.getFilePath(), TYPE_MP4));
                } else if (type == TYPE_MP3) { //mp3不需要缩略图

                } else if (type == FileInfo.TYPE_JPG) {//由Glide图片加载框架加载

                }
                fileInfo.setFileType(type);
            }
        }
        return fileInfoList;
    }

    /**
     * 根据文件路径获取文件的名称
     *
     * @param filePath
     * @return
     */
    public static String getFileName(String filePath) {
        if (filePath == null || filePath.equals("")) return "";
        return filePath.substring(filePath.lastIndexOf("/") + 1);
    }

    /**
     * 根据传入的byte数量转换为对应的byte, Kbyte, Mbyte, Gbyte单位的字符串
     *
     * @param size byte数量
     * @return
     */
    public static String getFileSize(long size) {
        if (size < 0) { //小于0字节则返回0
            return "0B";
        }

        double value = 0f;
        if ((size / 1024) < 1) { //0 ` 1024 byte
            return size + "B";
        } else if ((size / (1024 * 1024)) < 1) {//0 ` 1024 kbyte

            value = size / 1024f;
            return FORMAT.format(value) + "KB";
        } else if (size / (1024 * 1024 * 1024) < 1) {                  //0 ` 1024 mbyte
            value = (size * 100 / (1024 * 1024)) / 100f;
            return FORMAT.format(value) + "MB";
        } else {                  //0 ` 1024 mbyte
            value = (size * 100l / (1024l * 1024l * 1024l)) / 100f;
            return FORMAT.format(value) + "GB";
        }
    }

    /**
     * Drawable转Bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        //建立对应的Bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);

        return bitmap;
    }


    /**
     * 获取Apk文件的Log图标
     *
     * @param context
     * @param apk_path
     * @return
     */
    public static Drawable getApkThumbnail(Context context, String apk_path) {
        if (context == null) {
            return null;
        }

        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = pm.getPackageArchiveInfo(apk_path, PackageManager.GET_ACTIVITIES);
            ApplicationInfo appInfo = packageInfo.applicationInfo;
            /**获取apk的图标 */
            appInfo.sourceDir = apk_path;
            appInfo.publicSourceDir = apk_path;
            if (appInfo != null) {
                Drawable apk_icon = appInfo.loadIcon(pm);
                return apk_icon;
            }
        } catch (Exception e) {

        }

        return null;
    }

    /**
     * 获取缩略图的Bitmap
     *
     * @param filePath
     * @param type
     * @return
     */
    public static Bitmap getScreenshotBitmap(Context context, String filePath, int type) {
        Bitmap bitmap = null;
        switch (type) {
            case TYPE_APK: {
                Drawable drawable = getApkThumbnail(context, filePath);
                if (drawable != null) {
                    bitmap = drawableToBitmap(drawable);
                } else {
//                    bitmap = drawableToBitmap()
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
                }
                break;
            }
            case TYPE_JPEG: {
                try {
                    bitmap = BitmapFactory.decodeStream(new FileInputStream(new File(filePath)));
                } catch (FileNotFoundException e) {
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon_jpg);
                }
                bitmap = extractThumbnail(bitmap, 100, 100);
                break;
            }
            case TYPE_MP3: {
                /*
                try {
                    bitmap = BitmapFactory.decodeStream(new FileInputStream(new File(filePath)));
                } catch (FileNotFoundException e) {
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon_mp3);
                }
                */
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon_mp3);
                bitmap = extractThumbnail(bitmap, 100, 100);
                break;
            }
            case TYPE_MP4: {
                try {
                    bitmap = createVideoThumbnail(filePath);
                } catch (Exception e) {
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon_mp4);
                }
                bitmap = extractThumbnail(bitmap, 100, 100);
                break;
            }
        }

        return bitmap;
    }

    /**
     * 创建缩略图
     *
     * @param filePath
     * @return
     */
    public static Bitmap createVideoThumbnail(String filePath) {
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(filePath, MediaStore.Images.Thumbnails.MICRO_KIND);
        return bitmap;
    }


    /**
     * 将图片转换成指定宽高
     *
     * @param source
     * @param width
     * @param height
     * @return
     */
    public static Bitmap extractThumbnail(Bitmap source, int width, int height) {
        Bitmap bitmap = ThumbnailUtils.extractThumbnail(source, width, height);
        return bitmap;
    }


}
