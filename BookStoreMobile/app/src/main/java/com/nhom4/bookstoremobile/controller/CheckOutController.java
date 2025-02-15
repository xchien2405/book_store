package com.nhom4.bookstoremobile.controller;

import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nhom4.bookstoremobile.R;
import com.nhom4.bookstoremobile.activity.ViewOrderDetails;
import com.nhom4.bookstoremobile.adapter.OrderItemAdapter;
import com.nhom4.bookstoremobile.converter.Converter;
import com.nhom4.bookstoremobile.entities.Account;
import com.nhom4.bookstoremobile.entities.Book;
import com.nhom4.bookstoremobile.entities.CartItem;
import com.nhom4.bookstoremobile.entities.OrderDTO;
import com.nhom4.bookstoremobile.repositories.CartDAO;
import com.nhom4.bookstoremobile.repositories.CartTable;
import com.nhom4.bookstoremobile.retrofit.DefaultURL;
import com.nhom4.bookstoremobile.retrofit.RetrofitAPI;
import com.nhom4.bookstoremobile.service.BookService;
import com.nhom4.bookstoremobile.service.OrderService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOutController {
    private final List<TextView> btnList = new ArrayList<>();
    private final Boolean isBuyNow;
    private final Activity activity;
    private OrderItemAdapter adapter;
    private List<CartItem> orderItemList;
    private String paymentMethod;


    public CheckOutController(Activity activity, Boolean isBuyNow) {
        this.activity = activity;
        this.isBuyNow = isBuyNow;
    }

    public Activity getActivity() {
        return activity;
    }

    private List<Boolean> convertArrayToList(boolean[] booleanArray) {
        List<Boolean> booleanList = new ArrayList<>();
        for (boolean b : booleanArray) {
            booleanList.add(b);
        }
        return booleanList;
    }

    public void addBtnToList(TextView btn) {
        btnList.add(btn);
    }

    public void setEffect(TextView clickedBtn) {
        if (clickedBtn.getText().equals("Thanh toán khi nhận hàng")) {
            paymentMethod = "cod";
        } else {
            paymentMethod = "online";
        }

        for (TextView button : btnList) {
            if (button == clickedBtn) {
                button.setBackgroundResource(R.drawable.selection_border_rounded);
                continue;
            }
            button.setBackgroundResource(R.drawable.gray_border_rounded);
        }
    }

    public void getOrderItemList(boolean[] checkedArray) {
        List<Boolean> checkedList = convertArrayToList(checkedArray);
        orderItemList = CartDAO.getInstance(activity).getCartData();

        if (orderItemList.size() != 0) {
            for (int i = 0; i < orderItemList.size(); i++) {
                if (checkedList.get(i)) {
                    getBookDetailFromAPI(orderItemList.get(i));
                } else {
                    checkedList.remove(i);
                    orderItemList.remove(i);
                    i--;
                }
            }
        }

        RecyclerView recyclerView = activity.findViewById(R.id.orderItemList);

        adapter = new OrderItemAdapter(activity, orderItemList, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    public void buyNow(CartItem cartItem) {
        getBookDetailFromAPI(cartItem);

        orderItemList = new ArrayList<>();
        orderItemList.add(cartItem);

        RecyclerView recyclerView = activity.findViewById(R.id.orderItemList);

        adapter = new OrderItemAdapter(activity, orderItemList, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    private void getBookDetailFromAPI(CartItem cartItem) {
        BookService bookService = RetrofitAPI.getInstance().create(BookService.class);
        Call<Book> call = bookService.getBookDetails(cartItem.getBookID());
        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if (response.isSuccessful()) {
                    Book book = response.body();
                    String imageURL = DefaultURL.getUrl() + book.getBookImage();
                    book.setBookImage(imageURL);
                    cartItem.setBook(book);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
            }
        });
    }

    public void createOrder(Account account) {
        if (account.isAdmin()) {
            Toast.makeText(activity, "Vui lòng không dùng tài khoản quản trị để đặt hàng", Toast.LENGTH_SHORT).show();
            return;
        }

        String phone = account.getUserPhone();
        String address = account.getUserAddress();
        if (phone == null || phone.trim().isEmpty()) {
            Toast.makeText(activity, "Vui lòng kiểm tra lại số điện thoại", Toast.LENGTH_SHORT).show();
            return;
        } else if (address == null || address.trim().isEmpty()) {
            Toast.makeText(activity, "Vui lòng kiểm tra lại địa chỉ", Toast.LENGTH_SHORT).show();
            return;
        }

        OrderDTO orderDTO = new OrderDTO();
        List<String> bookList = new ArrayList<>();
        List<String> quantityList = new ArrayList<>();

        for (CartItem item : orderItemList) {
            bookList.add(item.getBookID());
            quantityList.add(String.valueOf(item.getQuantity()));
        }

        orderDTO.setBookList(bookList);
        orderDTO.setQuantityList(quantityList);

        orderDTO.setPrice(calculatePrice());
        orderDTO.setPhone(phone);
        orderDTO.setAddress(address);
        orderDTO.setPaymentMethod(paymentMethod);

        OrderService orderService = RetrofitAPI.getInstance().create(OrderService.class);
        Call<String> call = orderService.createOrder(account.getUserID(), orderDTO);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String orderID = response.body();
                    if(!isBuyNow) {
                        clearCart();
                    }
                    redirectToOrderDetails(orderID);
                    Toast.makeText(activity, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    private void clearCart() {
        CartTable cartTable = new CartTable(activity);
        for (CartItem item : orderItemList) {
            cartTable.removeFromCart(item.getBookID());
        }
    }

    private String calculatePrice() {
        int totalPrice = 0;
        for (CartItem item : orderItemList) {
            Book book = item.getBook();

            if (book != null) {
                int price = Converter.currencyToNumber(book.getBookPrice());
                totalPrice += (item.getQuantity() * price);
            }
        }
        return Converter.numberToCurrency(totalPrice);
    }

    public void redirectToOrderDetails(String orderID) {
        Intent intent = new Intent(activity, ViewOrderDetails.class);
        intent.putExtra("orderID", orderID);
        activity.startActivity(intent);
        activity.finish();
        activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }

    public void redirectBack() {
        activity.finish();
        activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }
}
