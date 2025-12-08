package com.emenu.features.product.service;

import com.emenu.enums.common.Status;
import com.emenu.exception.custom.NotFoundException;
import com.emenu.features.product.dto.request.ProductPromotionRequest;
import com.emenu.features.product.dto.response.ProductPromotionResponse;
import com.emenu.features.product.mapper.ProductPromotionMapper;
import com.emenu.features.product.models.Product;
import com.emenu.features.product.models.ProductPromotion;
import com.emenu.features.product.repository.ProductPromotionRepository;
import com.emenu.features.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductPromotionServiceImpl implements ProductPromotionService {

    private final ProductPromotionRepository promotionRepository;
    private final ProductRepository productRepository;
    private final ProductPromotionMapper promotionMapper;

    @Override
    @Transactional
    public ProductPromotionResponse createPromotion(ProductPromotionRequest request) {
        log.info("Creating new promotion: {}", request.getName());
        
        Product product = productRepository.findByIdAndNotDeleted(request.getProductId())
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + request.getProductId()));
        
        ProductPromotion promotion = promotionMapper.toEntity(request);
        promotion.setProduct(product);
        
        ProductPromotion savedPromotion = promotionRepository.save(promotion);
        
        log.info("Promotion created with ID: {}", savedPromotion.getId());
        return promotionMapper.toResponse(savedPromotion);
    }

    @Override
    @Transactional
    public ProductPromotionResponse updatePromotion(UUID id, ProductPromotionRequest request) {
        log.info("Updating promotion with ID: {}", id);
        
        ProductPromotion promotion = promotionRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new NotFoundException("Promotion not found with ID: " + id));
        
        // Update product if changed
        if (!promotion.getProduct().getId().equals(request.getProductId())) {
            Product product = productRepository.findByIdAndNotDeleted(request.getProductId())
                    .orElseThrow(() -> new NotFoundException("Product not found with ID: " + request.getProductId()));
            promotion.setProduct(product);
        }
        
        promotionMapper.updateEntity(request, promotion);
        ProductPromotion updatedPromotion = promotionRepository.save(promotion);
        
        log.info("Promotion updated: {}", id);
        return promotionMapper.toResponse(updatedPromotion);
    }

    @Override
    @Transactional
    public void deletePromotion(UUID id) {
        log.info("Deleting promotion with ID: {}", id);
        
        ProductPromotion promotion = promotionRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new NotFoundException("Promotion not found with ID: " + id));
        
        promotion.softDelete();
        promotionRepository.save(promotion);
        
        log.info("Promotion soft deleted: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductPromotionResponse getPromotionById(UUID id) {
        log.info("Fetching promotion with ID: {}", id);
        
        ProductPromotion promotion = promotionRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new NotFoundException("Promotion not found with ID: " + id));
        
        return promotionMapper.toResponse(promotion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductPromotionResponse> getAllPromotions() {
        log.info("Fetching all active promotions");
        
        return promotionRepository.findAllActive().stream()
                .map(promotionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductPromotionResponse getPromotionByProduct(UUID productId) {
        log.info("Fetching promotion for product ID: {}", productId);
        
        return promotionRepository.findByProductId(productId)
                .map(promotionMapper::toResponse)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductPromotionResponse> getPromotionsByStatus(Status status) {
        log.info("Fetching promotions with status: {}", status);
        
        return promotionRepository.findByStatus(status).stream()
                .map(promotionMapper::toResponse)
                .collect(Collectors.toList());
    }
}
