package com.nhom4.bookstoremobile.controller;

import android.app.Activity;
import android.graphics.Color;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nhom4.bookstoremobile.R;
import com.nhom4.bookstoremobile.adapter.OrderAdapter;
import com.nhom4.bookstoremobile.entities.Book;
import com.nhom4.bookstoremobile.entities.Order;
import com.nhom4.bookstoremobile.repositories.AccountDAO;
import com.nhom4.bookstoremobile.retrofit.DefaultURL;
import com.nhom4.bookstoremobile.retrofit.RetrofitAPI;
import com.nhom4.bookstoremobile.service.OrderService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewOrderListController {
    private final Activity activity;
    private final String userID;
    private final List<TextView> btnList = new ArrayList<>();

    public ViewOrderListController(Activity activity, String userID) {
        this.activity = activity;
        this.userID = userID;
    }

    public void addBtnToList(TextView btn) {
        btnList.add(btn);
    }

    public void setEffect(TextView clickedBtn) {
        for (TextView textView : btnList) {
            if (textView == clickedBtn) {
                textView.setBackgroundResource(R.drawable.bottom_border_black);
                textView.setTextColor(Color.parseColor("#000000"));
                continue;
            }
            textView.setBackground(null);
            textView.setTextColor(Color.parseColor("#FF49454F"));
        }
    }

    public void getOrderFromAPI(int choice) {
        OrderService orderService = RetrofitAPI.getInstance().create(OrderService.class);
        Call<List<Order>> call;
        if (AccountDAO.getInstance(activity).getAccountData().isAdmin()) {
            call = orderService.getOrderList();
        } else {
            call = orderService.getPersonalOrders(userID);
        }
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful()) {
                    List<Order> orderList = response.body();

                    filterData(choice, orderList);

                    RecyclerView recyclerView = activity.findViewById(R.id.orderList);
                    OrderAdapter adapter = new OrderAdapter(activity, orderList, recyclerView);

                    recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
            }
        });
    }

    private void filterData(int choice, List<Order> orderList) {
        if (choice != 4) {
            for (int i = 0; i < orderList.size(); i++) {
                if (orderList.get(i).getOrderStatusInt() != choice) {
                    orderList.remove(i);
                    i--;
                }
            }
        }

        for (Order order : orderList) {
            Book book = order.getOrderFirstBook();
            String imageUrl = DefaultURL.getUrl() + book.getBookImage();
            book.setBookImage(imageUrl);
        }
    }

    public void redirectBack() {
        activity.finish();
        activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

    public void reload(SwipeRefreshLayout pullToRefresh) {
        setEffect(btnList.get(0));
        getOrderFromAPI(4);
        pullToRefresh.setRefreshing(false);
    }
}
