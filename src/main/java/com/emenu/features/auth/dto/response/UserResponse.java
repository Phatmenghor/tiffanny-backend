package com.emenu.features.auth.dto.response;

import com.emenu.enums.user.AccountStatus;
import com.emenu.enums.user.RoleEnum;
import com.emenu.shared.dto.BaseAuditResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.usertype.UserType;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserResponse extends BaseAuditResponse {
    
    private String userIdentifier;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String profileImageUrl;
    private AccountStatus accountStatus;
    private List<RoleEnum> roles;
    private String position;
    private String address;
    private UUID businessId;
    private String businessName;
}