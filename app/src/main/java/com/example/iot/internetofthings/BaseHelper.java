package com.example.iot.internetofthings;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class BaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "kuebiko.db";

    public static final String TABLE_NAME = "cerdos";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ORIG = "origin";
    public static final String COLUMN_DEPT = "departure";
    public static final String COLUMN_ARR = "arrival";
    public static final String COLUMN_RACE = "race";
    public static final String COLUMN_LOT = "lot";

    private static final String TABLE_NAME_SENSOR = "sensors";

    private static final String COLUMN_ID_SENSOR = "idsensor";
    private static final String COLUMN_NAME_SENSOR = "namesensor";
    private static final String COLUMN_VALUE_SENSOR = "valorsensor";
    private static final String COLUMN_PIG_ID = "idpig";

    //Create the tables
    private static final String SQL_CREAR  = "create table "
            + TABLE_NAME + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_NAME
            + " text not null," + COLUMN_ORIG + " text," + COLUMN_DEPT + " text," + COLUMN_ARR + " text," + COLUMN_RACE + " text,"
            + COLUMN_LOT + " text);";

    private static final String SQL_CREAR_SENSOR  = "create table "
            + TABLE_NAME_SENSOR + "(" + COLUMN_ID_SENSOR
            + " integer primary key autoincrement, " + COLUMN_NAME_SENSOR
            + " text not null," + COLUMN_VALUE_SENSOR + " text," + COLUMN_PIG_ID + " text," + " FOREIGN KEY( " + COLUMN_PIG_ID + ") REFERENCES " +
            TABLE_NAME +"("+ COLUMN_ID +")" +
            ");";


    public BaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREAR);
        db.execSQL(SQL_CREAR_SENSOR);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_SENSOR);
        onCreate(db);
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }
    public int numberOfSensorRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME_SENSOR);
        return numRows;
    }

    public boolean insertPig (String id, String name, String orig,String dep, String arr, String lot, String race) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("name", name);
        contentValues.put("origin", orig);
        contentValues.put("departure", dep);
        contentValues.put("arrival", arr);
        contentValues.put("lot", lot);
        contentValues.put("race", race);
        db.insert("cerdos", null, contentValues);
        return true;
    }

    public boolean insertSensor(int id, String name, String value, String idpig){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("idsensor",id);
        contentValues.put("namesensor",name);
        contentValues.put("valorsensor",value);
        contentValues.put("idpig",idpig);
        db.insert(TABLE_NAME_SENSOR, null, contentValues);
        return true;
    }
    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME+ " where " + COLUMN_ID +" = "+id+"", null );
        return res;
    }

    public ArrayList<String> getNames(){
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor  cursor = db.rawQuery("select * from cerdos",null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String name = "Pig Name: "+cursor.getString(cursor.getColumnIndex("name")) + " \nRace: " + cursor.getString(cursor.getColumnIndex("race"))
                        +" \nLot:"+cursor.getString(cursor.getColumnIndex("lot"));

                list.add(name);
                cursor.moveToNext();
            }
        }
        return  list;
    }

    public ArrayList<String> getSensorInfo(int id){
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor  cursor = db.rawQuery("select * from sensors",null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String name = "The "+cursor.getString(cursor.getColumnIndex("namesensor")) + " is at " + cursor.getString(cursor.getColumnIndex("valorsensor"))
                        +"% capacity";

                list.add(name);
                cursor.moveToNext();
            }
        }
        return  list;
    }
    public String getSensorValue(int idsensor){
        String name="";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor  cursor = db.rawQuery("select * from sensors  where " + COLUMN_ID_SENSOR +" = "+idsensor+"",null);
        if (cursor.moveToFirst()) {
            name = cursor.getString(cursor.getColumnIndex("valorsensor"));
        }
            return name;
    }


    public boolean insertPigKeyboard(String orig, String arriv, String dep, String race, String lot){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ORIG, orig);
        contentValues.put(COLUMN_ARR, arriv);
        contentValues.put(COLUMN_DEPT, dep);
        contentValues.put(COLUMN_RACE, race);
        contentValues.put(COLUMN_LOT, lot);
        contentValues.put(COLUMN_NAME,"NewPIg");
        db.insert("cerdos", null, contentValues);
        return true;
    }

}
