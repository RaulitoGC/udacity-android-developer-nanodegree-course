package com.rguzman.techstore.presentation.product;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rguzman.techstore.R;
import com.rguzman.techstore.domain.model.Product;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    public interface OnListItemClickListener {
        void onListItemClick(Product product, AppCompatImageView productImage);
    }

    private OnListItemClickListener listener;
    private List<Product> list = new ArrayList<>();

    public ProductAdapter(OnListItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_product, parent, false);
        return new ProductViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Product> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void clearList() {
        this.list.clear();
        notifyDataSetChanged();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_product)
        AppCompatImageView productImg;
        @BindView(R.id.txt_product_title)
        AppCompatTextView txtProductTitle;
        @BindView(R.id.txt_product_price)
        AppCompatTextView txtProductPrice;

        private OnListItemClickListener listener;

        public ProductViewHolder(View view, OnListItemClickListener listener) {
            super(view);
            this.listener = listener;
            ButterKnife.bind(this, view);
        }

        public void bind(Product product) {
            Glide.with(productImg.getContext())
                    .load(product.getImage())
                    .placeholder(R.drawable.placeholder)
                    .centerCrop()
                    .into(productImg);

            txtProductTitle.setText(product.getName());
            String price = String.format(itemView.getResources().getString(R.string.text_product_price_format), product.getPrice());

            txtProductPrice.setText(price);
            itemView.setOnClickListener(v -> listener.onListItemClick(product, productImg));
        }
    }
}
