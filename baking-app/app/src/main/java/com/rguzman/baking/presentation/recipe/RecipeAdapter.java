package com.rguzman.baking.presentation.recipe;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rguzman.baking.R;
import com.rguzman.baking.domain.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    public interface ListItemClickListener {
        void onListItemClick(Recipe recipe);
    }

    private ListItemClickListener listener;
    private List<Recipe> list = new ArrayList<>();

    public RecipeAdapter(ListItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_item_recipe;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int position) {
        recipeViewHolder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Recipe> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void clearList() {
        this.list.clear();
        notifyDataSetChanged();
    }


    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipeImg)
        ImageView recipeImageView;
        @BindView(R.id.txtRecipeName)
        TextView txtRecipeName;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final Recipe recipe) {
            Glide.with(itemView)
                    .load(recipe.getImage())
                    .into(recipeImageView);

            txtRecipeName.setText(recipe.getName());

            itemView.setOnClickListener(v -> listener.onListItemClick(recipe));
        }
    }
}
