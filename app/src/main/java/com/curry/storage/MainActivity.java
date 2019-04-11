package com.curry.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.curry.storage.dbmanager.CommonUtils;
import com.curry.storage.entity.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

//("/mnt/sdcard")
//改模板，if else foreach for
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etText;
    private SharedPreferences sp;
    private EditText etName;
    private EditText etPwd;
    private CheckBox checkBox;
    private Button btn;
    private Button btnMulti;
    private Button btnDelete;
    private Button btnUpdate;
    private Button btnQuery;
    private Button btnOne;
    private CommonUtils commonUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        commonUtils = new CommonUtils(this);
        init();
    }

    private void init() {
        btn = (Button) findViewById(R.id.btn);
        btnMulti = (Button) findViewById(R.id.btnMulti);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnQuery = (Button) findViewById(R.id.btnQuery);
        btnOne = (Button) findViewById(R.id.btnOne);
        initListener();
    }

    private void initListener() {
        btn.setOnClickListener(this);
        btnMulti.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnQuery.setOnClickListener(this);
        btnOne.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
                User user = new User();
                user.setId(1234L);
                user.setName("张三");
                commonUtils.insertUser(user);
                break;
            case R.id.btnMulti:
                List<User> userList = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    User user1 = new User();
                    user1.setId(2111L + i);
                    user1.setName("张三" + i);
                    userList.add(user1);
                }
                commonUtils.insertMultiUser(userList);
                break;
            case R.id.btnDelete:
                User user1 = new User();
                user1.setId(2112L);
                commonUtils.deleteUser(user1);
                break;
            case R.id.btnUpdate:
                User user3 = new User();
                user3.setId(2113L);
                user3.setName("改名了");
                commonUtils.updateUser(user3);

                break;
            case R.id.btnQuery:
                List<User> userList1 = commonUtils.queryAll();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < userList1.size(); i++) {
                    User user2 = userList1.get(i);
                    sb.append("name" + user2.getName() + ",");
                }

                Log.d("查询结果", sb.toString());
                break;
            case R.id.btnOne:
                User user2 = commonUtils.queryOne(1234L);
                Log.d("查询结果", user2 + "");
                break;
        }
    }

    public String load() {
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            FileInputStream in = openFileInput("data");
            bufferedReader = new BufferedReader(new InputStreamReader(in));
            String content;
            while ((content = bufferedReader.readLine()) != null) {
                sb.append(content);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public void save(String inputText) {
        FileOutputStream out;
        BufferedWriter bufferedWriter = null;
        try {
            out = openFileOutput("data", Context.MODE_PRIVATE);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(out));
            bufferedWriter.write(inputText);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
