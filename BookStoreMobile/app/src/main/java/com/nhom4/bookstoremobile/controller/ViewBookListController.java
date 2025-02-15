package com.nhom4.bookstoremobile.controller;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nhom4.bookstoremobile.R;
import com.nhom4.bookstoremobile.activity.ManageAddBook;
import com.nhom4.bookstoremobile.adapter.BookAdapter;
import com.nhom4.bookstoremobile.entities.Account;
import com.nhom4.bookstoremobile.entities.Book;
import com.nhom4.bookstoremobile.repositories.AccountDAO;
import com.nhom4.bookstoremobile.retrofit.DefaultURL;
import com.nhom4.bookstoremobile.retrofit.RetrofitAPI;
import com.nhom4.bookstoremobile.service.BookService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewBookListController {
    private final Activity activity;

    public ViewBookListController(Activity activity) {
        this.activity = activity;
    }

    public void getBookListFromAPI() {
        BookService bookService = RetrofitAPI.getInstance().create(BookService.class);
        Call<List<Book>> call = bookService.getBookList();

        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful()) {
                    List<Book> bookList = response.body();

                    for (Book book : bookList) {
                        String imageUrl = DefaultURL.getUrl() + book.getBookImage();
                        book.setBookImage(imageUrl);
                    }

                    RecyclerView recyclerView = activity.findViewById(R.id.list_RecyclerView);

                    BookAdapter adapter = new BookAdapter(activity, bookList, recyclerView, false);

                    recyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
            }
        });
    }

    public void redirectToMain() {
        activity.finish();
        activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

    public void reload(SwipeRefreshLayout pullToRefresh) {
        getBookListFromAPI();
        checkAdmin();
        pullToRefresh.setRefreshing(false);
    }

    public void redirectToAddBook() {
        Intent intent = new Intent(activity, ManageAddBook.class);
        activity.startActivity(intent);
        activity.finish();
        activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }

    public void checkAdmin() {
        ImageButton addBtn = activity.findViewById(R.id.addBtn);

        Account account = AccountDAO.getInstance(activity).getAccountData();
        if (account != null) {
            if (account.isAdmin()) {
                addBtn.setVisibility(View.VISIBLE);
                addBtn.setOnClickListener(v -> redirectToAddBook());
                return;
            }
        }

        addBtn.setVisibility(View.GONE);
        addBtn.setOnClickListener(null);
    }
}
