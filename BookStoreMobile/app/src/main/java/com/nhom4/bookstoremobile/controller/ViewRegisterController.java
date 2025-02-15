package com.nhom4.bookstoremobile.controller;

import android.app.Activity;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.nhom4.bookstoremobile.R;
import com.nhom4.bookstoremobile.activity.ViewAccount;
import com.nhom4.bookstoremobile.entities.Account;
import com.nhom4.bookstoremobile.retrofit.RetrofitAPI;
import com.nhom4.bookstoremobile.service.AccountService;
import com.nhom4.bookstoremobile.service.ExceptionHandler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewRegisterController {
    private final Activity activity;

    public ViewRegisterController(Activity activity) {
        this.activity = activity;
    }

    public void checkInfo() {
        EditText userIDEditText = activity.findViewById(R.id.userID);
        EditText userPasswordEditText = activity.findViewById(R.id.userPassword);
        EditText userConfirmPasswordEditText = activity.findViewById(R.id.userConfirmPassword);

        String userID = userIDEditText.getText().toString();
        String userPassword = userPasswordEditText.getText().toString();
        String userConfirmPassword = userConfirmPasswordEditText.getText().toString();

        if (!userID.trim().isEmpty()) {
            if (userPassword.equals(userConfirmPassword)) {
                register(userID, userPassword);
            } else {
                Toast.makeText(activity, "Vui lòng xác nhận lại mật khẩu", Toast.LENGTH_LONG).show();
                ExceptionHandler.forcusError(userConfirmPasswordEditText);
            }
        } else {
            Toast.makeText(activity, "Vui lòng nhập tên đăng nhập", Toast.LENGTH_LONG).show();
            ExceptionHandler.forcusError(userIDEditText);
        }
    }

    private void register(String userID, String password) {
        Account account = new Account();
        account.setUserID(userID);
        account.setUserPassword(password);

        AccountService accountService = RetrofitAPI.getInstance().create(AccountService.class);
        Call<String> call = accountService.register(account);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(activity, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                    redirectToAccount();
                } else {
                    Toast.makeText(activity, "Tên tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    public void redirectToAccount() {
        Intent intent = new Intent(activity, ViewAccount.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }
}
