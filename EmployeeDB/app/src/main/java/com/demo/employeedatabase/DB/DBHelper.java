package com.demo.employeedatabase.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public String TABLE_NAME="employeeData";

    public DBHelper(Context context) {
        super(context,"EmployeeDB.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table "+TABLE_NAME+"(name TEXT primary key, phone TEXT,companyName Text,profileImg blob,email Text," +
                "Address TEXT,website Text,CompanyDetails TEXT,userID Text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop table if exists "+TABLE_NAME);

    }

    public void insertData(String name, String phone, String companyName,
                           byte[] prfImg, String email, String Address,
                           String Web, String CompanyDetails, String UserName ){
        try {
            SQLiteDatabase DB = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", name);
            contentValues.put("phone", phone);
            contentValues.put("companyName", companyName);
            contentValues.put("profileImg",prfImg);
            contentValues.put("email", email);
            contentValues.put("Address", Address);
            contentValues.put("website", Web);
            contentValues.put("CompanyDetails", CompanyDetails);
            contentValues.put("userID", UserName);
            DB.insert(TABLE_NAME, null, contentValues);
        }catch (Exception e){
            Log.e("sqlite",e.getMessage());
        }
    }




    public Cursor getData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("Select * from "+TABLE_NAME,null);
    }

    public boolean isEmpty(){
        SQLiteDatabase DB = this.getWritableDatabase();
        long rowCount = DatabaseUtils.queryNumEntries(DB,TABLE_NAME);
        return rowCount==0;
    }


    public void close() {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            db.close();
        }
    }


}
