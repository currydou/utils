package com.curry.contentprovider.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.curry.contentprovider.R;

public class CursorActivity extends AppCompatActivity {

    public static final String PERSON_INFO_TABLE = "table_info";
    private EditText etName;
    private EditText etAge;
    private Button btnSave;
    private ListView lvList;
    private String userName;
    private String userPhoneNumber;
    private MyCursorAdapter myCursorAdapter;
    private SQLiteDatabase dataBase;
    private Cursor myCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursor);
        etName = (EditText) findViewById(R.id.etName);
        etAge = (EditText) findViewById(R.id.etAge);
        btnSave = (Button) findViewById(R.id.btnSave);
        lvList = (ListView) findViewById(R.id.lvList);

        CursorOpenHelper cursorOpenHelper = new CursorOpenHelper(CursorActivity.this);
        dataBase = cursorOpenHelper.getWritableDatabase();
        //根据 _id 降序插叙数据库保证最后插入的在最上面
        myCursor = dataBase.rawQuery("SELECT id AS _id ,name , age FROM table_info", null);
        myCursorAdapter = new MyCursorAdapter(CursorActivity.this, myCursor, true);
        lvList.setAdapter(myCursorAdapter);

        btnSave.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                userName = etName.getText().toString();
                userPhoneNumber = etAge.getText().toString();

                if (userName.equals("")) {
                    Toast.makeText(CursorActivity.this, "用户名不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (userPhoneNumber.equals("")) {
                    Toast.makeText(CursorActivity.this, "电话不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                ContentValues contentValues = new ContentValues();
                contentValues.put(MyCursorAdapter.NAME, userName);
                contentValues.put(MyCursorAdapter.PHONENUMBER, userPhoneNumber);
                //把EditText中的文本插入数据库
                dataBase.insert(PERSON_INFO_TABLE, null, contentValues);
                myCursor = dataBase.rawQuery("SELECT id AS _id ,name , age FROM table_info", null);
                //Cursor改变调用chanageCursor()方法
                myCursorAdapter.changeCursor(myCursor);
            }
        });

    }
}
