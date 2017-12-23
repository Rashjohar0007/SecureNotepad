package com.gmail.rashjohar0007.securenotepad.basic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.gmail.rashjohar0007.securenotepad.models.Note;

import java.util.ArrayList;
import java.util.List;

public class DatabaseSupport extends SQLiteOpenHelper implements IConstants{

    private static final String DATABASE_NAME = "SecureNotepad";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NOTES= "Notes";
    private static final String TABLE_USER= "User";
    private static final String TABLE_NOTES_KEYS= "NoteKeys";
    private static final String KEY_NOTE_ID = "id";
    private static final String KEY_SECURE_KEY = "secure";
    private static final String KEY_LOGIN_KEY = "login";
    private static final String KEY_NOTE_KEY = "key";
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
        String CREATE_USER = "CREATE TABLE " + TABLE_USER +
                "(" +
                KEY_LOGIN_KEY + " TEXT," +
                KEY_SECURE_KEY + " TEXT" +
                ")";
        String CREATE_NOTE_TABLE = "CREATE TABLE " + TABLE_NOTES +
                "(" +
                KEY_NOTE_ID + " INTEGER PRIMARY KEY," +
                KEY_NOTE_TITLE + " TEXT," +
                KEY_NOTE_DATA + " TEXT" +
                ")";
        String CREATE_NOTE_KEY_TABLE = "CREATE TABLE " + TABLE_NOTES_KEYS +
                "(" +
                KEY_NOTE_ID + " INTEGER PRIMARY KEY," +
                KEY_NOTE_KEY + " TEXT" +
                ")";
        sqLiteDatabase.execSQL(CREATE_USER);
        sqLiteDatabase.execSQL(CREATE_NOTE_TABLE);
        sqLiteDatabase.execSQL(CREATE_NOTE_KEY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i != i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES_KEYS);
            onCreate(sqLiteDatabase);
        }
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
    public void addUSERLogin(String login,String secure)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_LOGIN_KEY, login);
            values.put(KEY_SECURE_KEY, secure);
            db.insertOrThrow(TABLE_USER, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }
    public void addNote(String key,Note note) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_NOTE_ID, note.getId());
            values.put(KEY_NOTE_TITLE, note.getTitle());
            values.put(KEY_NOTE_DATA, note.getData());
            db.insertOrThrow(TABLE_NOTES, null, values);
            db.setTransactionSuccessful();
            ContentValues values2 = new ContentValues();
            values.put(KEY_NOTE_ID, note.getId());
            values.put(KEY_NOTE_KEY, key);
            db.insertOrThrow(TABLE_NOTES_KEYS, null, values2);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }
    public long addOrUpdateNote(String key,Note note) {
        SQLiteDatabase db = getWritableDatabase();
        long userId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_NOTE_TITLE, note.getTitle());
            values.put(KEY_NOTE_DATA, note.getData());
            int rows = db.update(TABLE_NOTES, values, KEY_NOTE_ID + "= ?", new String[]{note.getId()+""});
            if (rows == 1) {
                String usersSelectQuery = String.format("SELECT * FROM %s WHERE %s = ?",
                        TABLE_NOTES, KEY_NOTE_ID);
                Cursor cursor = db.rawQuery(usersSelectQuery, new String[]{String.valueOf(note.getId())});
                try {
                    if (cursor.moveToFirst()) {
                        userId = cursor.getInt(0);
                        db.setTransactionSuccessful();
                    }
                } finally {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }
            } else {
                userId = db.insertOrThrow(TABLE_NOTES, null, values);
                db.setTransactionSuccessful();
                ContentValues values2 = new ContentValues();
                values.put(KEY_NOTE_ID, note.getId());
                values.put(KEY_NOTE_KEY, key);
                db.insertOrThrow(TABLE_NOTES_KEYS, null, values2);
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        return userId;
    }
    public List<Note> getAllNotes() {
        List<Note> posts = new ArrayList<>();
        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s",
                        TABLE_NOTES);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Note newUser = new Note(cursor.getInt(cursor.getColumnIndex(KEY_NOTE_ID)),cursor.getString(cursor.getColumnIndex(KEY_NOTE_TITLE)),cursor.getString(cursor.getColumnIndex(KEY_NOTE_DATA)));
                    posts.add(newUser);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return posts;
    }
    public int getKey() {
        int count=getAllNotes().size();
        if(count<=0) {
            return 1;
        } else {
            String POSTS_SELECT_QUERY =
                    String.format("SELECT MAX(%s) FROM %s",
                            KEY_NOTE_ID,TABLE_NOTES);
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
            try {
                if (cursor.moveToFirst()) {
                    return cursor.getInt(0)+1;
                }
            } catch (Exception e) {
                Log.d(TAG, "Error while trying to get posts from database");
            } finally {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
            }
            return 1;
        }
    }
    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOTE_TITLE, note.getTitle());
        values.put(KEY_NOTE_DATA, note.getData());

        return db.update(TABLE_NOTES, values, KEY_NOTE_ID + " = ?",
                new String[] { String.valueOf(note.getId()) });
    }
    public void deleteNote(Note note) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_NOTES, KEY_NOTE_ID + "= ?", new String[]{note.getId()+""});
            db.delete(TABLE_NOTES_KEYS, KEY_NOTE_ID + "= ?", new String[]{note.getId()+""});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete all posts and users");
        } finally {
            db.endTransaction();
        }
    }
    public void deleteAllNotes() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_NOTES, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete all posts and users");
        } finally {
            db.endTransaction();
        }
    }
}
