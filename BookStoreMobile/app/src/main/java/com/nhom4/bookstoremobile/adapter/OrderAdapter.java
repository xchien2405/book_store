package com.nhom4.bookstoremobile.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nhom4.bookstoremobile.R;
import com.nhom4.bookstoremobile.activity.ViewOrderDetails;
import com.nhom4.bookstoremobile.converter.Converter;
import com.nhom4.bookstoremobile.entities.Book;
import com.nhom4.bookstoremobile.entities.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private final Context mContext;
    private final List<Order> mOrderList;
    private final RecyclerView mRecyclerView;

    public OrderAdapter(Context context, List<Order> orderList, RecyclerView recyclerView) {
        mContext = context;
        mOrderList = orderList;
        mRecyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.main_order_layout, parent, false);
        view.setOnClickListener(v -> {
            int itemPosition = mRecyclerView.getChildLayoutPosition(v);
            Order order = mOrderList.get(itemPosition);
            Intent intent = new Intent(mContext, ViewOrderDetails.class);
            intent.putExtra("orderID", order.getOrderID());
            intent.putExtra("userID", order.getUserID());
            intent.putExtra("orderTime", order.getOrderTime().toString());
            intent.putExtra("orderPhone", order.getOrderPhone());
            intent.putExtra("orderAddress", order.getOrderAddress());
            intent.putExtra("orderStatus", order.getOrderStatus());
            intent.putExtra("orderPrice", order.getOrderPrice());
            intent.putExtra("orderItemQuantity", order.getOrderItemQuantity());

            mContext.startActivity(intent);
            ((Activity) mContext).overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = mOrderList.get(position);
        Book book = order.getOrderFirstBook();

        holder.orderID.setText(order.getOrderID());
        holder.orderStatus.setText(order.getOrderStatus());

        Glide.with(mContext)
                .load(book.getBookImage())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .fitCenter()
                .into(holder.imageViewFirstBook);

        holder.orderFirstBook.setText(book.getBookName());

        int priceInt = Converter.currencyToNumber(order.getOrderPrice());
        priceInt += 20000;
        String priceString = Converter.numberToCurrency(priceInt);

        holder.orderPrice.setText(priceString);
        String soSP = "";
        if (order.getOrderItemQuantity() != 0) {
            soSP = "Và " + order.getOrderItemQuantity() + " cuốn sách khác";
        }
        holder.orderOtherBook.setText(soSP);
    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView orderID;
        public TextView orderStatus;
        public ImageView imageViewFirstBook;
        public TextView orderFirstBook;
        public TextView orderPrice;
        public TextView orderOtherBook;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderID = itemView.findViewById(R.id.orderID);
            orderStatus = itemView.findViewById(R.id.orderStatus);
            imageViewFirstBook = itemView.findViewById(R.id.firstBookImage);
            orderFirstBook = itemView.findViewById(R.id.firstBookName);
            orderPrice = itemView.findViewById(R.id.orderPrice);
            orderOtherBook = itemView.findViewById(R.id.otherBook);
        }
    }
}
