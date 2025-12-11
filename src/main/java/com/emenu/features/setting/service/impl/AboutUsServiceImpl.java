package com.emenu.features.setting.service.impl;

import com.emenu.features.setting.dto.AboutUsDto;
import com.emenu.features.setting.dto.AboutUsRequest;
import com.emenu.features.setting.mapper.SettingMapper;
import com.emenu.features.setting.models.AboutUs;
import com.emenu.features.setting.repository.AboutUsRepository;
import com.emenu.features.setting.service.AboutUsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AboutUsServiceImpl implements AboutUsService {

    private final AboutUsRepository aboutUsRepository;
    private final SettingMapper settingMapper;

    @Override
    @Transactional(readOnly = true)
    public AboutUsDto getAboutUs() {
        return aboutUsRepository.findAll().stream()
                .findFirst()
                .map(settingMapper::toDto)
                .orElse(null);
    }

    @Override
    @Transactional
    public AboutUsDto updateAboutUs(AboutUsRequest request) {
        AboutUs aboutUs = aboutUsRepository.findAll().stream()
                .findFirst()
                .orElse(new AboutUs());

        settingMapper.updateEntity(request, aboutUs);
        AboutUs savedAboutUs = aboutUsRepository.save(aboutUs);
        return settingMapper.toDto(savedAboutUs);
    }
}
