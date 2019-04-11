package com.curry.contentprovider.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.curry.contentprovider.R;

/**
 * Created by curry.zhang on 5/10/2017.
 */

public class MyCursorAdapter extends CursorAdapter {

    public static final String NAME = "name";
    public static final String  PHONENUMBER= "age";



    public MyCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public MyCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        ViewHolder viewHolder = new ViewHolder();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_info, parent, false);

        viewHolder.tv_name = (TextView) view.findViewById(R.id.tv_name);
        viewHolder.tv_phonenumber = (TextView) view.findViewById(R.id.tv_phonenumber);
        view.setTag(viewHolder);
        Log.i("cursor", "newView=" + view);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Log.i("cursor", "bindView=" + view);
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        //从数据库中查询姓名字段
        String name = cursor.getString(cursor.getColumnIndex(NAME));
        //从数据库中查询电话字段
        String phoneNumber = cursor.getString(cursor.getColumnIndex(PHONENUMBER));

        viewHolder.tv_name.setText(name);
        viewHolder.tv_phonenumber.setText(phoneNumber);
    }

    class ViewHolder {
        TextView tv_name;
        TextView tv_phonenumber;
    }


}
