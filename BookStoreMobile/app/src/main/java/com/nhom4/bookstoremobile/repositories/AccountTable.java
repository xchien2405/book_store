package com.nhom4.bookstoremobile.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AccountTable extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;

    public AccountTable(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getReadableDatabase();
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String accountTable = "CREATE TABLE IF NOT EXISTS account (userID TEXT PRIMARY KEY, isAdmin INTEGER)";
        db.execSQL(accountTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS account");
        onCreate(db);
    }

    public Cursor getAccount() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query("account", null, null, null, null, null, null);
    }

    public void addAccount(String userID, int isAdmin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userID", userID);
        values.put("isAdmin", isAdmin);
        db.insert("account", null, values);
        db.close();
    }

    public void clear() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("account", null, null);
        db.close();
    }
}
