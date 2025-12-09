package com.emenu.shared.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RequestInfoUtil {

    /**
     * Extract client IP address from HTTP request
     * Handles X-Forwarded-For header for proxied requests
     */
    public String getClientIp(HttpServletRequest request) {
        if (request == null) {
            return "Unknown";
        }

        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_FORWARDED");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_VIA");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // Handle multiple IPs in X-Forwarded-For (take the first one)
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }

        return ip != null ? ip : "Unknown";
    }

    /**
     * Extract device information from User-Agent header
     */
    public String getDeviceInfo(HttpServletRequest request) {
        if (request == null) {
            return "Unknown Device";
        }

        String userAgent = request.getHeader("User-Agent");
        return userAgent != null && !userAgent.isEmpty() ? userAgent : "Unknown Device";
    }

    /**
     * Parse physical device type from User-Agent
     * Returns: Mobile, Tablet, Desktop, Bot, or Unknown
     */
    public String getPhysicalDevice(HttpServletRequest request) {
        if (request == null) {
            return "Unknown";
        }

        String userAgent = request.getHeader("User-Agent");
        if (userAgent == null || userAgent.isEmpty()) {
            return "Unknown";
        }

        String lowerUA = userAgent.toLowerCase();

        // Check for bots/crawlers
        if (lowerUA.contains("bot") || lowerUA.contains("crawler") || lowerUA.contains("spider")) {
            return "Bot";
        }

        // Check for mobile devices
        if (lowerUA.contains("mobile") || lowerUA.contains("android") || 
            lowerUA.contains("iphone") || lowerUA.contains("ipod") ||
            lowerUA.contains("blackberry") || lowerUA.contains("windows phone")) {
            return "Mobile";
        }

        // Check for tablets
        if (lowerUA.contains("tablet") || lowerUA.contains("ipad")) {
            return "Tablet";
        }

        // Default to desktop
        if (lowerUA.contains("windows") || lowerUA.contains("macintosh") || 
            lowerUA.contains("linux") || lowerUA.contains("x11")) {
            return "Desktop";
        }

        return "Unknown";
    }

    /**
     * Get location from IP address
     * For now, returns "Unknown" - can be enhanced with IP geolocation service
     */
    public String getLocation(String ipAddress) {
        // TODO: Integrate with IP geolocation service (e.g., MaxMind, IP2Location)
        // For now, just return unknown
        return "Unknown";
    }

    public Double getLatitude(HttpServletRequest request) {
        String lat = request.getHeader("X-Latitude");
        if (lat != null && !lat.isEmpty()) {
            try {
                return Double.parseDouble(lat);
            } catch (NumberFormatException e) {
                log.warn("Invalid latitude header: {}", lat);
            }
        }
        return null;
    }

    public Double getLongitude(HttpServletRequest request) {
        String lon = request.getHeader("X-Longitude");
        if (lon != null && !lon.isEmpty()) {
            try {
                return Double.parseDouble(lon);
            } catch (NumberFormatException e) {
                log.warn("Invalid longitude header: {}", lon);
            }
        }
        return null;
    }
}
