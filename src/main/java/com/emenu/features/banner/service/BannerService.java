package com.emenu.features.banner.service;

import com.emenu.features.banner.dto.BannerDto;
import com.emenu.features.banner.dto.CreateBannerRequest;
import com.emenu.features.banner.dto.UpdateBannerRequest;
import java.util.List;
import java.util.UUID;

public interface BannerService {
    BannerDto createBanner(CreateBannerRequest request);
    BannerDto updateBanner(UUID id, UpdateBannerRequest request);
    void deleteBanner(UUID id);
    BannerDto getBannerById(UUID id);
    List<BannerDto> getAllBanners();
}
