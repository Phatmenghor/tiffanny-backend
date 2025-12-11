package com.emenu.features.setting.models;

import com.emenu.shared.domain.BaseUUIDEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "about_us")
public class AboutUs extends BaseUUIDEntity {

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "store_phone", nullable = false)
    private String storePhone;

    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @Column(name = "bank_number", nullable = false)
    private String bankNumber;

    @Column(name = "available_time", nullable = false)
    private String availableTime;

    @Column(name = "showroom_hours", nullable = false)
    private String showroomHours;

    @Column(name = "website_url", nullable = false)
    private String websiteUrl;

    @Column(name = "telegram_url")
    private String telegramUrl;

    @Column(name = "messenger_url")
    private String messengerUrl;

    @Column(name = "facebook_url")
    private String facebookUrl;

    @Column(name = "instagram_url")
    private String instagramUrl;

    @Column(name = "twitter_url")
    private String twitterUrl;

    @Column(name = "about_us_profile_image")
    private String aboutUsProfileImage;

    @Column(name = "qr_code_image")
    private String qrCodeImage;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;
}
