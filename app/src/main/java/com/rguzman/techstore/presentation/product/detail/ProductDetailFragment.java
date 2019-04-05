package com.rguzman.techstore.presentation.product.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rguzman.techstore.R;

import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

public class ProductDetailFragment extends DaggerFragment {

    public static final String ARGS_PRODUCT_ID = "com.rguzman.techstore.args.PRODUCT_ID";

    public static ProductDetailFragment newInstance(String productId) {
        Bundle args = new Bundle();
        args.putString(ARGS_PRODUCT_ID, productId);
        ProductDetailFragment fragment = new ProductDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
