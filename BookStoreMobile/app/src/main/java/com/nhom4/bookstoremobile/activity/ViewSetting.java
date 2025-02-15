package com.nhom4.bookstoremobile.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nhom4.bookstoremobile.R;
import com.nhom4.bookstoremobile.controller.ViewSettingController;

public class ViewSetting extends AppCompatActivity {
    private ViewSettingController controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        controller = new ViewSettingController(this);

        setListener();
    }

    @Override
    public void onBackPressed() {
        controller.redirectToAccount();
        super.onBackPressed();
    }

    private void setListener() {
        findViewById(R.id.backBtn).setOnClickListener(v -> controller.redirectToAccount());
        findViewById(R.id.logoutBtn).setOnClickListener(v -> controller.showConfirmationPopup());
        findViewById(R.id.infoBtn).setOnClickListener(v -> controller.redirectToSettingInfo());
    }
}
