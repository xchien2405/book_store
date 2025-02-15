package com.nhom4.bookstoremobile.controller;

import android.app.Activity;
import android.content.Intent;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nhom4.bookstoremobile.MainActivity;
import com.nhom4.bookstoremobile.R;
import com.nhom4.bookstoremobile.activity.CheckOut;
import com.nhom4.bookstoremobile.activity.ViewAccount;
import com.nhom4.bookstoremobile.activity.ViewCart;
import com.nhom4.bookstoremobile.adapter.CartItemAdapter;
import com.nhom4.bookstoremobile.entities.Book;
import com.nhom4.bookstoremobile.entities.CartItem;
import com.nhom4.bookstoremobile.repositories.AccountDAO;
import com.nhom4.bookstoremobile.repositories.CartDAO;
import com.nhom4.bookstoremobile.repositories.CartTable;
import com.nhom4.bookstoremobile.retrofit.DefaultURL;
import com.nhom4.bookstoremobile.retrofit.RetrofitAPI;
import com.nhom4.bookstoremobile.service.BookService;
import com.nhom4.bookstoremobile.service.Popup;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewCartController {
    private final Activity activity;
    private final CartTable cartTable;
    private final CheckBox totalCheckBox;
    private List<CartItem> cart;
    private CartItemAdapter adapter;

    public ViewCartController(ViewCart activity) {
        this.activity = activity;
        cartTable = new CartTable(activity);
        totalCheckBox = activity.findViewById(R.id.totalCheckBox);
    }

    public void getCartData() {
        cart = CartDAO.getInstance(activity).getCartData();

        if (cart.size() != 0) {
            for (CartItem cartItem : cart) {
                getBookDetailFromAPI(cartItem);
            }

            RecyclerView recyclerView = activity.findViewById(R.id.cartItemList);

            adapter = new CartItemAdapter(activity, cart, recyclerView);

            adapter.setTotalCheckBox(totalCheckBox);
            adapter.setPaymentBtn(activity.findViewById(R.id.paymentBtn));
            adapter.setTotalPriceTxtView(activity.findViewById(R.id.totalPrice));

            recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(adapter);
        }
    }

    private void getBookDetailFromAPI(CartItem cartItem) {
        BookService bookService = RetrofitAPI.getInstance().create(BookService.class);
        Call<Book> call = bookService.getBookDetails(cartItem.getBookID());
        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if (response.isSuccessful()) {
                    Book book = response.body();
                    if (book != null) {
                        int stock = book.getBookStock();
                        if (stock <= 0) {
                            Toast.makeText(activity, "Sản phẩm " + cartItem.getBookID() + " đã được bán hết", Toast.LENGTH_SHORT).show();
                            cartTable.removeFromCart(cartItem.getBookID());
                            cart.remove(cartItem);
                            getCartData();
                            return;
                        }

                        if (cartItem.getQuantity() > stock) {
                            Toast.makeText(activity, "Số lượng sản phẩm " + cartItem.getBookID() + " đã được thay đổi", Toast.LENGTH_SHORT).show();
                            cartTable.updateQuantityItem(cartItem.getBookID(), stock);
                            cartItem.setQuantity(stock);
                        }

                        String imageUrl = DefaultURL.getUrl() + book.getBookImage();
                        book.setBookImage(imageUrl);
                        cartItem.setBook(book);
                    }
                } else {
                    cartTable.removeFromCart(cartItem.getBookID());
                    cart.remove(cartItem);
                    Toast.makeText(activity, "Sản phẩm " + cartItem.getBookID() + " không còn được bán", Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {

            }
        });
    }

    public void showConfirmationPopup() {
        Popup.showConfirm(activity, "Xác nhận", "Bạn muốn xóa những sản phẩm được chọn?", (dialog, which) -> {
            adapter.deleteCartItem();
            totalCheckBox.setChecked(false);
        });
    }

    public void redirectBack() {
        activity.finish();
        activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

    public void redirectToMain() {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    public void redirectToAccount() {
        Intent intent = new Intent(activity, ViewAccount.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    public void redirectToCheckOut() {
        if (AccountDAO.getInstance(activity).getAccountData() == null) {
            Toast.makeText(activity, "Vui lòng đăng nhập để đặt hàng", Toast.LENGTH_SHORT).show();
            redirectToAccount();
        }

        if (adapter != null) {
            for (Boolean b : adapter.getIsChecked()) {
                if (b) {
                    Intent intent = new Intent(activity, CheckOut.class);
                    intent.putExtra("orderList", adapter.checkOut());
                    activity.startActivity(intent);
                    activity.finish();
                    activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);

                    return;
                }
            }

            Toast.makeText(activity, "Vui lòng chọn ít nhất 1 sản phẩm", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(activity, "Giỏ hàng hiện đang trống", Toast.LENGTH_SHORT).show();
        }
    }

    public void reload(SwipeRefreshLayout pullToRefresh) {
        getCartData();
        pullToRefresh.setRefreshing(false);
    }

    public void checkAll() {
        adapter.checkAllCartItem(totalCheckBox.isChecked());
    }
}
