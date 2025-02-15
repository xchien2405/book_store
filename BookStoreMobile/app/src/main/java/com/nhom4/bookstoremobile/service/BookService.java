package com.nhom4.bookstoremobile.service;


import androidx.annotation.Nullable;

import com.nhom4.bookstoremobile.entities.Book;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface BookService {
    @GET("books")
    Call<List<Book>> getBookList();

    @GET("topselling")
    Call<List<Book>> getTopSelling();

    @GET("books/{id}")
    Call<Book> getBookDetails(@Path("id") String bookId);

    @Multipart
    @POST("books")
    Call<String> addBook(
            @Part MultipartBody.Part image,
            @Part("book") Book book
    );

    @Multipart
    @PUT("books/{id}")
    Call<Void> editBook(
            @Path("id") String bookId,
            @Part @Nullable MultipartBody.Part image,
            @Part("book") Book book
    );

    @DELETE("books/{id}")
    Call<String> deleteBook(@Path("id") String id);
}
