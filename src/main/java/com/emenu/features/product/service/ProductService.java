package com.emenu.features.product.service;

import com.emenu.enums.common.Status;
import com.emenu.features.product.dto.request.ProductRequest;
import com.emenu.features.product.dto.response.ProductResponse;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);

    ProductResponse updateProduct(UUID id, ProductRequest request);

    void deleteProduct(UUID id);

    ProductResponse getProductById(UUID id);

    List<ProductResponse> getAllProducts();

    List<ProductResponse> getProductsByCategory(UUID categoryId);

    List<ProductResponse> getProductsBySubCategory(UUID subCategoryId);

    List<ProductResponse> getProductsByStatus(Status status);

    void incrementProductView(UUID id);
}
