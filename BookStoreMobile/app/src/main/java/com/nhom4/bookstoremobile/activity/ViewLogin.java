package com.nhom4.bookstoremobile.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nhom4.bookstoremobile.R;
import com.nhom4.bookstoremobile.controller.ViewLoginController;

public class ViewLogin extends AppCompatActivity {
    private ViewLoginController controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        controller = new ViewLoginController(this);

        setListener();
    }

    @Override
    public void onBackPressed() {
        controller.redirectToAccount();
        super.onBackPressed();
    }

    private void setListener() {
        findViewById(R.id.backBtn).setOnClickListener(v -> controller.redirectToAccount());
        findViewById(R.id.loginBtn).setOnClickListener(v -> controller.checkLogin());
    }
}


