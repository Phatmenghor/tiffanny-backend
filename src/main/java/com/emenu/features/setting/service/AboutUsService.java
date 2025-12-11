package com.emenu.features.setting.service;

import com.emenu.features.setting.dto.AboutUsDto;
import com.emenu.features.setting.dto.AboutUsRequest;

public interface AboutUsService {
    AboutUsDto getAboutUs();
    AboutUsDto updateAboutUs(AboutUsRequest request);
}
