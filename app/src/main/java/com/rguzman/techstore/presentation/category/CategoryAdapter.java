package com.rguzman.techstore.presentation.category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.rguzman.techstore.R;
import com.rguzman.techstore.domain.model.Category;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

  public interface OnListItemClickListener {
    void onListItemClick(Category category);
  }

  private OnListItemClickListener listener;
  private List<Category> list = new ArrayList<>();

  public CategoryAdapter(OnListItemClickListener listener) {
    this.listener = listener;
  }


  @NonNull
  @Override
  public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_category, parent, false);
    return new CategoryViewHolder(view, listener);
  }

  @Override
  public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
    holder.bind(list.get(position));
  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  public void setList(List<Category> list) {
    this.list = list;
    notifyDataSetChanged();
  }

  public void clearList() {
    this.list.clear();
    notifyDataSetChanged();
  }

  public static class CategoryViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.img_category)
    AppCompatImageView categoryImg;
    @BindView(R.id.txt_category_title)
    AppCompatTextView txtCategoryTitle;

    private OnListItemClickListener listener;

    public CategoryViewHolder(View view, OnListItemClickListener listener) {
      super(view);
      this.listener = listener;
      ButterKnife.bind(this, view);
    }

    public void bind(Category category) {
      Glide.with(itemView)
              .load(category.getImage())
              .into(categoryImg);

      txtCategoryTitle.setText(category.getName());
      itemView.setOnClickListener(v -> listener.onListItemClick(category));
    }
  }
}
