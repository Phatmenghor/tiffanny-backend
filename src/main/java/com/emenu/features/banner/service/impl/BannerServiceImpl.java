package com.emenu.features.banner.service.impl;

import com.emenu.exception.custom.NotFoundException;
import com.emenu.features.banner.dto.BannerDto;
import com.emenu.features.banner.dto.CreateBannerRequest;
import com.emenu.features.banner.dto.UpdateBannerRequest;
import com.emenu.features.banner.mapper.BannerMapper;
import com.emenu.features.banner.models.Banner;
import com.emenu.features.banner.repository.BannerRepository;
import com.emenu.features.banner.service.BannerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepository;
    private final BannerMapper bannerMapper;

    @Override
    @Transactional
    public BannerDto createBanner(CreateBannerRequest request) {
        log.info("Creating new banner: {}", request.getName());
        Banner banner = bannerMapper.toEntity(request);
        Banner savedBanner = bannerRepository.save(banner);
        return bannerMapper.toDto(savedBanner);
    }

    @Override
    @Transactional
    public BannerDto updateBanner(UUID id, UpdateBannerRequest request) {
        log.info("Updating banner: {}", id);
        Banner banner = bannerRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Banner not found with ID: " + id));

        bannerMapper.updateEntity(request, banner);
        Banner updatedBanner = bannerRepository.save(banner);
        return bannerMapper.toDto(updatedBanner);
    }

    @Override
    @Transactional
    public void deleteBanner(UUID id) {
        log.info("Deleting banner: {}", id);
        Banner banner = bannerRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Banner not found with ID: " + id));

        banner.softDelete();
        bannerRepository.save(banner);
    }

    @Override
    @Transactional(readOnly = true)
    public BannerDto getBannerById(UUID id) {
        log.info("Fetching banner: {}", id);
        Banner banner = bannerRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Banner not found with ID: " + id));
        return bannerMapper.toDto(banner);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BannerDto> getAllBanners() {
        log.info("Fetching all banners");
        return bannerRepository.findAll(Sort.by(Sort.Direction.ASC, "displayOrder")).stream()
                .filter(banner -> !banner.getIsDeleted())
                .map(bannerMapper::toDto)
                .collect(Collectors.toList());
    }
}
