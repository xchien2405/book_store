package com.nhom4.bookstoremobile.repositories;

import android.app.Activity;
import android.database.Cursor;

import com.nhom4.bookstoremobile.entities.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    private static CartDAO cartDAO;
    private final CartTable cartTable;

    public CartDAO(CartTable cartTable) {
        this.cartTable = cartTable;
    }

    public static CartDAO getInstance(Activity activity) {
        if (cartDAO == null) {
            CartTable cartTable = new CartTable(activity);
            cartDAO = new CartDAO(cartTable);
        }
        return cartDAO;
    }

    public List<CartItem> getCartData() {
        List<CartItem> cart = new ArrayList<>();

        Cursor cursor = cartTable.getAllCartItems();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String bookID = cursor.getString(cursor.getColumnIndex("bookID"));
                int quantity = cursor.getInt(cursor.getColumnIndex("quantity"));
                cart.add(new CartItem(bookID, quantity));
            } while (cursor.moveToNext());
            cursor.close();
        }

        return cart;
    }

    public CartItem getCartItem(String id) {
        Cursor cursor = cartTable.findCartItem(id);

        if (cursor != null && cursor.moveToFirst()) {
            int quantity = cursor.getInt(cursor.getColumnIndex("quantity"));
            return new CartItem(id, quantity);
        }
        return null;
    }

    public void updateQuantityItem(String id, int quantity) {
        cartTable.updateQuantityItem(id, quantity);
    }

    public void addToCart(String id, int quantity) {
        cartTable.addToCart(id, quantity);
    }

    public void removeFromCart(String bookID) {
        cartTable.removeFromCart(bookID);
    }
}
