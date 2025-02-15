package com.nhom4.bookstoremobile.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CartTable extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;

    public CartTable(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getReadableDatabase();
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String cartTable = "CREATE TABLE IF NOT EXISTS cart (bookID TEXT PRIMARY KEY, quantity INTEGER)";
        db.execSQL(cartTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS cart");
        onCreate(db);
    }

    public Cursor getAllCartItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query("cart", null, null, null, null, null, null);
    }

    public void addToCart(String bookID, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("bookID", bookID);
        values.put("quantity", quantity);
        db.insert("cart", null, values);
        db.close();
    }

    public void updateQuantityItem(String bookID, int newQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("quantity", newQuantity);
        db.update("cart", values, "bookID=?", new String[]{bookID});
        db.close();
    }

    public void removeFromCart(String bookID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("cart", "bookID=?", new String[]{bookID});
        db.close();
    }

    public void deleteAllCartItems() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("cart", null, null);
        db.close();
    }

    public Cursor findCartItem(String bookID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {"bookID", "quantity"};
        String selection = "bookID=?";
        String[] selectionArgs = {bookID};
        return db.query("cart", projection, selection, selectionArgs, null, null, null);
    }

}
