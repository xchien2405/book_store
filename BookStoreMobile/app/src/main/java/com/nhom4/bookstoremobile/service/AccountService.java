package com.nhom4.bookstoremobile.service;

import com.nhom4.bookstoremobile.entities.Account;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AccountService {
    @FormUrlEncoded
    @POST("login")
    Call<Account> login(
            @Field("username") String userID,
            @Field("password") String userPassword
    );

    @GET("accounts/{username}")
    Call<Account> getAccount(@Path("username") String userID);

    @PUT("accounts/{username}")
    Call<Void> editAccount(@Path("username") String userID, @Body Account account);

    @POST("register")
    Call<String> register(@Body Account account);
}
