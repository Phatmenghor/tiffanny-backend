package com.emenu.features.auth.mapper;

import com.emenu.features.auth.dto.response.ActivityLogResponse;
import com.emenu.features.auth.models.ActivityLog;
import org.springframework.stereotype.Component;

@Component
public class ActivityLogMapper {

    public ActivityLogResponse toResponse(ActivityLog activityLog) {
        if (activityLog == null) {
            return null;
        }

        ActivityLogResponse response = new ActivityLogResponse();
        
        // Map base fields
        response.setId(activityLog.getId());
        response.setCreatedAt(activityLog.getCreatedAt());
        response.setUpdatedAt(activityLog.getUpdatedAt());
        response.setCreatedBy(activityLog.getCreatedBy());
        response.setUpdatedBy(activityLog.getUpdatedBy());
        
        // Map activity log fields
        response.setProfile(activityLog.getProfile());
        response.setDevice(activityLog.getDevice());
        response.setClientIp(activityLog.getClientIp());
        response.setPhysicalDevice(activityLog.getPhysicalDevice());
        response.setLocation(activityLog.getLocation());
        
        if (activityLog.getLatitude() != null && activityLog.getLongitude() != null) {
            response.setGoogleMapUrl("https://www.google.com/maps?q=" + 
                    activityLog.getLatitude() + "," + activityLog.getLongitude());
        }
        
        // Map user information
        if (activityLog.getUser() != null) {
            response.setUserId(activityLog.getUser().getId());
            response.setFirstName(activityLog.getUser().getFirstName());
            response.setLastName(activityLog.getUser().getLastName());
            response.setUsername(activityLog.getUser().getUserIdentifier());
        }
        
        return response;
    }
}
