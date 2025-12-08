package com.emenu.features.auth.models;

import com.emenu.enums.user.AccountStatus;
import com.emenu.shared.domain.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.usertype.UserType;

import java.util.List;

@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_user_deleted", columnList = "is_deleted"),
        @Index(name = "idx_user_identifier", columnList = "user_identifier, is_deleted"),
        @Index(name = "idx_user_business", columnList = "business_id, is_deleted")
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseUUIDEntity {

    @Column(name = "user_identifier", nullable = false, unique = true)
    private String userIdentifier;

    @Column(name = "email")
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", nullable = false)
    private AccountStatus accountStatus = AccountStatus.ACTIVE;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    @Column(name = "position")
    private String position;

    @Column(name = "address")
    private String address;

    public String getFullName() {
        if (firstName != null && lastName != null) {
            return firstName + " " + lastName;
        } else if (firstName != null) {
            return firstName;
        } else if (lastName != null) {
            return lastName;
        }
        return userIdentifier;
    }

    public boolean isActive() {
        return AccountStatus.ACTIVE.equals(accountStatus);
    }
}
