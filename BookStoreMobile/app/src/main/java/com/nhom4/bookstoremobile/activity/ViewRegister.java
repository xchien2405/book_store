package com.nhom4.bookstoremobile.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nhom4.bookstoremobile.R;
import com.nhom4.bookstoremobile.controller.ViewRegisterController;

public class ViewRegister extends AppCompatActivity {
    private ViewRegisterController controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        controller = new ViewRegisterController(this);

        setListener();
    }

    private void setListener() {
        findViewById(R.id.registerBtn).setOnClickListener(v -> controller.checkInfo());
        findViewById(R.id.backBtn).setOnClickListener(v -> controller.redirectToAccount());
    }

    @Override
    public void onBackPressed() {
        controller.redirectToAccount();
        super.onBackPressed();
    }
}
