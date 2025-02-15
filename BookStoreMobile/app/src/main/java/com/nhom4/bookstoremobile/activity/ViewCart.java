package com.nhom4.bookstoremobile.activity;

import android.os.Bundle;
import android.widget.CheckBox;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nhom4.bookstoremobile.R;
import com.nhom4.bookstoremobile.controller.ViewCartController;

public class ViewCart extends AppCompatActivity {
    private ViewCartController controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        controller = new ViewCartController(this);
        setListener();

//        cartTable.deleteAllCartItems();
//        cartTable.addToCart("BXFLU747", 10);
//        cartTable.addToCart("BWNPW735", 10);
//        cartTable.addToCart("BPNOW492", 10);
//        cartTable.addToCart("BLYVL195", 10);
//        cartTable.addToCart("BLSVL608", 10);
//        cartTable.addToCart("BHXGU988", 10);
//        cartTable.addToCart("BHKKX073", 10);
//        cartTable.addToCart("BGRMC730", 10);
//        cartTable.addToCart("BGEHN704", 10);
//        cartTable.addToCart("BEVKS715", 10);

        controller.getCartData();
    }

    private void setListener() {
        SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(() -> controller.reload(pullToRefresh));

        CheckBox totalCheckBox = findViewById(R.id.totalCheckBox);
        totalCheckBox.setOnClickListener(view -> controller.checkAll());

        findViewById(R.id.homeBtn).setOnClickListener(v -> controller.redirectToMain());
        findViewById(R.id.backBtn).setOnClickListener(v -> controller.redirectBack());
        findViewById(R.id.deleteBtn).setOnClickListener(v -> controller.showConfirmationPopup());
        findViewById(R.id.accountBtn).setOnClickListener(v -> controller.redirectToAccount());
        findViewById(R.id.paymentBtn).setOnClickListener(v -> controller.redirectToCheckOut());
    }
}
