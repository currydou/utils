package com.curry.file.util1;

import android.graphics.Bitmap;
import android.util.Log;

import com.yodoo.atinvoice.BuildConfig;
import com.yodoo.atinvoice.R;
import com.yodoo.atinvoice.base.FeiKongBaoApplication;
import com.yodoo.atinvoice.okhttp.OkHttpUtil;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 上传工具类
 * <p>
 * 支持上传文件和参数
 */
public class UploadUtil {
    private static UploadUtil uploadUtil;
    private static final String BOUNDARY = UUID.randomUUID().toString(); // 边界标识
    // 随机生成
    private static final String PREFIX = "--";
    private static final String LINE_END = "\r\n";
    private static final String CONTENT_TYPE = "multipart/form-data"; // 内容类型
    /**
     * 去上传文件
     */
    public static final int TO_UPLOAD_FILE = 1;
    /**
     * 上传文件响应
     */
    public static final int UPLOAD_FILE_DONE = 2; //
    /**
     * 选择文件
     */
    public static final int TO_SELECT_PHOTO = 3;
    /**
     * 上传初始化
     */
    public static final int UPLOAD_INIT_PROCESS = 4;
    /**
     * 上传中
     */
    public static final int UPLOAD_IN_PROCESS = 5;

    public UploadUtil() {

    }

    /**
     * 单例模式获取上传工具类
     *
     * @return
     */
    public static UploadUtil getInstance() {
        if (null == uploadUtil) {
            uploadUtil = new UploadUtil();
        }
        return uploadUtil;
    }

    /**
     * 内文页多线程上传
     */
//	public void ArticleUpload(final ArrayList<String> pathlist) {
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				UploadFiles(pathlist);
//			}
//		}).start();
//	}

//	private void UploadFiles(ArrayList<String> pathlist) {
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("uid", PerfHelper.getStringData(UserMsg.USER_ID));
//		map.put("pws", PerfHelper.getStringData(UserMsg.USER_PWS));
//		map.put("active_uid", PerfHelper.getStringData(UserMsg.USER_UDID));
////		String fileKey = "bizfile";
//		uploadUtil.uploadFile(pathlist, /*fileKey,*/ UserMsg.fkb_suishouji_uploadfile_login, map);
//	}

    private static final String TAG = "UploadUtil";
    private int readTimeOut = 10 * 1000; // 读取超时
    private int connectTimeout = 10 * 1000; // 超时时间
//	/***
//	 * 请求使用多长时间
//	 */
//	private static int requestTime = 0;

    private static final String CHARSET = "UTF-8"; // 设置编码

    /***
     * 上传成功
     */
    public static final int UPLOAD_SUCCESS_CODE = 10;
    /**
     * 文件不存在
     */
    public static final int UPLOAD_FILE_NOT_EXISTS_CODE = 2;
    /**
     * 服务器出错
     */
    public static final int UPLOAD_SERVER_ERROR_CODE = 7;

    public File compressImage(File file) {
        String tmpPath = file.getPath() + "tmp";
        Bitmap bitmap = BitMapUtil.getBitmap(file.getPath(), 1000, 1000);
        File tmpFile = BitMapUtil.saveBitmap(tmpPath, bitmap);
        if (tmpFile != null && tmpFile.exists() && tmpFile.length() > 0) {
            tmpFile.renameTo(file);
        }
        return file;
    }

    private void uploadImg(File file, String RequestURL, Map<String, String> param) {
        file = compressImage(file);
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder();
        multipartBuilder.setType(MultipartBody.FORM);
        if (param != null && param.size() > 0) {
            Iterator<String> it = param.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                String value = param.get(key);
                multipartBuilder.addPart(Headers.of(
                        "Content-Disposition",
                        "form-data; name=\"" + key + "\""),
                        RequestBody.create(null, value));
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "uploadImg: " + key + "=" + value);
                }
            }
        }
        RequestBody requestBody = multipartBuilder.addPart(Headers.of(
                "Content-Disposition",
                "form-data; name=\"mFile\"; filename=\"" + file.getName() + "\""), fileBody)
                .build();
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "uploadImg: filename=" + file.getName());
        }
        Request request = new Request.Builder()
                .url(RequestURL)
                .post(requestBody)
                .build();

        Call call = OkHttpUtil.getInstance().getOkHttpClient().newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                if (BuildConfig.DEBUG) Log.e(TAG, "request error");
                sendMessage(UPLOAD_SERVER_ERROR_CODE, FeiKongBaoApplication.instance.getString(R.string.sign_img_upload_fail));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.code() == 200) {
                    if (BuildConfig.DEBUG) Log.e(TAG, "request success");
                    String result = response.body().string();
                    if (BuildConfig.DEBUG) Log.e(TAG, "result : " + result);
                    sendMessage(UPLOAD_SUCCESS_CODE, result);
                } else if (response != null) {
                    if (BuildConfig.DEBUG) Log.e(TAG, "request error");
                    sendMessage(UPLOAD_SERVER_ERROR_CODE, FeiKongBaoApplication.instance.getString(R.string.sign_img_upload_fail) + "：code=" + response.code());
                } else {
                    if (BuildConfig.DEBUG) Log.e(TAG, "request error");
                    sendMessage(UPLOAD_SERVER_ERROR_CODE, FeiKongBaoApplication.instance.getString(R.string.sign_img_upload_fail));
                }
            }


        });

    }

    /**
     * 上传单张图片
     * <p>
     * //	 * @param files
     * //	 * @param fileKey
     * //	 * @param RequestURL
     * //	 * @param param
     */
    public void UploadOneFile(File file, String RequestURL, Map<String, String> param) {
        uploadImg(file, RequestURL, param);
    }

    /**
     * 发送上传结果
     *
     * @param responseCode
     * @param responseMessage
     */
    private void sendMessage(int responseCode, String responseMessage) {
        if (onUploadProcessListener != null)
            onUploadProcessListener.onUploadDone(responseCode, responseMessage);
    }

    /**
     * 下面是一个自定义的回调函数，用到回调上传文件是否完成
     *
     * @author shimingzheng
     */
    public static interface OnUploadProcessListener {
        /**
         * 上传响应
         *
         * @param responseCode
         * @param message
         */
        void onUploadDone(int responseCode, String message);

        /**
         * 上传中
         *
         * @param uploadSize
         */
        void onUploadProcess(int uploadSize);

        /**
         * 准备上传
         *
         * @param fileSize
         */
        void initUpload(int fileSize);
    }

    private OnUploadProcessListener onUploadProcessListener;

    public void setOnUploadProcessListener(OnUploadProcessListener onUploadProcessListener) {
        this.onUploadProcessListener = onUploadProcessListener;
    }

    public int getReadTimeOut() {
        return readTimeOut;
    }

    public void setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

//	/**
//	 * 获取上传使用的时间
//	 * 
//	 * @return
//	 */
//	public static int getRequestTime() {
//		return requestTime;
//	}

    public static interface uploadProcessListener {

    }

}
