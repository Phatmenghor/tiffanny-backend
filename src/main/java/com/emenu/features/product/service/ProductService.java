package com.emenu.features.product.service;

import com.emenu.enums.common.Status;
import com.emenu.features.product.dto.request.AllProductRequest;
import com.emenu.features.product.dto.request.CreateProductRequest;
import com.emenu.features.product.dto.request.UpdateProductRequest;
import com.emenu.features.product.dto.response.AllProductResponseDto;
import com.emenu.features.product.dto.response.ProductDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductDto createProduct(CreateProductRequest request);

    ProductDto updateProduct(UUID id, UpdateProductRequest request);

    void deleteProduct(UUID id);

    ProductDto getProductById(UUID id);

    AllProductResponseDto getAllProducts(AllProductRequest request);

    void incrementProductView(UUID id);
}
