package com.anwahh.designitandvideomenu.downloadzip.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static volatile DBHelper dbHelper;
    //创建表语法
    private static final String SQL_CREATE = "create table " + DBConstant.TABLE_NAME + "(_id integer primary key autoincrement," +
            DBConstant.THREAD_ID + " integer," + DBConstant.URL + " text," + DBConstant.THREAD_START + " integer," + DBConstant.THREAD_END + " integer," + DBConstant.FINISHED + " integer)";
    //删除表语法
    private static final String SQL_DROP = "drop table if exists " + DBConstant.TABLE_NAME;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private DBHelper(Context context) {
        super(context, DBConstant.DB_NAME, null, DBConstant.VERSION);
    }

    public static DBHelper getInstance(Context context) {
        if (dbHelper == null) {
            synchronized (DBHelper.class) {
                if (dbHelper == null) {
                    dbHelper = new DBHelper(context.getApplicationContext());
                }
            }
        }
        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //先删除
        db.execSQL(SQL_DROP);
        //再创建
        db.execSQL(SQL_CREATE);
    }
}
