package com.nhom4.bookstoremobile.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nhom4.bookstoremobile.R;
import com.nhom4.bookstoremobile.controller.CheckOutController;
import com.nhom4.bookstoremobile.controller.ViewSettingInfoController;
import com.nhom4.bookstoremobile.entities.Account;
import com.nhom4.bookstoremobile.entities.CartItem;
import com.nhom4.bookstoremobile.repositories.AccountDAO;

public class CheckOut extends AppCompatActivity {
    private CheckOutController orderController;
    private ViewSettingInfoController infoController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        boolean isBuyNow = getIntent().getBooleanExtra("isBuyNow", false);

        infoController = new ViewSettingInfoController(this);
        Account account = AccountDAO.getInstance(this).getAccountData();
        infoController.getAccountFromAPI(account.getUserID());

        orderController = new CheckOutController(this, isBuyNow);

        if (isBuyNow) {
            String bookID = getIntent().getStringExtra("bookID");
            int quantity = getIntent().getIntExtra("quantity", 0);
            CartItem cartItem = new CartItem(bookID, quantity);
            orderController.buyNow(cartItem);
        } else {
            boolean[] checkedArray = getIntent().getBooleanArrayExtra("orderList");
            orderController.getOrderItemList(checkedArray);
        }

        setListener();
    }



    private void setListener() {
        findViewById(R.id.backBtn).setOnClickListener(v -> orderController.redirectBack());
        findViewById(R.id.phoneChange).setOnClickListener(v -> infoController.openEditor(3));
        findViewById(R.id.addressChange).setOnClickListener(v -> infoController.openEditor(4));
        findViewById(R.id.overlayLayout).setOnTouchListener((v, event) -> {
            v.performClick();
            infoController.closeEditor();
            return false;
        });

        TextView codBtn = findViewById(R.id.codBtn);
        TextView waitBtn = findViewById(R.id.zaloBtn);
        TextView momoBtn = findViewById(R.id.momoBtn);
        TextView creditBtn = findViewById(R.id.creditBtn);

        orderController.addBtnToList(codBtn);
        orderController.addBtnToList(waitBtn);
        orderController.addBtnToList(momoBtn);
        orderController.addBtnToList(creditBtn);

        codBtn.setOnClickListener(v -> {
            orderController.setEffect(codBtn);
        });
        waitBtn.setOnClickListener(v -> {
            orderController.setEffect(waitBtn);
        });
        momoBtn.setOnClickListener(v -> {
            orderController.setEffect(momoBtn);
        });
        creditBtn.setOnClickListener(v -> {
            orderController.setEffect(creditBtn);
        });

        orderController.setEffect(codBtn);

        findViewById(R.id.orderBtn).setOnClickListener(v -> orderController.createOrder(infoController.getAccount()));
    }

    @Override
    public void onBackPressed() {
        orderController.redirectBack();
        super.onBackPressed();
    }
}
