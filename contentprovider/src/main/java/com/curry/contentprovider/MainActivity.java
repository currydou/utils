package com.curry.contentprovider;

import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.curry.contentprovider.sqlite.CursorActivity;

/**
 * 加了权限，没有加运行时权限处理
 * 1 和 3 在原生6.0 ，7.0会自动崩溃，
 * 1 和 3 在小米，酷派的5.1系统 上自己弹窗申请权限；
 * <p>
 * 那当时看那个郭霖的权限时的那个，是什么情况？用的原生的
 */
//原生的加权限，没处理 ，测试
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //许多提供程序都允许您通过将 ID 值追加到 URI 末尾来访问表中的单个行。 例如，要从用户字典中检索 _ID 为 4 的行，则可使用此内容 URI：
        Uri singleUri = ContentUris.withAppendedId(UserDictionary.Words.CONTENT_URI, 4);

    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                startActivity(new Intent(this, ContactsActivity.class));
                break;
            case R.id.btn2:
                startActivity(new Intent(this, CursorActivity.class));
                break;
            case R.id.btn3:
                startActivity(new Intent(this, MediaActivity.class));
                break;
        }

    }
}
