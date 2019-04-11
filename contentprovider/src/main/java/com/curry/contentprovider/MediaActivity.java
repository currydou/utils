package com.curry.contentprovider;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.curry.contentprovider.entity.FileInfo;
import com.curry.contentprovider.util.FileUtils;
import com.curry.contentprovider.util.LogUtil;

import java.util.List;

/**
 * Caused by: java.lang.SecurityException: Permission Denial:
 * reading com.android.providers.media.MediaProvider uri content://media/external/file
 * from pid=3466, uid=10082 requires android.permission.READ_EXTERNAL_STORAGE, or grantUriPermission()
 */
public class MediaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);


        List<FileInfo> fileInfoList = FileUtils.getSpecificTypeFiles(getApplicationContext(), new String[]{FileInfo.EXTEND_APK});
        List<FileInfo> infoList = FileUtils.getDetailFileInfos(getApplicationContext(), fileInfoList, FileUtils.TYPE_APK);

        for (FileInfo fileInfo : infoList) {
            LogUtil.d(fileInfo.toString());
        }

    }
}
