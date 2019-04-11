package com.curry.contentprovider.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by curry.zhang on 5/10/2017.
 */

public class CursorOpenHelper extends SQLiteOpenHelper {

    public CursorOpenHelper(Context context) {
        super(context, "cursor.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
// create table Orders(Id integer primary key, CustomName text, OrderPrice integer, Country text);
        String sql = "create table if not exists " + CursorActivity.PERSON_INFO_TABLE + " (id integer, name text, age text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
