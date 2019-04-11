package com.curry.file.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Created by curry on 2017/12/17.
 */

public class SelectPhotoUtils {

    public static final int CODE_TAKE_PHOTO = 0x11;
    public static final int CODE_ALBUM = 0x12;
    public static final int CODE_CROP = 0x13;

    private static Uri imageUri;
    private static File tempImgFile;

    public static void takePhoto(Activity activity) {
        if (!AppUtils.isExistSD() || activity == null) {
            showNoSDCardToast(activity);
            return;
        }
        tempImgFile = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".png");
        try {
            if (tempImgFile.exists()) {
                tempImgFile.delete();
            }
            tempImgFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageUri = Uri.fromFile(tempImgFile);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        activity.startActivityForResult(intent, CODE_TAKE_PHOTO);
    }

    public static Intent goCrop(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例，这里设置的是正方形（长宽比为1:1）
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 2);
        // outputX outputY 是裁剪图片宽高
//                intent.putExtra("outputX", 300);
//                intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        return intent;
    }

    public static void goAlbum(Activity activity) {
        if (!AppUtils.isExistSD() || activity == null) {
            showNoSDCardToast(activity);
            return;
        }
        Intent albumIntent = new Intent(Intent.ACTION_PICK);
        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(albumIntent, CODE_ALBUM);
    }

    /**
     * 在activity的onActivityForResult方法中调用
     */
    public void onActivityForResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode != Activity.RESULT_OK) {
                return;
            }
            switch (requestCode) {
                case CODE_TAKE_PHOTO://拍照
//                    if (tempImgFile == null || !tempImgFile.exists()) {
//                        showNoSDCardToast();
//                        return;
//                    }
//                    goCrop(Uri.fromFile(tempImgFile));
                    if (data != null && data.getData() != null) {
                        goCrop(data.getData());
                    }

                    break;
                case CODE_ALBUM:// 在相册中查找
                    if (data != null && data.getData() != null) {
                        goCrop(data.getData());
                    }
                    break;
                case CODE_CROP://截图
                    if (data != null && tempImgFile != null && mSelectedListener != null) {
                        mSelectedListener.onSelected(tempImgFile.getAbsolutePath());
                    }
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public interface OnSelectedListener {
        void onSelected(String path);
    }

    private OnSelectedListener mSelectedListener;

    public void setAliSaveListener(OnSelectedListener listener) {
        this.mSelectedListener = listener;
    }

    public static void showNoSDCardToast(Context context) {
        Toast.makeText(context, "没有SD卡", Toast.LENGTH_SHORT).show();
    }


    /*==============================属性参考备忘=================================================*/

    /**
     * 相册参数备忘
     */
    private void albumMemo(int width, int height) {
        Intent intent = new Intent();
        intent.setType("image/*");  // 开启Pictures画面Type设定为image
        intent.setAction(Intent.ACTION_GET_CONTENT); //使用Intent.ACTION_GET_CONTENT这个Action
        //实现对图片的裁剪，必须要设置图片的属性和大小
        intent.setType("image/*");  //获取任意图片类型
        intent.putExtra("crop", "true");  //滑动选中图片区域
        intent.putExtra("aspectX", 1);  //裁剪框比例1:1
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", width);  //输出图片大小
        intent.putExtra("outputY", height);
        intent.putExtra("return-data", true);  //有返回值

        /*-----------------*/
        Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
    }


    /**
     * 备忘 裁剪里面的设置属性。（）
     */
    private void cropMemo(Uri imageUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.putExtra("crop", "true");
        // 设置x,y的比例，截图方框就按照这个比例来截 若设置为0,0，或者不设置 则自由比例截图
        intent.putExtra("aspectX", 2);
        intent.putExtra("aspectY", 1);
        // 裁剪区的宽和高 其实就是裁剪后的显示区域 若裁剪的比例不是显示的比例，
        // 则自动压缩图片填满显示区域。若设置为0,0 就不显示。若不设置，则按原始大小显示
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 100);
        // 不知道有啥用。。可能会保存一个比例值 需要相关文档啊
        intent.putExtra("scale", true);
        // true的话直接返回bitmap，可能会很占内存 不建议
        intent.putExtra("return-data", true);
        // 上面设为false的时候将MediaStore.EXTRA_OUTPUT即"output"关联一个Uri
        intent.putExtra("MediaStore.EXTRA_OUTPUT", imageUri);
        // 看参数即可知道是输出格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // 面部识别 这里用不上
        intent.putExtra("noFaceDetection", false);
        intent.setDataAndType(imageUri, "image/*");
        //
//        intent.putExtra("output", imageUri);


        /*---------另一种，应该是指定裁剪路径的------------------*/
//        intent.putExtra("path", path);
//        intent.putExtra("crop", tag);


    }

}
