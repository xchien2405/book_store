package com.nhom4.bookstoremobile.controller;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nhom4.bookstoremobile.R;
import com.nhom4.bookstoremobile.activity.CheckOut;
import com.nhom4.bookstoremobile.activity.ManageEditBook;
import com.nhom4.bookstoremobile.activity.ViewAccount;
import com.nhom4.bookstoremobile.activity.ViewCart;
import com.nhom4.bookstoremobile.adapter.BookAdapter;
import com.nhom4.bookstoremobile.entities.Account;
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

public class ViewBookDetailsController {
    private final Activity activity;
    private final String bookID;
    private Book book;

    public ViewBookDetailsController(Activity activity, String bookID) {
        this.activity = activity;
        this.bookID = bookID;
    }

    public void getBookDetailFromAPI() {
        BookService bookService = RetrofitAPI.getInstance().create(BookService.class);
        Call<Book> call = bookService.getBookDetails(bookID);
        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if (response.isSuccessful()) {
                    book = response.body();
                    String imageUrl = DefaultURL.getUrl() + book.getBookImage();
                    book.setBookImage(imageUrl);
                    setData(book);
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
            }
        });
    }

    private void showDeleteConfirm() {
        TextView idTextView = activity.findViewById(R.id.id_TxtView);
        String id = idTextView.getText().toString();
        Popup.showConfirm(activity, "Xác nhận", "Bạn muốn xóa sản phẩm " + id + "?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteBookByAPI();
            }
        });
    }

    private void deleteBookByAPI() {
        BookService bookService = RetrofitAPI.getInstance().create(BookService.class);
        Call<String> call = bookService.deleteBook(book.getBookID());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(activity, "Xóa thành công sản phẩm " + book.getBookID(), Toast.LENGTH_SHORT).show();
                    activity.finish();
                } else {
                    Toast.makeText(activity, "Vui lòng hoàn thành các đơn hàng có sản phẩm " + book.getBookID() + " để xóa", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    public void getBookListFromAPI() {
        BookService bookService = RetrofitAPI.getInstance().create(BookService.class);
        Call<List<Book>> call2 = bookService.getBookList();
        call2.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful()) {
                    List<Book> bookList = response.body();
                    if (book != null) {
                        for (Book bookInList : bookList) {
                            if (bookInList.getBookID().equals(book.getBookID())) {
                                bookList.remove(bookInList);
                                break;
                            }
                        }
                    }

                    for (Book book : bookList) {
                        String imageUrl = DefaultURL.getUrl() + book.getBookImage();
                        book.setBookImage(imageUrl);
                    }

                    RecyclerView recyclerView = activity.findViewById(R.id.detail_RecyclerView);
                    BookAdapter adapter = new BookAdapter(activity, bookList, recyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
            }
        });
    }

    private void setData(Book book) {
        ImageView bookImage = activity.findViewById(R.id.book_Image);
        TextView nameTextView = activity.findViewById(R.id.name_TxtView);
        TextView soldTextView = activity.findViewById(R.id.sold_TxtView);
        TextView priceTextView = activity.findViewById(R.id.price_TxtView);
        TextView idTextView = activity.findViewById(R.id.id_TxtView);
        TextView authorTextView = activity.findViewById(R.id.author_TxtView);
        TextView publisherTextView = activity.findViewById(R.id.publisher_TxtView);
        TextView weightTextView = activity.findViewById(R.id.weight_TxtView);
        TextView sizeTextView = activity.findViewById(R.id.size_TxtView);
        TextView introductionTextView = activity.findViewById(R.id.introduction_TxtView);

        Glide.with(activity)
                .load(book.getBookImage())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .fitCenter()
                .into(bookImage);

        nameTextView.setText(book.getBookName());
        soldTextView.setText("Đã bán " + book.getBookSold());
        priceTextView.setText(book.getBookPrice());
        idTextView.setText(book.getBookID());
        authorTextView.setText(book.getBookAuthor());
        publisherTextView.setText(book.getBookPublisher());
        weightTextView.setText(book.getBookWeight() + " gr");
        sizeTextView.setText(book.getBookSize());
        introductionTextView.setText(book.getBookIntroduction());
    }

    public void openAddCartView(int choice) {
        CartItem cartItem = CartDAO.getInstance(activity).getCartItem(bookID);

        if (cartItem != null) {
            if (cartItem.getQuantity() >= book.getBookStock() || book.getBookStock() <= 0) {
                Toast.makeText(activity, "Số lượng sản phẩm trong giỏ hàng đã đạt số lượng tối đa", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (book != null) {
            if (book.getBookStock() <= 0) {
                Toast.makeText(activity, "Sản phẩm đã hết hàng", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        LayoutInflater inflater = activity.getLayoutInflater();
        View addCartLayout = inflater.inflate(R.layout.main_add_cart_item_layout, null);
        if (choice == 1) {
            Button button = addCartLayout.findViewById(R.id.addToCartBtn);
            button.setText("Mua ngay");
        }

        FrameLayout layoutContainer = activity.findViewById(R.id.addCart_Layout);
        layoutContainer.addView(addCartLayout);

        addCartLayout.setClickable(true);
        addCartLayout.setFocusable(true);
        activity.findViewById(R.id.overlayLayout).setVisibility(View.VISIBLE);

        setDataToAddCart(addCartLayout);

        setListenerAddLayout(addCartLayout, choice);

        Animation slideUpAnimation = AnimationUtils.loadAnimation(activity, R.anim.slide_up);
        addCartLayout.startAnimation(slideUpAnimation);
    }


    private void setDataToAddCart(View addCartLayout) {
        ImageView imageView = addCartLayout.findViewById(R.id.imageView);
        TextView nameTextView = addCartLayout.findViewById(R.id.name_TxtView);
        TextView authorTextView = addCartLayout.findViewById(R.id.author_TxtView);
        TextView priceTextView = addCartLayout.findViewById(R.id.price_TxtView);


        Glide.with(activity)
                .load(book.getBookImage())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .fitCenter()
                .into(imageView);

        nameTextView.setText(book.getBookName());
        authorTextView.setText(book.getBookAuthor());
        priceTextView.setText(book.getBookPrice());
    }

    private void addItemToCart(String id, int quantity) {
        try (CartTable cartTable = new CartTable(activity)) {
            CartItem cartItem = CartDAO.getInstance(activity).getCartItem(id);

            if (cartItem != null) {
                cartTable.updateQuantityItem(id, cartItem.getQuantity() + quantity);
            } else {
                cartTable.addToCart(id, quantity);
            }
        }
    }

    public void closeAddCartView() {
        FrameLayout layoutContainer = activity.findViewById(R.id.addCart_Layout);
        View addCartLayout = layoutContainer.getChildAt(0);

        activity.findViewById(R.id.overlayLayout).setVisibility(View.GONE);
        addCartLayout.setClickable(false);
        addCartLayout.setFocusable(false);
        Animation slideDownAnimation = AnimationUtils.loadAnimation(activity, R.anim.slide_down);
        addCartLayout.startAnimation(slideDownAnimation);
        layoutContainer.removeAllViewsInLayout();
    }

    private void setListenerAddLayout(View addCart_Layout, int choice) {
        CartItem cartItem = CartDAO.getInstance(activity).getCartItem(bookID);
        EditText quantity_EditText = addCart_Layout.findViewById(R.id.quantity_EditText);

        addCart_Layout.findViewById(R.id.closeBtn).setOnClickListener(v -> closeAddCartView());
        addCart_Layout.findViewById(R.id.addToCartBtn).setOnClickListener(v -> clickAddBtn(quantity_EditText, choice));
        quantity_EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String quantityRaw = s.toString();
                int quantity = 0;
                if (!quantityRaw.isEmpty()) {
                    quantity = Integer.parseInt(quantityRaw);
                }

                int quantityInCart = 0;
                if (cartItem != null) {
                    quantityInCart = cartItem.getQuantity();
                }

                if (quantity <= 0) {
                    Toast.makeText(activity, "Số lượng tối thiểu là 1", Toast.LENGTH_SHORT).show();
                    quantity_EditText.setText("1");
                } else if (book.getBookStock() < (quantity + quantityInCart)) {
                    Toast.makeText(activity, "Số lượng sản phẩm trong giỏ hàng đã đạt số lượng tối đa", Toast.LENGTH_SHORT).show();
                    int remain = book.getBookStock() - quantityInCart;
                    quantity_EditText.setText(String.valueOf(remain));
                }
            }
        });
        addCart_Layout.findViewById(R.id.plusBtn).setOnClickListener(v -> clickPlusBtn(cartItem, quantity_EditText));
        addCart_Layout.findViewById(R.id.minusBtn).setOnClickListener(v -> clickMinusBtn(quantity_EditText));
    }

    private void clickMinusBtn(EditText quantity_EditText) {
        String quantityRaw = quantity_EditText.getText().toString();
        int quantity = Integer.parseInt(quantityRaw) - 1;

        if (quantity <= 0) {
            Toast.makeText(activity, "Số lượng tối thiểu là 1", Toast.LENGTH_SHORT).show();
        } else {
            quantity_EditText.setText(String.valueOf(quantity));
        }
    }

    private void clickPlusBtn(CartItem cartItem, EditText quantity_EditText) {
        String quantityRaw = quantity_EditText.getText().toString();
        int quantityInCart = 0;
        if (cartItem != null) {
            quantityInCart = cartItem.getQuantity();
        }

        int quantity = Integer.parseInt(quantityRaw) + 1;
        if (book.getBookStock() < (quantity + quantityInCart)) {
            Toast.makeText(activity, "Số lượng sản phẩm trong giỏ hàng đã đạt số lượng tối đa", Toast.LENGTH_SHORT).show();
        } else {
            quantity_EditText.setText(String.valueOf(quantity));
        }
    }

    private void clickAddBtn(EditText quantity_EditText, int choice) {
        String quantityRaw = quantity_EditText.getText().toString();
        int quantity = Integer.parseInt(quantityRaw);
        if (choice == 1) {
            redirectToBuyNow(bookID, quantity);
        } else {
            addItemToCart(book.getBookID(), quantity);
            Toast.makeText(activity, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
            closeAddCartView();
        }

    }

    public void redirectBack() {
        activity.finish();
        activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

    public void reload(SwipeRefreshLayout pullToRefresh) {
        getBookDetailFromAPI();
        getBookListFromAPI();
        setUpAdminLayout();
        pullToRefresh.setRefreshing(false);
    }

    public void redirectToCart() {
        Intent intent = new Intent(activity, ViewCart.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }

    public void redirectToAccount() {
        Intent intent = new Intent(activity, ViewAccount.class);
        activity.startActivity(intent);
    }

    public void redirectToBuyNow(String bookID, int quantity) {
        if (AccountDAO.getInstance(activity).getAccountData() == null) {
            Toast.makeText(activity, "Vui lòng đăng nhập để mua hàng", Toast.LENGTH_SHORT).show();
            redirectToAccount();
            return;
        }

        closeAddCartView();
        Intent intent = new Intent(activity, CheckOut.class);
        intent.putExtra("isBuyNow", true);
        intent.putExtra("bookID", bookID);
        intent.putExtra("quantity", quantity);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }

    private void redirectToEditBook() {
        Intent intent = new Intent(activity, ManageEditBook.class);
        intent.putExtra("book_id", book.getBookID());
        intent.putExtra("book_name", book.getBookName());
        intent.putExtra("book_HinhAnh", book.getBookImage());
        intent.putExtra("book_TacGia", book.getBookAuthor());
        intent.putExtra("book_NhaCungCap", book.getBookPublisher());
        intent.putExtra("book_TonKho", book.getBookStock());
        intent.putExtra("book_Gia", book.getBookPrice());
        intent.putExtra("book_TrongLuong", book.getBookWeight());
        intent.putExtra("book_KickThuoc", book.getBookSize());
        intent.putExtra("book_GioiThieu", book.getBookIntroduction());
        activity.startActivity(intent);
        activity.finish();
        activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }

    public void setUpAdminLayout() {
        Button editBtn = activity.findViewById(R.id.editBtn);
        Button deleteBtn = activity.findViewById(R.id.deleteBtn);

        Account account = AccountDAO.getInstance(activity).getAccountData();
        if (account != null) {
            if (account.isAdmin()) {
                editBtn.setVisibility(View.VISIBLE);
                deleteBtn.setVisibility(View.VISIBLE);
                editBtn.setOnClickListener(v -> redirectToEditBook());
                deleteBtn.setOnClickListener(v -> showDeleteConfirm());
                return;
            }
        }

        editBtn.setVisibility(View.GONE);
        editBtn.setOnClickListener(null);
        deleteBtn.setVisibility(View.GONE);
        deleteBtn.setOnClickListener(null);
    }
}
