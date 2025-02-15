package com.nhom4.bookstoremobile.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nhom4.bookstoremobile.R;
import com.nhom4.bookstoremobile.controller.ViewBookDetailsController;

public class ViewBookDetails extends AppCompatActivity {
    private ViewBookDetailsController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        String id = getIntent().getStringExtra("book_id");
        controller = new ViewBookDetailsController(this, id);

        controller.getBookDetailFromAPI();
        controller.getBookListFromAPI();
        controller.setUpAdminLayout();

        setListener();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

    private void setListener() {
        SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(() -> controller.reload(pullToRefresh));

        findViewById(R.id.backBtn).setOnClickListener(v -> controller.redirectBack());
        findViewById(R.id.cartBtn).setOnClickListener(v -> controller.redirectToCart());
        findViewById(R.id.addToCartBtn).setOnClickListener(v -> controller.openAddCartView(2));
        findViewById(R.id.buyNowBtn).setOnClickListener(v -> controller.openAddCartView(1));
        findViewById(R.id.overlayLayout).setOnTouchListener((v, event) -> {
            v.performClick();
            controller.closeAddCartView();
            return false;
        });
    }
}