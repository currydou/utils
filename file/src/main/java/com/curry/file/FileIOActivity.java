package com.curry.file;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * 更多资料参考notes.txt
 */
public class FileIOActivity extends AppCompatActivity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_io);
        String content = load();
        this.editText = (EditText) findViewById(R.id.editText);
        editText.setText(content);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        save(editText.getText().toString().trim());
    }

    private void save(String data) {
        BufferedWriter bw = null;
        FileOutputStream fos;
        try {
            fos = openFileOutput("data", Context.MODE_PRIVATE);
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String load() {
        FileInputStream fis;
        BufferedReader br;
        StringBuilder sb = new StringBuilder();
        try {
            fis = openFileInput("data");
            br = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
