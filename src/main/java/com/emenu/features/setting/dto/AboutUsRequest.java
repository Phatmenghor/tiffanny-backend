package com.emenu.features.setting.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AboutUsRequest {

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotBlank(message = "Store phone is required")
    private String storePhone;

    @NotBlank(message = "Bank name is required")
    private String bankName;

    @NotBlank(message = "Bank number is required")
    private String bankNumber;

    @NotBlank(message = "Available time is required")
    private String availableTime;

    @NotBlank(message = "Showroom hours is required")
    private String showroomHours;

    @NotBlank(message = "Website URL is required")
    private String websiteUrl;

    private String telegramUrl;
    private String messengerUrl;
    private String facebookUrl;
    private String instagramUrl;
    private String twitterUrl;

    @NotBlank(message = "About Us Profile Image is required")
    private String aboutUsProfileImage;

    @NotBlank(message = "QR Code Image is required")
    private String qrCodeImage;

    @NotBlank(message = "Description is required")
    private String description;
}
