package com.nhom4.bookstoremobile.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nhom4.bookstoremobile.R;
import com.nhom4.bookstoremobile.activity.ViewBookDetails;
import com.nhom4.bookstoremobile.entities.Book;
import com.nhom4.bookstoremobile.entities.CartItem;
import com.nhom4.bookstoremobile.repositories.CartDAO;

import java.util.ArrayList;
import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {
    private final Context context;
    private final List<CartItem> itemList;
    private final RecyclerView mRecyclerView;
    private final List<Boolean> isChecked = new ArrayList<>();
    private final CartDAO cartDAO;
    private Button paymentBtn;
    private TextView totalPriceTxtView;
    private CheckBox totalCheckBox;

    public CartItemAdapter(Context context, List<CartItem> itemList, RecyclerView mRecyclerView) {
        this.context = context;
        this.itemList = itemList;
        this.mRecyclerView = mRecyclerView;
        this.cartDAO = CartDAO.getInstance((Activity) context);

        for (int i = 0; i < itemList.size(); i++) {
            isChecked.add(false);
        }
    }

    public List<Boolean> getIsChecked() {
        return isChecked;
    }

    public void setPaymentBtn(Button paymentBtn) {
        this.paymentBtn = paymentBtn;
    }

    public void setTotalPriceTxtView(TextView totalPriceTxtView) {
        this.totalPriceTxtView = totalPriceTxtView;
    }

    public void setTotalCheckBox(CheckBox totalCheckBox) {
        this.totalCheckBox = totalCheckBox;
    }

    @NonNull
    @Override
    public CartItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_cart_item_layout, parent, false);
        view.setOnClickListener(v -> {
            int itemPosition = mRecyclerView.getChildLayoutPosition(v);
            CartItem cartItem = itemList.get(itemPosition);
            Intent intent = new Intent(context, ViewBookDetails.class);
            intent.putExtra("book_id", cartItem.getBookID());
            context.startActivity(intent);
        });

        return new CartItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemAdapter.ViewHolder holder, int p) {
        int position = holder.getAdapterPosition();
        CartItem cartItem = itemList.get(position);
        Book book = cartItem.getBook();

        holder.bookId = cartItem.getBookID();

        int quantity = cartItem.getQuantity();
        holder.itemQuantity.setText(String.valueOf(quantity));

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

        boolean isCheck = isChecked.get(position);
        holder.checkBox.setChecked(isCheck);

        setListener(holder);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    private void setTotalCheckBox() {
        for (boolean check : isChecked) {
            if (!check) {
                totalCheckBox.setChecked(false);
                return;
            }
        }
        totalCheckBox.setChecked(true);
    }

    public void checkAllCartItem(boolean isCheckedAll) {
        for (int i = 0; i < itemList.size(); i++) {
            isChecked.set(i, isCheckedAll);
        }
        notifyDataSetChanged();
    }

    private void addItemToCart(String id, int quantity) {
        CartItem cartItem = cartDAO.getCartItem(id);

        if (cartItem != null) {
            cartDAO.updateQuantityItem(id, quantity);
        } else {
            cartDAO.addToCart(id, quantity);
        }
    }

    public void deleteCartItem() {
        for (int i = 0; i < itemList.size(); i++) {
            if (isChecked.get(i)) {
                CartItem cartItem = itemList.get(i);

                cartDAO.removeFromCart(cartItem.getBookID());

                isChecked.remove(i);
                itemList.remove(i);
                i--;
            }
        }
        getTotalQuantity();
        getTotalPrice();

        notifyDataSetChanged();
    }

    private void getTotalQuantity() {
        int totalQuantity = 0;

        for (int i = 0; i < itemList.size(); i++) {
            if (isChecked.get(i)) {
                totalQuantity += itemList.get(i).getQuantity();
            }
        }

        String text = "Thanh toán (" + totalQuantity + ")";
        paymentBtn.setText(text);
    }

    private void getTotalPrice() {
        int totalPrice = 0;

        for (int i = 0; i < itemList.size(); i++) {
            if (isChecked.get(i)) {
                Book book = itemList.get(i).getBook();
                if (book != null) {
                    String priceRaw = book.getBookPrice();
                    priceRaw = priceRaw.replace("₫", "");
                    priceRaw = priceRaw.replaceAll("\\s+", "");
                    priceRaw = priceRaw.replace(".", "");

                    int price = Integer.parseInt(priceRaw);
                    totalPrice += (itemList.get(i).getQuantity() * price);
                }
            }
        }

        String totalPriceString = String.format("%,d", totalPrice).replace(',', '.') + " ₫";
        totalPriceTxtView.setText(totalPriceString);
    }


    private void setListener(ViewHolder holder) {
        int position = holder.getAdapterPosition();
        CartItem cartItem = itemList.get(position);
        Book book = cartItem.getBook();

        EditText itemQuantity = holder.itemQuantity;
        if (book != null) {
            itemQuantity.addTextChangedListener(new TextWatcher() {
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
                    int stock = book.getBookStock();
                    if (quantity <= 0) {
                        Toast.makeText(context, "Số lượng tối thiểu là 1", Toast.LENGTH_SHORT).show();
                        cartItem.setQuantity(1);
                        addItemToCart(holder.bookId, 1);
                        itemQuantity.setText(String.valueOf(1));
                    } else if (stock < quantity) {
                        Toast.makeText(context, "Số lượng tồn kho không đủ", Toast.LENGTH_SHORT).show();
                        cartItem.setQuantity(stock);
                        addItemToCart(holder.bookId, stock);
                        itemQuantity.setText(String.valueOf(stock));
                    }
                    getTotalQuantity();
                    getTotalPrice();
                }
            });
        }

        holder.plusBtn.setOnClickListener(v -> {
            String quantityRaw = itemQuantity.getText().toString();
            int quantity = Integer.parseInt(quantityRaw) + 1;

            if (book.getBookStock() < quantity) {
                Toast.makeText(context, "Số lượng tồn kho không đủ", Toast.LENGTH_SHORT).show();
            } else {
                holder.itemQuantity.setText(String.valueOf(quantity));
                cartItem.setQuantity(quantity);
                addItemToCart(holder.bookId, quantity);
                getTotalQuantity();
                getTotalPrice();
            }
        });

        holder.minusBtn.setOnClickListener(v -> {
            String quantityRaw = itemQuantity.getText().toString();
            int quantity = Integer.parseInt(quantityRaw) - 1;

            if (quantity <= 0) {
                Toast.makeText(context, "Số lượng tối thiểu là 1", Toast.LENGTH_SHORT).show();
            } else {
                holder.itemQuantity.setText(String.valueOf(quantity));
                cartItem.setQuantity(quantity);
                addItemToCart(holder.bookId, quantity);
                getTotalQuantity();
                getTotalPrice();
            }
        });

        holder.checkBox.setOnCheckedChangeListener((buttonView, isCheck) -> {
            RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
            int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();

            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition >= firstVisibleItemPosition && adapterPosition <= lastVisibleItemPosition) {
                isChecked.set(position, isCheck);
                getTotalQuantity();
                getTotalPrice();
            }
            setTotalCheckBox();
        });
    }

    public boolean[] checkOut() {
        boolean[] booleanArray = new boolean[isChecked.size()];
        for (int i = 0; i < isChecked.size(); i++) {
            booleanArray[i] = isChecked.get(i);
        }

        return booleanArray;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        String bookId;
        ImageView imageView;
        TextView itemName;
        TextView itemAuthor;
        TextView itemPrice;
        EditText itemQuantity;
        ImageButton plusBtn;
        ImageButton minusBtn;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            itemName = itemView.findViewById(R.id.name_TxtView);
            itemAuthor = itemView.findViewById(R.id.author_TxtView);
            itemPrice = itemView.findViewById(R.id.price_TxtView);
            itemQuantity = itemView.findViewById(R.id.quantity_EditText);
            plusBtn = itemView.findViewById(R.id.plusBtn);
            minusBtn = itemView.findViewById(R.id.minusBtn);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}
