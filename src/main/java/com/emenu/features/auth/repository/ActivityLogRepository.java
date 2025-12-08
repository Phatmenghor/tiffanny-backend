package com.emenu.features.auth.repository;

import com.emenu.features.auth.models.ActivityLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, UUID> {

    Optional<ActivityLog> findByIdAndIsDeletedFalse(UUID id);

    @Query("SELECT al FROM ActivityLog al " +
            "LEFT JOIN FETCH al.user u " +
            "WHERE al.isDeleted = false " +
            "AND (:userId IS NULL OR al.user.id = :userId) " +
            "AND (:clientIp IS NULL OR :clientIp = '' OR al.clientIp = :clientIp) " +
            "AND (:startDate IS NULL OR al.createdAt >= :startDate) " +
            "AND (:endDate IS NULL OR al.createdAt <= :endDate) " +
            "AND (:search IS NULL OR :search = '' OR " +
            "    LOWER(al.device) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "    LOWER(al.physicalDevice) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "    LOWER(al.location) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "    LOWER(al.profile) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<ActivityLog> searchActivityLogs(
            @Param("userId") UUID userId,
            @Param("clientIp") String clientIp,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("search") String search,
            Pageable pageable
    );
}
