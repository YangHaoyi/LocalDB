package com.yhy.loginsave;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 作者 : YangHaoyi on 2016/7/18.
 * 邮箱 ：yanghaoyi@neusoft.com
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME  = "Library";
    private final static int DATABASE_VERSION = 1;
    private final static String TABLE_NAME = "User";


    /**
     * 构造函数，创建数据库
     * */
    public SQLiteHelper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    /***
     * 建表
     * */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE"+TABLE_NAME
                +"(_id INTEGER PRIMARY KEY,"
                +" UserName VARCHAR(20) NOT NULL,"
                +" Token VARCHAR(30))";
        db.execSQL(sql);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql ="DROP TABLE IF EXISTS" +TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }
    /**
     * 获取游标
     * */
    public Cursor select(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, null,null,null,null,null,null);
        return cursor;
    }
    /***
     * 插入一条数据
     * */
    public long insert(String user,String token){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("UserName",user);
        cv.put("Token",token);
        long row = db.insert(TABLE_NAME,null,cv);
        return row;
    }
    /***
     * 删除一条数据
     * */
    public void delete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "_id = ?";
        String[] whereValue = {Integer.toString(id)};
        db.delete(TABLE_NAME,where,whereValue);
    }
    /***
     * 修改一条数据
     * */
    public void update(int id,String user,String token){
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "_id = ?";
        String[]whereValue = {Integer.toString(id)};
        ContentValues cv = new ContentValues();
        cv.put("UserName",user);
        cv.put("Token",token);
        db.update(TABLE_NAME,cv,where,whereValue);
    }
    /**
     * 查找一条数据
     * **/
    public Cursor query(String[] args){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE UserName LIKE ?",args);
        return cursor;
    }
}
