package com.nhom4.bookstoremobile.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nhom4.bookstoremobile.R;
import com.nhom4.bookstoremobile.controller.ManageEditBookController;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ManageEditBook extends AppCompatActivity {
    private static final int PICK_IMAGE = 1;
    private ManageEditBookController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        setListener();

        controller = new ManageEditBookController(this);

        controller.setBookData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            controller.setSelectedImage(data.getData());
            try {
                InputStream inputStream = getContentResolver().openInputStream(controller.getSelectedImage());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                controller.getImagePreview().setImageBitmap(bitmap);
                controller.setChangeImage(true);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void openImagePickLayout() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    private void setListener() {
        findViewById(R.id.addImageBtn).setOnClickListener(v -> openImagePickLayout());
        findViewById(R.id.addBookBtn).setOnClickListener(v -> controller.editBookByAPI());
        findViewById(R.id.backBtn).setOnClickListener(v -> controller.redirectBack());
    }
}
