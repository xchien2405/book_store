package com.nhom4.bookstoremobile;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nhom4.bookstoremobile.controller.MainActivityController;

public class MainActivity extends AppCompatActivity {
    private long backPressedTime;
    private MainActivityController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new MainActivityController(this);
        controller.getTopSellingFromAPI();

        setListener();
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 1000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Nhấn thêm một lần nữa để thoát", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    private void setListener() {
        SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(() -> controller.reload(pullToRefresh));

        findViewById(R.id.viewListBtn).setOnClickListener(v -> controller.redirectToBookList());
        findViewById(R.id.homeBtn).setOnClickListener(v -> recreate());
        findViewById(R.id.top_cartBtn).setOnClickListener(v -> controller.redirectToCart());
        findViewById(R.id.bottom_cartBtn).setOnClickListener(v -> controller.redirectToCart());
        findViewById(R.id.accountBtn).setOnClickListener(v -> controller.redirectToAccount());
    }
}
