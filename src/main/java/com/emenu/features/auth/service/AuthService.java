package com.emenu.features.auth.service;

import com.emenu.features.auth.dto.request.AdminPasswordResetRequest;
import com.emenu.features.auth.dto.request.LoginRequest;
import com.emenu.features.auth.dto.request.PasswordChangeRequest;
import com.emenu.features.auth.dto.response.LoginResponse;
import com.emenu.features.auth.dto.response.UserResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    LoginResponse login(LoginRequest request, HttpServletRequest httpRequest);
    
    void logout(String token);

    UserResponse changePassword(PasswordChangeRequest request);
    
    UserResponse adminResetPassword(AdminPasswordResetRequest request);
}