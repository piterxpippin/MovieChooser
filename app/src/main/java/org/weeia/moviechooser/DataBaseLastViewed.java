package org.weeia.moviechooser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//import java.util.GregorianCalendar;

/**
 * Created by user on 6/20/16.
 */
public class DataBaseLastViewed extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "data.db";
    public static final String TABLE_NAME = "last_viewed";
    //GregorianCalendar czas;


    public DataBaseLastViewed(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, IMDB INTEGER, DATA INTEGER)");
       // czas = new GregorianCalendar();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(int imdb){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("IMDB",imdb);
        contentValues.put("DATA", System.currentTimeMillis());
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;

    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME,null);
        return res;
    }
}

    /*
DataBaseLastViewed db = new DataBaseLastViewed(this);
Cursor cursor = db.getAllData();
if ( cursor.getCount() != 0) {
cursor.moveToLast();
numerIMBD = cursor.getString(1);
        }



// wrzucanie do bazy

DataBaseLastViewed db = new DataBaseLastViewed(this);
boolean isInserted = db.insertData(numerIMDB);
if (isInserted == true)
Toast.makeText(GetValuesActivity.this, "Zapisano dane", Toast.LENGTH_LONG).show();
else
Toast.makeText(GetValuesActivity.this, "Błąd zapisu danych", Toast.LENGTH_LONG).show();

        */