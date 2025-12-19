package com.emenu.features.product.service.impl;

import com.emenu.exception.custom.NotFoundException;
import com.emenu.features.product.dto.request.AllProductRequest;
import com.emenu.features.product.dto.request.CreateProductRequest;
import com.emenu.features.product.dto.request.UpdateProductRequest;
import com.emenu.features.product.dto.response.AllProductResponseDto;
import com.emenu.features.product.dto.response.ProductDto;
import com.emenu.features.product.mapper.ProductMapper;
import com.emenu.features.product.models.Product;
import com.emenu.features.product.repository.CategoryRepository;
import com.emenu.features.product.repository.ProductRepository;
import com.emenu.features.product.repository.SubCategoryRepository;
import com.emenu.features.product.service.ProductService;
import com.emenu.features.product.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public ProductDto createProduct(CreateProductRequest request) {
        log.info("Creating new product: {}", request.getName());
        
        Product product = productMapper.toEntity(request);
        Product savedProduct = productRepository.save(product);

        if (request.getSuggestionIds() != null && !request.getSuggestionIds().isEmpty()) {
            List<Product> suggestedProducts = productRepository.findAllById(request.getSuggestionIds());
            suggestedProducts.forEach(suggested -> {
                com.emenu.features.product.models.ProductSuggestion suggestion = new com.emenu.features.product.models.ProductSuggestion();
                suggestion.setProduct(savedProduct);
                suggestion.setSuggestedProduct(suggested);
                savedProduct.getSuggestions().add(suggestion);
            });
            productRepository.save(savedProduct);
        }
        
        log.info("Product created with ID: {}", savedProduct.getId());
        return productMapper.toDto(savedProduct);
    }

    @Override
    @Transactional
    public ProductDto updateProduct(UUID id, UpdateProductRequest request) {
        log.info("Updating product with ID: {}", id);
        
        Product product = productRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + id));
        
        productMapper.updateEntity(request, product);
        
        if (request.getSuggestionIds() != null) {
            product.getSuggestions().clear();
            if (!request.getSuggestionIds().isEmpty()) {
                List<Product> suggestedProducts = productRepository.findAllById(request.getSuggestionIds());
                suggestedProducts.forEach(suggested -> {
                    com.emenu.features.product.models.ProductSuggestion suggestion = new com.emenu.features.product.models.ProductSuggestion();
                    suggestion.setProduct(product);
                    suggestion.setSuggestedProduct(suggested);
                    product.getSuggestions().add(suggestion);
                });
            }
        }
        
        Product updatedProduct = productRepository.save(product);
        
        log.info("Product updated: {}", id);
        return productMapper.toDto(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(UUID id) {
        log.info("Deleting product with ID: {}", id);
        
        Product product = productRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + id));
        
        product.softDelete();
        productRepository.save(product);
        
        log.info("Product soft deleted: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto getProductById(UUID id) {
        log.info("Fetching product: {}", id);
        
        Product product = productRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + id));
        
        return productMapper.toDto(product);
    }

    @Override
    @Transactional(readOnly = true)
    public AllProductResponseDto getAllProducts(AllProductRequest request) {
        log.info("Fetching all products with filters");
        Pageable pageable = PageRequest.of(request.getPageNo() - 1, request.getPageSize(), Sort.by(Sort.Direction.DESC, "createdAt"));

        var spec = ProductSpecification.filterProducts(
                request.getSearch(),
                request.getStatus(),
                request.getCategoryId(),
                request.getSubCategoryId(),
                null,  // minPrice
                null,  // maxPrice
                null,  // inStock
                null,  // minViews
                null,  // maxViews
                null,  // createdFrom
                null,  // createdTo
                request.getHasDiscount() // hasDiscount
        );

        Page<Product> page = productRepository.findAll(spec, pageable);

        List<ProductDto> content = page.stream()
                .map(productMapper::toDto)
                .toList();

        return productMapper.mapToListDto(content, page);
    }

    @Override   
    @Transactional
    public void incrementProductView(UUID id) {
        log.info("Incrementing product view: {}", id);
        
        Product product = productRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + id));
        
        product.setProductView(product.getProductView() + 1);
        productRepository.save(product);
        
        log.info("Product view incremented for: {}", id);
    }
}
