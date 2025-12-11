package com.emenu.features.setting.controller;

import com.emenu.features.setting.dto.AboutUsDto;
import com.emenu.features.setting.dto.AboutUsRequest;
import com.emenu.features.setting.service.AboutUsService;
import com.emenu.shared.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/about-us")
@RequiredArgsConstructor
@Slf4j
public class AboutUsController {

    private final AboutUsService aboutUsService;

    @GetMapping
    public ResponseEntity<ApiResponse<AboutUsDto>> getAboutUs() {
        log.info("Fetching about us settings");
        AboutUsDto response = aboutUsService.getAboutUs();
        return ResponseEntity.ok(ApiResponse.success("About Us retrieved successfully", response));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<AboutUsDto>> updateAboutUs(@Valid @RequestBody AboutUsRequest request) {
        log.info("Updating about us settings");
        AboutUsDto response = aboutUsService.updateAboutUs(request);
        return ResponseEntity.ok(ApiResponse.success("About Us updated successfully", response));
    }
}
