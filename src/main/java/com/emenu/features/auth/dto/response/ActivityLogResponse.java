package com.emenu.features.auth.dto.response;

import com.emenu.shared.dto.BaseAuditResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class ActivityLogResponse extends BaseAuditResponse {
    
    private String profile;
    private String device;
    private String clientIp;
    private String physicalDevice;
    private String location;
    
    // User information
    private UUID userId;
    private String firstName;
    private String lastName;
    private String username;
}
