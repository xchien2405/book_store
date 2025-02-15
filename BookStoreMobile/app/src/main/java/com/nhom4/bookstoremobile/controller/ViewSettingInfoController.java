package com.nhom4.bookstoremobile.controller;

import android.app.Activity;
import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nhom4.bookstoremobile.R;
import com.nhom4.bookstoremobile.entities.Account;
import com.nhom4.bookstoremobile.retrofit.RetrofitAPI;
import com.nhom4.bookstoremobile.service.AccountService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewSettingInfoController {
    private final Activity activity;
    private View editInfo_Layout;
    private Account account;

    public ViewSettingInfoController(Activity activity) {
        this.activity = activity;
    }

    public Account getAccount() {
        return account;
    }

    public void getAccountFromAPI(String userID) {
        AccountService accountService = RetrofitAPI.getInstance().create(AccountService.class);

        Call<Account> call = accountService.getAccount(userID);
        call.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()) {
                    account = response.body();
                    setAccountData(account);
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
            }
        });
    }

    private void setAccountData(Account account) {
        TextView userID_TextView = activity.findViewById(R.id.userID);
        TextView userName_TextView = activity.findViewById(R.id.userName);
        TextView userEmail_TextView = activity.findViewById(R.id.userEmail);
        TextView userPhone_TextView = activity.findViewById(R.id.userPhone);
        TextView userAddress_TextView = activity.findViewById(R.id.userAddress);

        if (userID_TextView != null) {
            userID_TextView.setText(account.getUserID());
        }
        if (userName_TextView != null) {
            userName_TextView.setText(account.getUserName());
        }
        if (userEmail_TextView != null) {
            userEmail_TextView.setText(account.getUserEmail());
        }
        if (userPhone_TextView != null) {
            userPhone_TextView.setText(account.getUserPhone());
        }
        if (userAddress_TextView != null) {
            userAddress_TextView.setText(account.getUserAddress());
        }
    }

    public void openEditor(int choice) {
        LayoutInflater inflater = activity.getLayoutInflater();
        editInfo_Layout = inflater.inflate(R.layout.main_edit_info, null);

        editInfo_Layout.setId(choice);
        setListenerEditor(editInfo_Layout);

        TextView titleInfo = editInfo_Layout.findViewById(R.id.titleInfo);
        TextView info = editInfo_Layout.findViewById(R.id.info);
        EditText infoEditText = editInfo_Layout.findViewById(R.id.infoEditText);

        String choiceString = "";
        String editInfo = "";
        switch (choice) {
            case 1:
                choiceString = "Họ tên";
                editInfo = account.getUserName();
                break;
            case 2:
                choiceString = "Email";
                editInfo = account.getUserEmail();
                infoEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                break;
            case 3:
                choiceString = "Số điện thoại";
                editInfo = account.getUserPhone();

                InputFilter[] filters = new InputFilter[1];
                filters[0] = new InputFilter.LengthFilter(10);

                infoEditText.setFilters(filters);
                infoEditText.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
            case 4:
                choiceString = "Địa chỉ";
                editInfo = account.getUserAddress();
                break;
        }

        titleInfo.setText(choiceString);
        info.setText(choiceString);
        infoEditText.setText(editInfo);

        editInfo_Layout.findViewById(R.id.closeBtn).setOnClickListener(v -> closeEditor());

        FrameLayout layoutContainer = activity.findViewById(R.id.editInfoLayout);
        layoutContainer.addView(editInfo_Layout);

        editInfo_Layout.setClickable(true);
        editInfo_Layout.setFocusable(true);
        activity.findViewById(R.id.overlayLayout).setVisibility(View.VISIBLE);

        Animation slideUpAnimation = AnimationUtils.loadAnimation(activity, R.anim.slide_up);
        editInfo_Layout.startAnimation(slideUpAnimation);
    }

    public void closeEditor() {
        FrameLayout layoutContainer = activity.findViewById(R.id.editInfoLayout);

        activity.findViewById(R.id.overlayLayout).setVisibility(View.GONE);

        editInfo_Layout.setClickable(false);
        editInfo_Layout.setFocusable(false);

        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        Animation slideDownAnimation = AnimationUtils.loadAnimation(activity, R.anim.slide_down);
        editInfo_Layout.startAnimation(slideDownAnimation);

        layoutContainer.removeAllViewsInLayout();
    }

    private void setListenerEditor(View view) {
        view.findViewById(R.id.saveBtn).setOnClickListener(v -> {
            Account newAccount = new Account();
            newAccount.setUserID(account.getUserID());
            newAccount.setAdmin(account.isAdmin());

            EditText infoEditText = view.findViewById(R.id.infoEditText);
            String info = infoEditText.getText().toString();

            if (info.trim().isEmpty()) {
                Toast.makeText(activity, "Vui lòng không để trống", Toast.LENGTH_SHORT).show();
                return;
            }

            switch (view.getId()) {
                case 1:
                    newAccount.setUserName(info);
                    break;
                case 2:
                    newAccount.setUserEmail(info);
                    break;
                case 3:
                    newAccount.setUserPhone(info);
                    break;
                case 4:
                    newAccount.setUserAddress(info);
                    break;
            }

            editPersonalAccountByAPI(newAccount);
        });
    }

    private void editPersonalAccountByAPI(Account account) {
        AccountService accountService = RetrofitAPI.getInstance().create(AccountService.class);
        Call<Void> call = accountService.editAccount(account.getUserID(), account);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    afterEdit();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                afterEdit();
            }
        });
    }

    public void redirectBack() {
        activity.finish();
        activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

    private void afterEdit() {
        Toast.makeText(activity, "Chỉnh sửa thành công", Toast.LENGTH_SHORT).show();
        getAccountFromAPI(account.getUserID());
        closeEditor();
    }
}
