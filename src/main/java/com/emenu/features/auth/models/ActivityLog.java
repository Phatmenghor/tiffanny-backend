package com.emenu.features.auth.models;

import com.emenu.shared.domain.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "activity_logs", indexes = {
        @Index(name = "idx_activity_log_user_id", columnList = "user_id"),
        @Index(name = "idx_activity_log_deleted", columnList = "is_deleted"),
        @Index(name = "idx_activity_log_created_at", columnList = "created_at"),
        @Index(name = "idx_activity_log_client_ip", columnList = "client_ip")
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ActivityLog extends BaseUUIDEntity {

    @Column(name = "profile")
    private String profile;

    @Column(name = "device", length = 500)
    private String device;

    @Column(name = "client_ip", length = 45)
    private String clientIp;

    @Column(name = "physical_device", length = 500)
    private String physicalDevice;

    @Column(name = "location")
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
