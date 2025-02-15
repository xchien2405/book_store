package com.nhom4.bookstoremobile.controller;

import android.app.Activity;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.nhom4.bookstoremobile.R;
import com.nhom4.bookstoremobile.activity.ViewAccount;
import com.nhom4.bookstoremobile.entities.Account;
import com.nhom4.bookstoremobile.repositories.AccountDAO;
import com.nhom4.bookstoremobile.retrofit.RetrofitAPI;
import com.nhom4.bookstoremobile.service.AccountService;
import com.nhom4.bookstoremobile.service.ExceptionHandler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewLoginController {
    private final Activity activity;

    public ViewLoginController(Activity activity) {
        this.activity = activity;
    }

    public void checkLogin() {
        EditText userID_EditText = activity.findViewById(R.id.userID);
        EditText userPassword_EditText = activity.findViewById(R.id.userPassword);

        String userID = userID_EditText.getText().toString();
        String userPassword = userPassword_EditText.getText().toString();

        if (userID.isEmpty()) {
            ExceptionHandler.forcusError(userID_EditText);
            Toast.makeText(activity, "Vui lòng nhập tên đăng nhập", Toast.LENGTH_SHORT).show();
            return;
        } else if (userPassword.isEmpty()) {
            ExceptionHandler.forcusError(userPassword_EditText);
            Toast.makeText(activity, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }

        AccountService accountService = RetrofitAPI.getInstance().create(AccountService.class);

        Call<Account> call = accountService.login(userID, userPassword);
        call.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()) {
                    Account account = response.body();
                    if (account.getUserID() != null) {
                        Toast.makeText(activity, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        loginSuccess(account);
                        redirectToAccount();
                    } else {
                        Toast.makeText(activity, "Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void loginSuccess(Account account) {
        AccountDAO accountDAO = AccountDAO.getInstance(activity);
        accountDAO.clear();
        int isAdminInt = 0;
        if (account.isAdmin()) {
            isAdminInt = 1;
        }
        accountDAO.addAccount(account.getUserID(), isAdminInt);
    }

    public void redirectToAccount() {
        Intent intent = new Intent(activity, ViewAccount.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }
}
