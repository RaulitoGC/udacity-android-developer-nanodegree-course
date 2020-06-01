package com.rguzman.baking.presentation.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rguzman.baking.R;
import com.rguzman.baking.domain.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private List<Ingredient> list = new ArrayList<>();

    public IngredientAdapter() {
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_item_ingredient;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder ingredientViewHolder, int position) {
        ingredientViewHolder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void setList(List<Ingredient> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void clearList() {
        this.list.clear();
        notifyDataSetChanged();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_ingredient_name)
        TextView txtIngredientName;
        @BindView(R.id.txt_ingredient_measure)
        TextView txtIngredientMeasure;
        @BindView(R.id.txt_ingredient_quantity)
        TextView txtIngredientQuantity;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final Ingredient ingredient) {
            txtIngredientMeasure.setText(itemView.getResources().getString(R.string.text_ingredient_measure_label, ingredient.getMeasure()));
            txtIngredientName.setText(itemView.getResources().getString(R.string.text_ingredient_name_label, ingredient.getIngredient()));
            txtIngredientQuantity.setText(itemView.getResources().getString(R.string.text_ingredient_quantity_label, String.valueOf(ingredient.getQuantity())));
        }
    }
}
