package com.rguzman.techstore.presentation.product.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.rguzman.techstore.R;
import com.rguzman.techstore.domain.model.Feature;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class FeatureAdapter extends RecyclerView.Adapter<FeatureAdapter.FeatureViewHolder> {

    private List<Feature> list = new ArrayList<>();

    @NonNull
    @Override
    public FeatureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_feature, parent, false);
        return new FeatureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeatureViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Feature> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void clearList() {
        this.list.clear();
        notifyDataSetChanged();
    }

    public static class FeatureViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_product_detail_label)
        AppCompatTextView txtProductDetailLabel;
        @BindView(R.id.txt_product_detail_name)
        AppCompatTextView txtProductDetailName;


        public FeatureViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(Feature feature) {
            String featureLabel = String.format(itemView.getResources().getString(R.string.text_feature_label_format), feature.getLabel());
            txtProductDetailLabel.setText(featureLabel);
            txtProductDetailName.setText(feature.getName());
        }
    }
}
