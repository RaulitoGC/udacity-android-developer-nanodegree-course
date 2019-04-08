package com.rguzman.techstore.data.preferences;

import android.content.SharedPreferences;

import com.rguzman.techstore.domain.model.Product;

import javax.inject.Inject;

public class ProductPrefsImpl implements ProductPrefs {

  private final SharedPreferences preferences;

  @Inject
  public ProductPrefsImpl(SharedPreferences preferences) {
    this.preferences = preferences;
  }

  @Override
  public void saveProduct(Product product) {
    SharedPreferences.Editor editor = preferences.edit();
    editor.putString(PRODUCT_NAME, product.getName());
    editor.putString(PRODUCT_ID, product.getProductId());
    editor.putString(PRODUCT_IMAGE, product.getImage());
    editor.putString(PRODUCT_PRICE, product.getPrice());
    editor.putString(PRODUCT_DESCRIPTION, product.getDescription());
    editor.putString(PRODUCT_CATEGORY_ID, product.getCategoryId());
    editor.apply();
  }

  @Override
  public Product getProduct() {
    Product product = new Product();
    String productName = preferences.getString(PRODUCT_NAME, "");
    String productId = preferences.getString(PRODUCT_ID, "");
    String productImage = preferences.getString(PRODUCT_IMAGE, "");
    String productPrice = preferences.getString(PRODUCT_PRICE, "");
    String productDescription = preferences.getString(PRODUCT_DESCRIPTION, "");
    String productCategoryId = preferences.getString(PRODUCT_CATEGORY_ID, "");
    product.setName(productName);
    product.setProductId(productId);
    product.setImage(productImage);
    product.setPrice(productPrice);
    product.setDescription(productDescription);
    product.setCategoryId(productCategoryId);
    return product;
  }
}
