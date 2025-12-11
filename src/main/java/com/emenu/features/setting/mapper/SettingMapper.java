package com.emenu.features.setting.mapper;

import com.emenu.features.setting.dto.AboutUsDto;
import com.emenu.features.setting.dto.AboutUsRequest;
import com.emenu.features.setting.models.AboutUs;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SettingMapper {

    AboutUsDto toDto(AboutUs aboutUs);

    void updateEntity(AboutUsRequest request, @MappingTarget AboutUs aboutUs);
}
