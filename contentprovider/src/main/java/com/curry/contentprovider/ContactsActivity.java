package com.curry.contentprovider;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.curry.contentprovider.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    private static final String NAME = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
    private static final String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);
        ListView lvTest = (ListView) findViewById(R.id.lvTest);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, getContants());
        lvTest.setAdapter(arrayAdapter);
    }

    /**
     * Caused by: java.lang.SecurityException: Permission Denial: opening provider com.android.providers.contacts.ContactsProvider2 from ProcessRecord{3f9907f 3581:com.curry.contentprovider/u0a82} (pid=3581, uid=10082) requires android.permission.READ_CONTACTS or android.permission.WRITE_CONTACTS
     * 危险权限。。。。
     * 读取手机联系人
     *
     * @return
     */
    private List<String> getContants() {
        List<String> contantList = new ArrayList<>();
        LogUtil.d(ContactsContract.CommonDataKinds.Phone.CONTENT_URI + "");
        //同样是联系人，uri的区别
        //1：content://com.android.contacts/data/phones
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);

        //2：http://blog.csdn.net/james_lang/article/details/56398068
        //content://com.android.contacts/raw_contacts
        if (cursor == null) {
            return contantList;
        }
        while (cursor.moveToNext()) {
            //得到手机联系人
            String name = cursor.getString(cursor.getColumnIndex(NAME));
            String number = cursor.getString(cursor.getColumnIndex(NUMBER));

            contantList.add(name);
            contantList.add(number);
        }

        return contantList;
    }
}
