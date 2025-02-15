package com.nhom4.bookstoremobile.controller;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nhom4.bookstoremobile.R;
import com.nhom4.bookstoremobile.activity.ViewBookDetails;
import com.nhom4.bookstoremobile.entities.Book;
import com.nhom4.bookstoremobile.retrofit.DefaultURL;
import com.nhom4.bookstoremobile.retrofit.RetrofitAPI;
import com.nhom4.bookstoremobile.service.BookService;
import com.nhom4.bookstoremobile.service.ExceptionHandler;

import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageEditBookController {
    private final Activity activity;
    private final Book book;
    boolean changeImage = false;
    private ImageView imagePreview;
    private Uri selectedImage;

    public ManageEditBookController(Activity activity) {
        this.activity = activity;
        book = getDataFromIntent();
        imagePreview = activity.findViewById(R.id.imagePreview);
        setUpLayout();
    }

    public Uri getSelectedImage() {
        return selectedImage;
    }

    public void setSelectedImage(Uri selectedImage) {
        this.selectedImage = selectedImage;
    }

    public void setChangeImage(boolean changeImage) {
        this.changeImage = changeImage;
    }

    public ImageView getImagePreview() {
        return imagePreview;
    }


    private Book getDataFromIntent() {
        String book_ID = activity.getIntent().getStringExtra("book_id");
        String book_Name = activity.getIntent().getStringExtra("book_name");
        String book_HinhAnh = activity.getIntent().getStringExtra("book_HinhAnh");
        String book_TacGia = activity.getIntent().getStringExtra("book_TacGia");
        String book_NhaCungCap = activity.getIntent().getStringExtra("book_NhaCungCap");
        int book_TonKho = activity.getIntent().getIntExtra("book_TonKho", 0);
        String book_GiaR = activity.getIntent().getStringExtra("book_Gia");
        double book_TrongLuong = activity.getIntent().getDoubleExtra("book_TrongLuong", 0.0);
        String book_KickThuoc = activity.getIntent().getStringExtra("book_KickThuoc");
        String book_GioiThieu = activity.getIntent().getStringExtra("book_GioiThieu");

        return new Book(book_ID, book_Name, book_HinhAnh, book_TacGia, book_NhaCungCap, book_TonKho, book_GiaR, book_TrongLuong, book_KickThuoc, book_GioiThieu);
    }

    private void setUpLayout() {
        TextView addBook = activity.findViewById(R.id.titleTxtView);
        addBook.setText("Chỉnh sửa sản phẩm");

        Button saveBtn = activity.findViewById(R.id.addBookBtn);
        saveBtn.setText("Lưu");
    }

    public void editBookByAPI() {
        Book newBook = new ExceptionHandler().handleExceptionBook(activity);
        if (newBook == null) {
            return;
        }
        newBook.setBookID(book.getBookID());
        String imageURL = book.getBookImage().replace(DefaultURL.getUrl(), "");
        newBook.setBookImage(imageURL);

        MultipartBody.Part imagePart;

        if (changeImage) {
            imagePart = prepareFilePart(selectedImage);
        } else {
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), "");
            imagePart = MultipartBody.Part.createFormData("image", "", requestBody);
            ;
        }

        BookService bookService = RetrofitAPI.getInstance().create(BookService.class);
        Call<Void> call = bookService.editBook(newBook.getBookID(), imagePart, newBook);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(activity, "Chỉnh sửa thành công", Toast.LENGTH_SHORT).show();
                    redirectToBookDetails();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }

    private MultipartBody.Part prepareFilePart(Uri uri) {
        try {
            InputStream inputStream = activity.getContentResolver().openInputStream(uri);
            byte[] fileBytes = new byte[inputStream.available()];
            inputStream.read(fileBytes);
            RequestBody requestFile = RequestBody.create(MediaType.parse(activity.getContentResolver().getType(uri)), fileBytes);
            return MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setBookData() {
        EditText nameEditText = activity.findViewById(R.id.add_name);
        EditText priceEditText = activity.findViewById(R.id.add_price);
        EditText authorEditText = activity.findViewById(R.id.add_author);
        EditText publisherEditText = activity.findViewById(R.id.add_publisher);
        EditText weightEditText = activity.findViewById(R.id.add_weight);
        EditText sizeEditText = activity.findViewById(R.id.add_size);
        EditText stockEditText = activity.findViewById(R.id.add_stock);
        EditText introductionEditText = activity.findViewById(R.id.add_introduction);

        Glide.with(activity)
                .load(book.getBookImage())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .fitCenter()
                .into(imagePreview);

        nameEditText.setText(book.getBookName());

        String priceRaw = book.getBookPrice();
        String price = priceRaw.replaceAll("[^0-9]", "");
        priceEditText.setText(price);

        authorEditText.setText(book.getBookAuthor());
        publisherEditText.setText(book.getBookPublisher());
        weightEditText.setText(String.valueOf(book.getBookWeight()));
        sizeEditText.setText(book.getBookSize());
        stockEditText.setText(String.valueOf(book.getBookStock()));
        introductionEditText.setText(book.getBookIntroduction());
    }

    private void redirectToBookDetails() {
        Intent intent = new Intent(activity, ViewBookDetails.class);
        intent.putExtra("book_id", book.getBookID());
        activity.startActivity(intent);
        activity.finish();
    }

    public void redirectBack() {
        activity.finish();
        activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }
}
