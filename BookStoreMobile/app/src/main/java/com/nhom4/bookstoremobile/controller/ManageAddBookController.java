package com.nhom4.bookstoremobile.controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import com.nhom4.bookstoremobile.R;
import com.nhom4.bookstoremobile.activity.ViewBookDetails;
import com.nhom4.bookstoremobile.activity.ViewBookList;
import com.nhom4.bookstoremobile.entities.Book;
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

public class ManageAddBookController {
    private final Activity activity;
    private final ImageView imagePreview;
    private Uri selectedImage;

    public ManageAddBookController(Activity activity) {
        this.activity = activity;
        imagePreview = activity.findViewById(R.id.imagePreview);
    }

    public Uri getSelectedImage() {
        return selectedImage;
    }

    public void setSelectedImage(Uri selectedImage) {
        this.selectedImage = selectedImage;
    }

    public void addBook() {
        if (selectedImage == null) {
            Rect rectangle = new Rect();
            Toast.makeText(activity, "Vui lòng chọn ảnh", Toast.LENGTH_SHORT).show();
            imagePreview.requestFocus();
            imagePreview.getGlobalVisibleRect(rectangle);
            imagePreview.requestRectangleOnScreen(rectangle);
            return;
        }

        MultipartBody.Part imagePart = prepareFilePart(selectedImage);

        Book newBook = new ExceptionHandler().handleExceptionBook(activity);

        BookService bookService = RetrofitAPI.getInstance().create(BookService.class);
        Call<String> call = bookService.addBook(imagePart, newBook);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String bookID = response.body();
                    if (bookID != null) {
                        Toast.makeText(activity, "Thêm sách thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, ViewBookDetails.class);
                        intent.putExtra("book_id", bookID);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(activity, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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

    public ImageView getImagePreview() {
        return imagePreview;
    }

    public void redirectBack() {
        Intent intent = new Intent(activity, ViewBookList.class);
        activity.startActivity(intent);
        activity.finish();
        activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }
}
