package com.gmail.rashjohar0007.securenotepad.basic;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseSupport extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "SecureNotepad";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NOTES= "Notes";
    private static final String KEY_NOTE_ID = "id";
    private static final String KEY_NOTE_TITLE = "title";
    private static final String KEY_NOTE_DATA = "data";

    private static DatabaseSupport sInstance;

    public static synchronized DatabaseSupport getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseSupport(context.getApplicationContext());
        }
        return sInstance;
    }
    private DatabaseSupport(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private DatabaseSupport(Context context,DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_NOTE_TABLE = "CREATE TABLE " + TABLE_NOTES +
                "(" +
                KEY_NOTE_ID + " INTEGER PRIMARY KEY," +
                KEY_NOTE_TITLE + " TEXT," +
                KEY_NOTE_DATA + " TEXT" +
                ")";
        sqLiteDatabase.execSQL(CREATE_NOTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i != i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
            onCreate(sqLiteDatabase);
        }
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
}
