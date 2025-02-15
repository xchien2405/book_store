package com.nhom4.bookstoremobile.service;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class Popup {
    public static void showConfirm(Context context, String title, String message, DialogInterface.OnClickListener positiveClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", positiveClickListener)
                .setNegativeButton("Cancel", null)
                .show();
    }

    public static void showStatusEdit(Context context, String title, DialogInterface.OnClickListener clickListener) {
        String[] status = {"Chưa xác nhận", "Đã xác nhận", "Đang vận chuyển", "Đã hoàn thành", "Đã hủy"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setItems(status, clickListener)
                .show();
    }
}
