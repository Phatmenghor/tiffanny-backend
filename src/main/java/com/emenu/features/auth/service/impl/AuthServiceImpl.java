package com.emenu.features.auth.service.impl;

import com.emenu.enums.user.RoleEnum;
import com.emenu.exception.custom.ValidationException;
import com.emenu.features.auth.dto.request.AdminPasswordResetRequest;
import com.emenu.features.auth.dto.request.LoginRequest;
import com.emenu.features.auth.dto.request.PasswordChangeRequest;
import com.emenu.features.auth.dto.response.LoginResponse;
import com.emenu.features.auth.dto.response.UserResponse;
import com.emenu.features.auth.mapper.UserMapper;
import com.emenu.features.auth.models.ActivityLog;
import com.emenu.features.auth.models.Role;
import com.emenu.features.auth.models.User;
import com.emenu.features.auth.repository.ActivityLogRepository;
import com.emenu.features.auth.repository.RoleRepository;
import com.emenu.features.auth.repository.UserRepository;
import com.emenu.features.auth.service.AuthService;
import com.emenu.security.SecurityUtils;
import com.emenu.security.jwt.JWTGenerator;
import com.emenu.security.jwt.TokenBlacklistService;
import com.emenu.shared.utils.RequestInfoUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.usertype.UserType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTGenerator jwtGenerator;
    private final SecurityUtils securityUtils;
    private final TokenBlacklistService tokenBlacklistService;
    private final ActivityLogRepository activityLogRepository;
    private final RequestInfoUtil requestInfoUtil;

    @Override
    public LoginResponse login(LoginRequest request, HttpServletRequest httpRequest) {
        log.info("Login attempt: {}", request.getUserIdentifier());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUserIdentifier(), request.getPassword())
            );

            User user = userRepository.findByUserIdentifierAndIsDeletedFalse(request.getUserIdentifier())
                    .orElseThrow(() -> new ValidationException("User not found"));

            securityUtils.validateAccountStatus(user);
            
            String token = jwtGenerator.generateAccessToken(authentication);
            LoginResponse response = userMapper.toLoginResponse(user, token);

            // Create activity log after successful login
            createActivityLog(user, request, httpRequest);

            log.info("Login successful: {}", user.getUserIdentifier());
            return response;
            
        } catch (Exception e) {
            log.warn("Login failed: {}", request.getUserIdentifier());
            throw new ValidationException("Invalid credentials");
        }
    }

    @Override
    public void logout(String authorizationHeader) {
        log.info("Processing logout");
        String token = extractToken(authorizationHeader);

        if (token == null || !jwtGenerator.validateToken(token)) {
            throw new ValidationException("Invalid token");
        }

        String userIdentifier = jwtGenerator.getUsernameFromJWT(token);
        tokenBlacklistService.blacklistToken(token, userIdentifier, "LOGOUT");
        
        log.info("Logout successful: {}", userIdentifier);
    }

    @Override
    public UserResponse changePassword(PasswordChangeRequest request) {
        User currentUser = securityUtils.getCurrentUser();

        if (!passwordEncoder.matches(request.getCurrentPassword(), currentUser.getPassword())) {
            throw new ValidationException("Current password is incorrect");
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new ValidationException("Password confirmation does not match");
        }

        currentUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
        User savedUser = userRepository.save(currentUser);

        tokenBlacklistService.blacklistAllUserTokens(currentUser.getUserIdentifier(), "PASSWORD_CHANGE");
        log.info("Password changed: {}", currentUser.getUserIdentifier());

        return userMapper.toResponse(savedUser);
    }

    @Override
    public UserResponse adminResetPassword(AdminPasswordResetRequest request) {
        log.info("Admin password reset: {}", request.getUserId());

        User user = userRepository.findByIdAndIsDeletedFalse(request.getUserId())
                .orElseThrow(() -> new ValidationException("User not found"));

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new ValidationException("Password confirmation does not match");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        User savedUser = userRepository.save(user);

        tokenBlacklistService.blacklistAllUserTokens(user.getUserIdentifier(), "ADMIN_PASSWORD_RESET");
        log.info("Admin password reset: {}", user.getUserIdentifier());

        return userMapper.toResponse(savedUser);
    }

    private String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7).trim();
        }
        return null;
    }

    /**
     * Create activity log entry for successful login
     */
    private void createActivityLog(User user, LoginRequest loginRequest, HttpServletRequest httpRequest) {
        try {
            ActivityLog activityLog = new ActivityLog();
            activityLog.setUser(user);
            activityLog.setProfile(loginRequest.getUserIdentifier());
            
            // Extract request information
            String clientIp = requestInfoUtil.getClientIp(httpRequest);
            activityLog.setClientIp(clientIp);
            activityLog.setDevice(requestInfoUtil.getDeviceInfo(httpRequest));
            activityLog.setPhysicalDevice(requestInfoUtil.getPhysicalDevice(httpRequest));
            activityLog.setLocation(requestInfoUtil.getLocation(clientIp));
            activityLog.setLatitude(requestInfoUtil.getLatitude(httpRequest));
            activityLog.setLongitude(requestInfoUtil.getLongitude(httpRequest));
            
            activityLogRepository.save(activityLog);
            log.debug("Activity log created for user: {}", user.getUserIdentifier());
        } catch (Exception e) {
            // Don't fail the login if activity log creation fails
            log.error("Failed to create activity log for user: {}", user.getUserIdentifier(), e);
        }
    }
}