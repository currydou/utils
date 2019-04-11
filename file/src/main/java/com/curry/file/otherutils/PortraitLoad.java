//package com.curry.file.otherutils;
//
//import android.content.Context;
//import android.net.Uri;
//import android.widget.ImageView;
//
//import com.bc.lingdaozhe.base.BaseApplication;
//import com.bc.lingdaozhe.base.URLConfig;
//import com.facebook.drawee.backends.pipeline.Fresco;
//import com.facebook.drawee.interfaces.DraweeController;
//import com.facebook.drawee.view.SimpleDraweeView;
//import com.facebook.imagepipeline.common.ResizeOptions;
//import com.facebook.imagepipeline.request.ImageRequest;
//import com.facebook.imagepipeline.request.ImageRequestBuilder;
//
///**
// * --ldz
// */
//public class PortraitLoad {
//
//    /**
//     * 加载图片
//     *
//     * @param picPath 图片网址
//     * @param iv      显示图片的控件
//     * @param context 上下文
//     * @param roundDp int值,0为正方形，5为正常圆角，90为全圆
//     * @auther wk
//     */
//
//   /* public static void RoundImage(String picPath, ImageView iv, Context context, int roundDp) {
//        if (StringUtil.isEmpty(picPath)) {
//            return;
//        }
//        int bigRoundpx = ScreenUtil.dip2px(context, roundDp);
//        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
//                .considerExifParams(true)
//                .cacheOnDisk(true)  // 设置下载的图片是否缓存在SD卡中
//                .cacheInMemory(true)    // 设置下载的图片是否缓存在内存中
//                .displayer(new RoundedBitmapDisplayer(bigRoundpx)) // 设置成圆角图片
//                .build();   // 创建配置过得DisplayImageOption对象
//        String url = null;
//        if (picPath.contains("http://") || picPath.contains("https://")) {
//            url = picPath;
//        } else {
//            url = URLConfig.baseUrl_pic_oss + picPath;
//        }
//        ImageLoader.getInstance().displayImage(url, new ImageViewAwareR(iv), displayImageOptions);
//    }*/
//
//    /**
//     * 加载图片
//     *
//     * @param picPath 图片网址
//     * @param iv      显示图片的控件
////     * @param context 上下文
////     * @param roundDp int值,0为正方形，5为正常圆角，90为全圆
//     * @auther wk
//     */
//
//
//    public static void frescoImage(String picPath, SimpleDraweeView iv) {
//        String url = picPath.contains("http://") || picPath.contains("https://") ? picPath : URLConfig.baseUrl_pic_oss + picPath;
//        if (picPath.contains("res:") || picPath.contains("file:")) {
//            url = picPath;
//        }
//        Uri uri = Uri.parse(url);
//        iv.setImageURI(uri);
//    }
//
//    public static void loadRes(SimpleDraweeView iv, int resId) {
//        String url = "res://" + BaseApplication.getInstance().getPackageName() + "/" + resId;
//        Uri uri = Uri.parse(url);
//        iv.setImageURI(uri);
//    }
//
//    public static void loadFile(SimpleDraweeView iv, String resId) {
//        String url = "file://" + BaseApplication.getInstance().getPackageName() + resId;
//        Uri uri = Uri.parse(url);
//        iv.setImageURI(uri);
//    }
//
//    public static void loadFile(SimpleDraweeView iv, String resId, int width) {
//        String url = "file://" + BaseApplication.getInstance().getPackageName() + resId;
//        Uri uri = Uri.parse(url);
//        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
//                .setResizeOptions(new ResizeOptions(width, width))
//                .build();
//
//        DraweeController controller = Fresco.newDraweeControllerBuilder()
//                .setOldController(iv.getController())
//                .setImageRequest(request)
//                .build();
//        iv.setController(controller);
////        iv.setImageURI(uri);
//    }
//
//}
