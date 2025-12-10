package com.emenu.features.product.service.impl;

import com.emenu.enums.common.Status;
import com.emenu.exception.custom.NotFoundException;
import com.emenu.features.product.dto.request.AllProductPromotionRequest;
import com.emenu.features.product.dto.request.CreateProductPromotionRequest;
import com.emenu.features.product.dto.request.UpdateProductPromotionRequest;
import com.emenu.features.product.dto.response.AllProductPromotionResponseDto;
import com.emenu.features.product.dto.response.ProductPromotionDto;
import com.emenu.features.product.mapper.ProductPromotionMapper;
import com.emenu.features.product.models.Product;
import com.emenu.features.product.models.ProductPromotion;
import com.emenu.features.product.repository.ProductPromotionRepository;
import com.emenu.features.product.repository.ProductRepository;
import com.emenu.features.product.service.ProductPromotionService;
import com.emenu.features.product.specification.ProductPromotionSpecification;
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
public class ProductPromotionServiceImpl implements ProductPromotionService {

    private final ProductPromotionRepository promotionRepository;
    private final ProductRepository productRepository;
    private final ProductPromotionMapper promotionMapper;

    @Override
    @Transactional
    public ProductPromotionDto createPromotion(CreateProductPromotionRequest request) {
        log.info("Creating new promotion: {}", request.getName());
        
        Product product = productRepository.findByIdAndIsDeletedFalse(request.getProductId())
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + request.getProductId()));
        
        ProductPromotion promotion = promotionMapper.toEntity(request);
        promotion.setProduct(product);
        
        ProductPromotion savedPromotion = promotionRepository.save(promotion);
        
        log.info("Promotion created with ID: {}", savedPromotion.getId());
        return promotionMapper.toDto(savedPromotion);
    }

    @Override
    @Transactional
    public ProductPromotionDto updatePromotion(UUID id, UpdateProductPromotionRequest request) {
        log.info("Updating promotion with ID: {}", id);
        
        ProductPromotion promotion = promotionRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Promotion not found with ID: " + id));
        
        // Update product if changed
        if (request.getProductId() != null && !promotion.getProduct().getId().equals(request.getProductId())) {
            Product product = productRepository.findByIdAndIsDeletedFalse(request.getProductId())
                    .orElseThrow(() -> new NotFoundException("Product not found with ID: " + request.getProductId()));
            promotion.setProduct(product);
        }
        
        promotionMapper.updateEntity(request, promotion);
        ProductPromotion updatedPromotion = promotionRepository.save(promotion);
        
        log.info("Promotion updated: {}", id);
        return promotionMapper.toDto(updatedPromotion);
    }

    @Override
    @Transactional
    public void deletePromotion(UUID id) {
        log.info("Deleting promotion with ID: {}", id);
        
        ProductPromotion promotion = promotionRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Promotion not found with ID: " + id));
        
        promotion.softDelete();
        promotionRepository.save(promotion);
        
        log.info("Promotion soft deleted: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductPromotionDto getPromotionById(UUID id) {
        log.info("Fetching promotion with ID: {}", id);
        
        ProductPromotion promotion = promotionRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Promotion not found with ID: " + id));
        
        return promotionMapper.toDto(promotion);
    }

    @Override
    @Transactional(readOnly = true)
    public AllProductPromotionResponseDto getAllPromotions(AllProductPromotionRequest request) {
        log.info("Fetching all promotions with filters");
        Pageable pageable = PageRequest.of(request.getPageNo() - 1, request.getPageSize(), Sort.by(Sort.Direction.DESC, "createdAt"));

        var spec = ProductPromotionSpecification.filterPromotions(
                request.getSearch(),
                request.getStatus(),
                request.getProductId(),
                null,  // createdFrom
                null   // createdTo
        );

        Page<ProductPromotion> page = promotionRepository.findAll(spec, pageable);

        List<ProductPromotionDto> content = page.stream()
                .map(promotionMapper::toDto)
                .toList();

        return promotionMapper.mapToListDto(content, page);
    }
}
