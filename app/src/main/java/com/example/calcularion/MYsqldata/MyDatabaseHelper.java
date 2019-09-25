package com.example.calcularion.MYsqldata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2019/9/14.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="sqlite.db";
    private static final int database_version=1;
   private String table_regester="sql_regester";
   private String table_data="sql_data";
   private String table_total="sql_total";
    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, database_version);
    }

    //调用父类构造器
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + "if not exists "+ table_regester + " ("
        + "id integer primary key,"
                        + "name varchar,"
                        +"acc_num long,"
                        + "password varchar)"
        );
        db.execSQL("create table " + "if not exists "+ table_data + " ("
                + "id integer primary key,"
                + "acc_num long,"
                +"type varchar,"
                +"money double,"
                +"date varchar,"
                +"timedetail varchar,"
                + "remark varchar)"
        );
        db.execSQL("create table " + "if not exists "+ table_total + " ("
                + "id integer primary key,"
                + "acc_num long,"
                +"date varchar,"
                +"type varchar,"
                +"ningchen double,"
                +"morning double,"
                +"afternoon double,"
                +"night double,"
                + "sumtotal double)"
        );
    }

//当打开数据库时传入的版本号与当前的版本号不同时会调用该方法
@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if  exists "+table_regester);
    db.execSQL("drop table if  exists "+table_data);
    db.execSQL("drop table if  exists "+table_total);
    this.onCreate(db);
}



}
