package com.rguzman.techstore.presentation.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rguzman.techstore.R;

import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

public class ProductListFragment extends DaggerFragment {

    public static final String ARGS_CATEGORY_ID = "com.rguzman.techstore.args.CATEGORY_ID";

    public static ProductListFragment newInstance(String categoryId) {
        Bundle args = new Bundle();
        args.putString(ARGS_CATEGORY_ID, categoryId);
        ProductListFragment fragment = new ProductListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
