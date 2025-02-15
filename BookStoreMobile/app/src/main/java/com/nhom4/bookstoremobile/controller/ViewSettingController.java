package com.nhom4.bookstoremobile.controller;

import android.app.Activity;
import android.content.Intent;

import com.nhom4.bookstoremobile.R;
import com.nhom4.bookstoremobile.activity.ViewAccount;
import com.nhom4.bookstoremobile.activity.ViewSettingInfo;
import com.nhom4.bookstoremobile.repositories.AccountDAO;
import com.nhom4.bookstoremobile.service.Popup;


public class ViewSettingController {
    private final Activity activity;

    public ViewSettingController(Activity activity) {
        this.activity = activity;
    }

    public void showConfirmationPopup() {
        Popup.showConfirm(activity, "Xác nhận", "Bạn muốn đăng xuất?", (dialog, which) -> {
            AccountDAO.getInstance(activity).clear();
            redirectToAccount();
        });
    }

    public void redirectToAccount() {
        Intent intent = new Intent(activity, ViewAccount.class);
        activity.startActivity(intent);
        activity.finish();
        activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

    public void redirectToSettingInfo() {
        Intent intent = new Intent(activity, ViewSettingInfo.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }
}
