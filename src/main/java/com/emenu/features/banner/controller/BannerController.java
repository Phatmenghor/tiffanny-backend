package com.emenu.features.banner.controller;

import com.emenu.features.banner.dto.BannerDto;
import com.emenu.features.banner.dto.CreateBannerRequest;
import com.emenu.features.banner.dto.UpdateBannerRequest;
import com.emenu.features.banner.service.BannerService;
import com.emenu.shared.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/banners")
@RequiredArgsConstructor
@Slf4j
public class BannerController {

    private final BannerService bannerService;

    @PostMapping
    public ResponseEntity<ApiResponse<BannerDto>> createBanner(@Valid @RequestBody CreateBannerRequest request) {
        BannerDto response = bannerService.createBanner(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Banner created successfully", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BannerDto>> updateBanner(@PathVariable UUID id, @Valid @RequestBody UpdateBannerRequest request) {
        BannerDto response = bannerService.updateBanner(id, request);
        return ResponseEntity.ok(ApiResponse.success("Banner updated successfully", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBanner(@PathVariable UUID id) {
        bannerService.deleteBanner(id);
        return ResponseEntity.ok(ApiResponse.success("Banner deleted successfully", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BannerDto>> getBannerById(@PathVariable UUID id) {
        BannerDto response = bannerService.getBannerById(id);
        return ResponseEntity.ok(ApiResponse.success("Banner retrieved successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BannerDto>>> getAllBanners() {
        List<BannerDto> response = bannerService.getAllBanners();
        return ResponseEntity.ok(ApiResponse.success("Banners retrieved successfully", response));
    }
}
