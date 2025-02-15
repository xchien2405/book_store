package com.nhom4.bookstoremobile.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nhom4.bookstoremobile.R;
import com.nhom4.bookstoremobile.controller.ViewAccountController;
import com.nhom4.bookstoremobile.entities.Account;
import com.nhom4.bookstoremobile.repositories.AccountDAO;

public class ViewAccount extends AppCompatActivity {
    private ViewAccountController controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        controller = new ViewAccountController(this);

        Account account = AccountDAO.getInstance(this).getAccountData();

        if (account == null) {
            setContentView(R.layout.activity_non_login);
            setListenerNonLogin();
        } else {
            setContentView(R.layout.activity_view_account);
            setListenerAccount();
            controller.getAccountFromAPI(account.getUserID());
        }
        setBaseListener();
    }

    private void setBaseListener() {
        findViewById(R.id.homeBtn).setOnClickListener(v -> controller.redirectToMain());
        findViewById(R.id.cartBtn).setOnClickListener(v -> controller.redirectToCart());
        findViewById(R.id.accountBtn).setOnClickListener(v -> recreate());
    }

    private void setListenerNonLogin() {
        findViewById(R.id.loginBtn).setOnClickListener(v -> controller.redirectToLogin());
        findViewById(R.id.registerBtn).setOnClickListener(v -> controller.redirectToRegister());
    }

    private void setListenerAccount() {
        SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(() -> controller.reload(pullToRefresh));

        findViewById(R.id.viewOrderListBtn).setOnClickListener(v -> controller.redirectToOrderList());
        findViewById(R.id.settingBtn).setOnClickListener(v -> controller.redirectToSetting());
    }
}
