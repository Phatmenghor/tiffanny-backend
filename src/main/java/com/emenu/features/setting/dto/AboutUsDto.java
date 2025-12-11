package com.emenu.features.setting.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class AboutUsDto {
    private UUID id;
    private String email;
    private String location;
    private String phoneNumber;
    private String storePhone;
    private String bankName;
    private String bankNumber;
    private String availableTime;
    private String showroomHours;
    private String websiteUrl;
    private String telegramUrl;
    private String messengerUrl;
    private String facebookUrl;
    private String instagramUrl;
    private String twitterUrl;
    private String aboutUsProfileImage;
    private String qrCodeImage;
    private String description;
}
