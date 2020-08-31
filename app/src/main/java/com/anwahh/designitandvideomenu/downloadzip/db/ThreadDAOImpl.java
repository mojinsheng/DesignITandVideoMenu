package com.anwahh.designitandvideomenu.downloadzip.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.anwahh.designitandvideomenu.downloadzip.entities.ThreadInfo;

import java.util.ArrayList;
import java.util.List;

public class ThreadDAOImpl implements ThreadDAO {
    private DBHelper dbHelper = null;

    public ThreadDAOImpl(Context context) {
        dbHelper = DBHelper.getInstance(context);
    }

    @Override
    public void insertThread(ThreadInfo threadInfo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("insert into " + DBConstant.TABLE_NAME + "(thread_id,url,thread_start,thread_end,finished) values(?,?,?,?,?)",
                new Object[]{threadInfo.id, threadInfo.url, threadInfo.thread_start, threadInfo.thread_end, threadInfo.finished});
        db.close();
    }


    @Override
    public void deleteThread(String url, int threadId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("delete from " + DBConstant.TABLE_NAME + " where url = ? and thread_id = ?",
                new Object[]{url, threadId});
        db.close();
    }

    @Override
    public void updateThread(String url, int threadId, int finished) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("update " + DBConstant.TABLE_NAME + " set finished = ? where url = ? and thread_id = ?",
                new Object[]{finished, url, threadId});
        db.close();
    }

    @Override
    public List<ThreadInfo> queryThread(String url) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DBConstant.TABLE_NAME + " where url = ?", new String[]{url});
        List<ThreadInfo> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            ThreadInfo threadInfo = new ThreadInfo();
            threadInfo.id = cursor.getInt(cursor.getColumnIndex("thread_id"));
            threadInfo.url = cursor.getString(cursor.getColumnIndex("url"));
            threadInfo.thread_start = cursor.getInt(cursor.getColumnIndex("thread_start"));
            threadInfo.thread_end = cursor.getInt(cursor.getColumnIndex("thread_end"));
            threadInfo.finished = cursor.getInt(cursor.getColumnIndex("finished"));
            list.add(threadInfo);
        }
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public boolean isExists(String url, int threadId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DBConstant.TABLE_NAME + " where url = ? and thread_id = ?", new String[]{url, threadId + ""});
        boolean isExits = cursor.moveToNext();
        cursor.close();
        db.close();
        return isExits;
    }
}
