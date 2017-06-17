package com.example.hi.vol;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Hi on 27-05-2017.
 */

public class DB_Handler extends SQLiteOpenHelper{
    final static String DB_NAME="data";
    final static int DB_VERISON=2;
    String TABLE="results";
    String NAME="name";
    String HEIGHT="height";
    String MASS="mass";

    String CREATE_TABLE="create table "+TABLE+"("+NAME+" varchar2(40),"+HEIGHT+" varchar2(40),"+MASS+" varchar2(40)) if not exists;";
    String SELECT_QUERY="select * from "+TABLE;
    String SELECT_PARTICILLAR="select * from "+TABLE+" where "+NAME+"=?";

    public DB_Handler(Context context) {
        super(context, DB_NAME, null, DB_VERISON);
        Log.e(">>>","database created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        Log.e(">>>","table is created");
    }

    public  void insertData(String name,String height,String mass)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(NAME,name);
        values.put(HEIGHT,height);
        values.put(MASS,mass);
        db.insert(TABLE,null,values);
        Log.e(">>>","values inserted");
    }

    public  void viewDate()
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery(SELECT_QUERY,null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            while (cursor.moveToNext())
            {
                String name=cursor.getString(0);
                String height=cursor.getString(1);
                String mass=cursor.getString(2);
                Log.e(">>>","name = "+name+"    height = "+height+"   mass = "+mass);
            }
        }
        else {
            Log.e(">>>","empty cursor");
        }
    }

    public void deleteData(String name)
    {
        SQLiteDatabase db=getWritableDatabase();
        //String a[]={ID};
        db.delete(TABLE,NAME+"=?",new String[]{name});
        Log.e(">>>","Data Deleted");
    }

    public  void updateData(String OLD_NAME,String NEW_NAME)
    {
        SQLiteDatabase db=getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(NAME,NEW_NAME);

        db.update(TABLE,values,NAME+"=?",new String[]{OLD_NAME});
        Log.e(">>>","data updated");
    }

    public  void findData(String NAME)
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery(SELECT_PARTICILLAR,new String[]{NAME});
        if(cursor!=null)
        {
            if(cursor.moveToNext())
            {
                String name=cursor.getString(0);
                String height=cursor.getString(1);
                String mass=cursor.getString(2);

                Log.e(">>>","NAME   "+name);
                Log.e(">>>","HEIGHT   "+height);
                Log.e(">>>","mass   "+mass);
            }
            else
            {
                Log.e(">>>","data not found");
            }

        }
        else{
            Log.e(">>>","data not found");
        }

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_QUERY="drop table "+TABLE+" if exists";
        db.execSQL(DROP_QUERY);
        onCreate(db);
    }
  
}
