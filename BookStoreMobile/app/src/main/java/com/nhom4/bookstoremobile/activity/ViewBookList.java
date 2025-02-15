package com.nhom4.bookstoremobile.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nhom4.bookstoremobile.R;
import com.nhom4.bookstoremobile.controller.ViewBookListController;

public class ViewBookList extends AppCompatActivity {
    private ViewBookListController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        setListener();

        controller = new ViewBookListController(this);
        controller.getBookListFromAPI();
        controller.checkAdmin();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

    private void setListener() {
        SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(() -> controller.reload(pullToRefresh));

        findViewById(R.id.backBtn).setOnClickListener(v -> controller.redirectToMain());
    }
}
