package com.example.shopping_list_app;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ShoppingItemViewHolder  extends RecyclerView.ViewHolder {
    TextView tvItemName, tvQuantity, tvPrice;
    ImageButton btnDelete;

    public ShoppingItemViewHolder(View itemView) {
        super(itemView);
        tvItemName = itemView.findViewById(R.id.tvItemName);
        tvQuantity = itemView.findViewById(R.id.tvQuantity);
        tvPrice = itemView.findViewById(R.id.tvPrice);
        btnDelete = itemView.findViewById(R.id.btnDelete);
    }
}
