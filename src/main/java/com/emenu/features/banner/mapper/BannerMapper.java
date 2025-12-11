package com.emenu.features.banner.mapper;

import com.emenu.features.banner.dto.BannerDto;
import com.emenu.features.banner.dto.CreateBannerRequest;
import com.emenu.features.banner.dto.UpdateBannerRequest;
import com.emenu.features.banner.models.Banner;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BannerMapper {

    BannerDto toDto(Banner banner);

    Banner toEntity(CreateBannerRequest request);

    void updateEntity(UpdateBannerRequest request, @MappingTarget Banner banner);
}
