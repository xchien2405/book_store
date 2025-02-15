package com.nhom4.bookstoremobile.adapter;

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
import com.nhom4.bookstoremobile.activity.ViewBookDetails;
import com.nhom4.bookstoremobile.controller.CheckOutController;
import com.nhom4.bookstoremobile.entities.Book;
import com.nhom4.bookstoremobile.entities.CartItem;

import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder> {

    private final Context context;
    private final List<CartItem> orderItemList;
    private final CheckOutController controller;
    private RecyclerView mRecyclerView;


    public OrderItemAdapter(Context context, List<CartItem> orderItemList, CheckOutController controller) {
        this.context = context;
        this.orderItemList = orderItemList;
        this.controller = controller;
    }

    public void setmRecyclerView(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_order_item_layout, parent, false);
        if (mRecyclerView != null) {
            view.setOnClickListener(v -> {
                int itemPosition = mRecyclerView.getChildLayoutPosition(v);
                CartItem cartItem = orderItemList.get(itemPosition);
                Intent intent = new Intent(context, ViewBookDetails.class);
                intent.putExtra("book_id", cartItem.getBookID());
                context.startActivity(intent);
            });
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem orderItem = orderItemList.get(position);
        Book book = orderItem.getBook();
        if (book != null) {
            holder.itemName.setText(book.getBookName());
            holder.itemAuthor.setText(book.getBookAuthor());
            holder.itemPrice.setText(book.getBookPrice());

            Glide.with(context)
                    .load(book.getBookImage())
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .fitCenter()
                    .into(holder.imageView);
        }
        holder.itemQuantity.setText(String.valueOf(orderItem.getQuantity()));

        if (controller != null) {
            setTotalQuantity(orderItemList);
            setPrice(orderItemList);
        }
    }

    private void setTotalQuantity(List<CartItem> itemList) {
        int totalQuantity = 0;

        for (CartItem item : itemList) {
            totalQuantity += item.getQuantity();
        }

        String text = "(" + totalQuantity + " sản phẩm):";

        TextView totalProduct = controller.getActivity().findViewById(R.id.totalProduct);
        totalProduct.setText(text);
    }

    private int getProductPrice(List<CartItem> itemList) {
        int productPrice = 0;

        for (CartItem item : itemList) {
            Book book = item.getBook();

            if (book != null) {
                String priceRaw = book.getBookPrice();
                priceRaw = priceRaw.replace("₫", "");
                priceRaw = priceRaw.replaceAll("\\s+", "");
                priceRaw = priceRaw.replace(".", "");

                int price = Integer.parseInt(priceRaw);
                productPrice += (item.getQuantity() * price);
            }
        }
        return productPrice;
    }

    private void setPrice(List<CartItem> itemList) {
        int productPrice = getProductPrice(itemList);
        String productPriceString = String.format("%,d", productPrice).replace(',', '.') + " ₫";
        TextView productPriceTxtView = controller.getActivity().findViewById(R.id.productPrice);
        productPriceTxtView.setText(productPriceString);

        int totalPrice = productPrice + 20000;
        String totalPriceString = String.format("%,d", totalPrice).replace(',', '.') + " ₫";
        TextView totalPriceTxtView = controller.getActivity().findViewById(R.id.totalPrice);
        totalPriceTxtView.setText(totalPriceString);
    }

    @Override
    public int getItemCount() {
        return orderItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView itemName, itemAuthor, itemPrice, itemQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            itemName = itemView.findViewById(R.id.itemName);
            itemAuthor = itemView.findViewById(R.id.itemAuthor);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);
        }
    }
}
