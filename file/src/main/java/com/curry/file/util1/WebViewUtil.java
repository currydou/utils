package com.curry.file.util1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.http.SslError;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yodoo.atinvoice.BuildConfig;
import com.yodoo.atinvoice.R;
import com.yodoo.atinvoice.model.ShareModel;
import com.yodoo.atinvoice.module.invoice.h5.JSHook;
import com.yodoo.atinvoice.view.dialog.ShareDialog;
import com.yodoo.atinvoice.webview.WebViewFragment;

import java.util.HashMap;

/**
 * Created by lib on 2017/3/17.
 */

public class WebViewUtil {

    private static final int THUMB_SIZE = 150;

    public static void initWebView(final Activity activity, WebView webView, final View loadingView) {
        initWebView(activity, webView, loadingView, null);
    }

    public static void initWebView(final Activity activity, final WebView webView, final View loadingView, final WebViewListener listener) {
        webView.requestFocusFromTouch();
        webView.clearCache(true);
//        webView.setWebContentsDebuggingEnabled(true);
        WebSettings set = webView.getSettings();
        set.setJavaScriptCanOpenWindowsAutomatically(true);//窗口自动打开？
        set.setJavaScriptEnabled(true);//支持js
        set.setSupportZoom(false);//不支持zoom
        set.setSaveFormData(true);//保存表单数据
        set.setBuiltInZoomControls(true);
        // set.setBlockNetworkImage(true);
        set.setUseWideViewPort(true);//自适应屏幕
        set.setDomStorageEnabled(true);//开启本地DOM存储（加载一些链接出现白板现象）
        set.setCacheMode(WebSettings.LOAD_NO_CACHE);//无缓存
        set.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//这个东西还是不用的为好  前端工程师会为自己的弹出框设置好位置  不必要手机端再来做弹出框的设置
        set.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
//        set.setAppCacheEnabled(true);//使用app缓存
        set.setDefaultTextEncodingName("utf-8"); // 设置文本编码
        set.setAllowFileAccess(true);//设置在WebView内部是否允许访问文件，默认允许访问。
        set.setDatabaseEnabled(true);//允许使用数据库api
        set.setLoadsImagesAutomatically(true);//自动加载图片

        WebViewClient client = new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if( url.startsWith("http:") || url.startsWith("https:") ) {
                    view.loadUrl(url);
                }else{ //非http或者https的网络请求拦截，用action_view启动。可能报错。
                    try {
                        Uri uri = Uri.parse(url);
                        Intent intent =new Intent(Intent.ACTION_VIEW, uri);
                        view.getContext().startActivity(intent);
                    }catch (Exception e){
                        e.printStackTrace();
                        if (url.startsWith("alipay")){
                        }
                    }
                    if (url.startsWith("alipay")){
                        activity.finish();
                    }
                }
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

        };
        webView.setWebChromeClient(new android.webkit.WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (!TextUtils.isEmpty(view.getTitle())) {
                    activity.setTitle(view.getTitle());
                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100 && loadingView != null) {
                    // 网页加载完成
                    loadingView.setVisibility(View.GONE);
                } else if (loadingView != null) {
                    // 加载中
                    loadingView.setVisibility(View.VISIBLE);
                }
            }

            // android 5.0
//            public boolean onShowFileChooser(
//                    WebView webView, ValueCallback<Uri[]> filePathCallback,
//                    WebChromeClient.FileChooserParams fileChooserParams) {
//                if (mFilePathCallback != null) {
//                    mFilePathCallback.onReceiveValue(null);
//                }
//                mFilePathCallback = filePathCallback;
//
//                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if (takePictureIntent.resolveActivity(webView.getContext().getPackageManager()) != null) {
//                    // Create the File where the photo should go
//                    File photoFile = null;
//                    try {
//                        photoFile = createImageFile();
//                        takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
//                    } catch (IOException ex) {
//                        // Error occurred while creating the File
//                        Log.e("WebViewSetting", "Unable to create Image File", ex);
//                    }
//
//                    // Continue only if the File was successfully created
//                    if (photoFile != null) {
//                        mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
//                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
//                                Uri.fromFile(photoFile));
//                    } else {
//                        takePictureIntent = null;
//                    }
//                }
//
//                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
//                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
//                contentSelectionIntent.setType("image/*");
//
//                Intent[] intentArray;
//                if (takePictureIntent != null) {
//                    intentArray = new Intent[]{takePictureIntent};
//                } else {
//                    intentArray = new Intent[0];
//                }
//
//                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
//                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
//                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
//                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
//
//                activity.startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE);
//
//                return true;
//            }
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                if (listener != null) {
                    listener.setUploadMessage2(filePathCallback);
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                    i.setType("image/*");
                    activity.startActivityForResult(Intent.createChooser(i, "Image Chooser"), WebViewFragment.INPUT_FILE_REQUEST_CODE);
                }
                return true;
            }

            //The undocumented magic method override
            //Eclipse will swear at you if you try to put @Override here
            // For Android 3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                if (listener != null) {
                    listener.setUploadMessage(uploadMsg);
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                    i.setType("image/*");
                    activity.startActivityForResult(Intent.createChooser(i, "Image Chooser"), WebViewFragment.FILECHOOSER_RESULTCODE);
                }

            }

            // For Android 3.0+
            public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
                if (listener != null) {
                    listener.setUploadMessage(uploadMsg);
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                    i.setType("image/*");
                    activity.startActivityForResult(
                            Intent.createChooser(i, "Image Chooser"),
                            WebViewFragment.FILECHOOSER_RESULTCODE);
                }
            }

            //For Android 4.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                if (listener != null) {
                    listener.setUploadMessage(uploadMsg);
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                    i.setType("image/*");
                    activity.startActivityForResult(Intent.createChooser(i, "Image Chooser"), WebViewFragment.FILECHOOSER_RESULTCODE);
                }
            }
        });

        JSHook jsHook = new JSHook(activity.getApplicationContext());
        webView.addJavascriptInterface(jsHook, "invoiceJSHook");
        webView.setWebViewClient(client);
    }

    public interface WebViewListener {
        void setUploadMessage(ValueCallback<Uri> mUploadMessage);

        void setUploadMessage2(ValueCallback<Uri[]> filePathCallback);
    }



}
