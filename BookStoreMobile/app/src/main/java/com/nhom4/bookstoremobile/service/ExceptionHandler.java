package com.nhom4.bookstoremobile.service;

import android.app.Activity;
import android.graphics.Rect;
import android.widget.EditText;
import android.widget.Toast;

import com.nhom4.bookstoremobile.R;
import com.nhom4.bookstoremobile.entities.Book;

public class ExceptionHandler {
    public static void forcusError(EditText editText) {
        Rect point = new Rect();

        editText.requestFocus();
        editText.getGlobalVisibleRect(point);
        editText.requestRectangleOnScreen(point);
    }

    public Book handleExceptionBook(Activity activity) {
        EditText nameEditText = activity.findViewById(R.id.add_name);
        EditText priceEditText = activity.findViewById(R.id.add_price);
        EditText authorEditText = activity.findViewById(R.id.add_author);
        EditText publisherEditText = activity.findViewById(R.id.add_publisher);
        EditText weightEditText = activity.findViewById(R.id.add_weight);
        EditText sizeEditText = activity.findViewById(R.id.add_size);
        EditText stockEditText = activity.findViewById(R.id.add_stock);
        EditText introductionEditText = activity.findViewById(R.id.add_introduction);

        String name = nameEditText.getText().toString();
        String price = priceEditText.getText().toString();
        String author = authorEditText.getText().toString();
        String publisher = publisherEditText.getText().toString();
        String weight = weightEditText.getText().toString();
        String size = sizeEditText.getText().toString();
        String stock = stockEditText.getText().toString();
        String introduction = introductionEditText.getText().toString();

        if (name.isEmpty()) {
            Toast.makeText(activity, "Vui lòng nhập tên", Toast.LENGTH_SHORT).show();

            forcusError(nameEditText);
            return null;
        } else if (price.isEmpty()) {
            Toast.makeText(activity, "Vui lòng nhập giá", Toast.LENGTH_SHORT).show();

            forcusError(priceEditText);
            return null;
        } else if (author.isEmpty()) {
            Toast.makeText(activity, "Vui lòng nhập tên tác giả", Toast.LENGTH_SHORT).show();

            forcusError(authorEditText);
            return null;
        } else if (stock.isEmpty()) {
            Toast.makeText(activity, "Vui lòng nhập số lượng tồn kho", Toast.LENGTH_SHORT).show();

            forcusError(stockEditText);
            return null;
        } else if (introduction.isEmpty()) {
            Toast.makeText(activity, "Vui lòng nhập mô tả", Toast.LENGTH_SHORT).show();

            forcusError(introductionEditText);
            return null;
        }

        Book newBook = new Book();
        newBook.setBookName(name);
        newBook.setBookPrice(price);
        newBook.setBookAuthor(author);
        newBook.setBookPublisher(publisher);
        newBook.setBookSize(size);
        newBook.setBookStock(Integer.parseInt(stock));
        newBook.setBookIntroduction(introduction);

        if (!weight.isEmpty()) {
            newBook.setBookWeight(Double.parseDouble(weight));
        }

        return newBook;
    }
}
