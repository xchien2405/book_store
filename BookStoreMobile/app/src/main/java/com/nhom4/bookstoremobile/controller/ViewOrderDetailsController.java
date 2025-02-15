package com.nhom4.bookstoremobile.controller;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nhom4.bookstoremobile.R;
import com.nhom4.bookstoremobile.adapter.OrderItemAdapter;
import com.nhom4.bookstoremobile.converter.Converter;
import com.nhom4.bookstoremobile.entities.Account;
import com.nhom4.bookstoremobile.entities.Book;
import com.nhom4.bookstoremobile.entities.CartItem;
import com.nhom4.bookstoremobile.entities.Order;
import com.nhom4.bookstoremobile.entities.OrderDetails;
import com.nhom4.bookstoremobile.repositories.AccountDAO;
import com.nhom4.bookstoremobile.retrofit.DefaultURL;
import com.nhom4.bookstoremobile.retrofit.RetrofitAPI;
import com.nhom4.bookstoremobile.service.OrderService;
import com.nhom4.bookstoremobile.service.Popup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewOrderDetailsController {
    private final Activity activity;
    private final String orderID;

    public ViewOrderDetailsController(Activity activity, String orderID) {
        this.activity = activity;
        this.orderID = orderID;
    }

    public void getOrderDetailsFromAPI() {
        OrderService orderService = RetrofitAPI.getInstance().create(OrderService.class);

        Call<OrderDetails> call = orderService.getOrderDetails(orderID);
        call.enqueue(new Callback<OrderDetails>() {
            @Override
            public void onResponse(Call<OrderDetails> call, Response<OrderDetails> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        OrderDetails orderDetails = response.body();
                        setOrderDetailsToLayout(orderDetails);
                        setTotalQuantity(orderDetails);
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderDetails> call, Throwable t) {
            }
        });
    }

    public void getOrderFromAPI() {
        OrderService orderService = RetrofitAPI.getInstance().create(OrderService.class);
        Call<Order> call = orderService.getOrder(orderID);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Order order = response.body();
                        setOrderToLayout(order);
                    }
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
            }
        });
    }

    private void setOrderToLayout(Order order) {
        Button editStatusBtn = activity.findViewById(R.id.editStatusBtn);
        editStatusBtn.setEnabled(order.getOrderStatusInt() < 3);

        TextView userNameTextView = activity.findViewById(R.id.userName);
        TextView userPhoneTextView = activity.findViewById(R.id.userPhone);
        TextView userAddressTextView = activity.findViewById(R.id.userAddress);

        TextView orderIDTextView = activity.findViewById(R.id.orderID);
        TextView orderStatusTextView = activity.findViewById(R.id.orderStatus);
        TextView orderTimeTextView = activity.findViewById(R.id.orderTime);

        TextView productPriceTextView = activity.findViewById(R.id.productPrice);
        TextView totalPriceTextView = activity.findViewById(R.id.totalPrice);

        userNameTextView.setText(order.getUserID());
        userPhoneTextView.setText(order.getOrderPhone());
        userAddressTextView.setText(order.getOrderAddress());

        orderIDTextView.setText(order.getOrderID());
        orderStatusTextView.setText(order.getOrderStatus());

        String orderTimeString = Converter.dateToString(order.getOrderTime());
        orderTimeTextView.setText(orderTimeString);

        productPriceTextView.setText(order.getOrderPrice());

        int priceInt = Converter.currencyToNumber(order.getOrderPrice());
        priceInt += 20000;
        String priceString = Converter.numberToCurrency(priceInt);

        totalPriceTextView.setText(priceString);
    }

    private void setTotalQuantity(OrderDetails orderDetails) {
        TextView totalProductTextView = activity.findViewById(R.id.totalProduct);

        int totalQuantity = 0;
        for (OrderDetails.OrderItem orderItem : orderDetails.getOrderItemList()) {
            totalQuantity += orderItem.getQuantity();
        }

        String itemQuantity = "(" + totalQuantity + " sản phẩm):";
        totalProductTextView.setText(itemQuantity);
    }

    public void setOrderDetailsToLayout(OrderDetails orderDetails) {
        List<CartItem> orderItemList = new ArrayList<>();

        for (OrderDetails.OrderItem orderItem : orderDetails.getOrderItemList()) {
            CartItem cartItem = new CartItem(orderItem.getBookID(), orderItem.getQuantity());
            Book book = orderItem.getBook();
            String imageURL = DefaultURL.getUrl() + book.getBookImage();
            book.setBookImage(imageURL);
            cartItem.setBook(book);
            orderItemList.add(cartItem);
        }

        RecyclerView recyclerView = activity.findViewById(R.id.orderItemList);
        OrderItemAdapter adapter = new OrderItemAdapter(activity, orderItemList, null);
        adapter.setmRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    public void reload(SwipeRefreshLayout pullToRefresh) {
        getOrderFromAPI();
        getOrderDetailsFromAPI();
        checkAdmin();
        pullToRefresh.setRefreshing(false);
    }

    public void redirectBack() {
        activity.finish();
        activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

    public void checkAdmin() {
        ConstraintLayout adminLayout = activity.findViewById(R.id.bottomLayout);
        Button editStatusBtn = activity.findViewById(R.id.editStatusBtn);

        Account account = AccountDAO.getInstance(activity).getAccountData();
        if (account != null) {
            if (account.isAdmin()) {
                adminLayout.setVisibility(View.VISIBLE);
                editStatusBtn.setOnClickListener(v -> showEditStatusPopup());
                return;
            }
        }

        adminLayout.setVisibility(View.GONE);
        editStatusBtn.setOnClickListener(null);
    }

    private void showEditStatusPopup() {
        Popup.showStatusEdit(activity, "Cập nhật trạng thái", (dialog, which) -> {
            TextView orderIDTextView = activity.findViewById(R.id.orderID);
            String orderID = orderIDTextView.getText().toString();

            editOrderStatusByAPI(orderID, which);
        });

    }

    private void editOrderStatusByAPI(String orderID, int choice) {
        OrderService orderService = RetrofitAPI.getInstance().create(OrderService.class);
        Call<Void> call = orderService.editOrderStatus(orderID, choice);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(activity, "Cập nhật trạng thái thành công", Toast.LENGTH_SHORT).show();
                    activity.recreate();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }
}
