package com.example.user.barcodereader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by USER on 29.04.2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String DATABASE_NAME = "CodeReader";

    private static final String TABLE_NAME = "DatabaseHelper";
    private static final String COL1 = "ID";
    private static final String COL2 = "name";

    private static final String TABLE_NAME_BARCODE = "Barcode";
    private static final String COL1_BARCODE = "ID";
    private static final String COL2_BARCODE = "name";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE "+TABLE_NAME+ " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COL2+" TEXT)";
        db.execSQL(createTable);

        String createTableBarcode = "CREATE TABLE "+TABLE_NAME_BARCODE+ " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COL2_BARCODE+" TEXT)";
        db.execSQL(createTableBarcode);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String item){

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL2, item);
            long result = db.insert(TABLE_NAME, null, contentValues);

            if (result == -1) {
                return false;
            } else {
                return true;
            }
    }


    public boolean addData2(String item){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2_BARCODE, item);
        long result = db.insert(TABLE_NAME_BARCODE, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME;
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public Cursor getDataBarcode(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME_BARCODE;
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT "+COL1+ " FROM "+ TABLE_NAME+" WHERE "+COL2 + " = '"+name+"'";
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public Cursor getItemIDBar(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT "+COL1_BARCODE+ " FROM "+ TABLE_NAME_BARCODE+" WHERE "+COL2_BARCODE + " = '"+name+"'";
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public void deleteName(int id,String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM "+TABLE_NAME+" WHERE "+COL1+" = '"+id+"'"+" AND "+COL2+" = '"+name+"'";
        db.execSQL(query);
    }

    public void deleteNameBar(int id,String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM "+TABLE_NAME_BARCODE+" WHERE "+COL1_BARCODE+" = '"+id+"'"+" AND "+COL2_BARCODE+" = '"+name+"'";
        db.execSQL(query);
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM "+TABLE_NAME_BARCODE;
        db.execSQL(query);
    }

    public void deleteAllFromQR(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM "+TABLE_NAME;
        db.execSQL(query);
    }


}
