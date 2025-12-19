package com.emenu.features.auth.repository;

import com.emenu.features.auth.models.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, UUID>,
                                                JpaSpecificationExecutor<ActivityLog> {

    Optional<ActivityLog> findByIdAndIsDeletedFalse(UUID id);

    @org.springframework.data.jpa.repository.Query("SELECT COUNT(DISTINCT a.user) FROM ActivityLog a WHERE a.createdAt BETWEEN :start AND :end")
    long countDistinctUsersActiveBetween(@org.springframework.data.repository.query.Param("start") java.time.LocalDateTime start, @org.springframework.data.repository.query.Param("end") java.time.LocalDateTime end);
}
