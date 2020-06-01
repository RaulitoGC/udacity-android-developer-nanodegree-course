package com.rguzman.techstore.data.preferences;

import com.rguzman.techstore.domain.model.Product;

public interface ProductPrefs {

  String PRODUCT_NAME = "techstore.product.prefs.PRODUCT_NAME";
  String PRODUCT_ID = "techstore.product.prefs.PRODUCT_ID";
  String PRODUCT_IMAGE = "techstore.product.prefs.PRODUCT_IMAGE";
  String PRODUCT_PRICE = "techstore.product.prefs.PRODUCT_PRICE";
  String PRODUCT_DESCRIPTION = "techstore.product.prefs.PRODUCT_DESCRIPTION";
  String PRODUCT_CATEGORY_ID = "techstore.product.prefs.PRODUCT_CATEGORY_ID";

  void saveProduct(Product product);

  Product getProduct();
}
