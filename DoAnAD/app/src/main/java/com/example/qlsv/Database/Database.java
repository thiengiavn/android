package com.example.qlsv.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //Truy vấn Database (Không trả về kết quả: DELETE, UPDATE, CREATE, INSERT
    public void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }
    //Truy vấn trả về kết quả SELECT. CursorFactory: Con trỏ duyệt từng dòng dữ liệu
    public Cursor GetData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }

    public void INSERT_USER(String user, String pass,String name, String place, String phone){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO User VALUES(null, ?, ?,?,?,?)";
        SQLiteStatement statement =  database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, user);
        statement.bindString(2, pass);
        statement.bindString(3,name);
        statement.bindString(4, place);
        statement.bindString(5, phone);
        statement.executeInsert();

    }
    //insert class
    public void INSERT_CLASS(String malop,String tenlop, int iduser){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Class VALUES(null, ?, ? ,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, malop);
        statement.bindString(2,tenlop);

        statement.bindString(3, iduser + "");
        statement.executeInsert();
    }
    //insert SV
    public void INSERT_SV(String tenSV,String date,int idclass ,int iduser, String tenlop, String sdt, String email,String place, byte[] images ){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Students VALUES(null, ?, ? ,?,?,?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, tenSV);
        statement.bindString(2,date);
        statement.bindString(3, idclass + "");
        statement.bindString(4, iduser + "");
        statement.bindString(5, tenlop);
        statement.bindString(6, sdt);
        statement.bindString(7, email);
        statement.bindString(8, place);
        statement.bindBlob(9, images);

        statement.executeInsert();
    }
}