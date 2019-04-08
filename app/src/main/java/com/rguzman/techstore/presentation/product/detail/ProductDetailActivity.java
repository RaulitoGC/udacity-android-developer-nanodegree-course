package com.rguzman.techstore.presentation.product.detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.rguzman.techstore.R;
import com.rguzman.techstore.domain.model.Feature;
import com.rguzman.techstore.domain.model.Product;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class ProductDetailActivity extends DaggerAppCompatActivity implements ProductDetailView {

    public static final String EXTRA_PRODUCT_ID = "com.rguzman.techstore.extra.PRODUCT_ID";

    @BindView(R.id.product_image)
    AppCompatImageView productImg;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private ProductDetailViewModel productDetailViewModel;

    public static Intent getCallingIntent(Context context, String productId) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID, productId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);

        supportPostponeEnterTransition();

        if (getIntent() != null && getIntent().hasExtra(EXTRA_PRODUCT_ID)) {
            String productId = getIntent().getStringExtra(EXTRA_PRODUCT_ID);

            this.productDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(ProductDetailViewModel.class);
            this.productDetailViewModel.setView(this);
            this.productDetailViewModel.init(productId);
        }
    }

    @Override
    public void loadProduct(Product product) {
        supportPostponeEnterTransition();

        Glide.with(this)
                .load(product.getImage())
                .centerCrop()
                .dontAnimate()
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        supportStartPostponedEnterTransition();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        supportStartPostponedEnterTransition();
                        return false;
                    }
                })
                .into(productImg);
    }

    @Override
    public void loadFeatures(List<Feature> features) {

    }

    @Override
    public Context context() {
        return this;
    }

    @Override
    public void showError(String message) {
        Toast.makeText(context(), message, Toast.LENGTH_LONG).show();
    }
}
