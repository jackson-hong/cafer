package com.spring.cafer.domain.cafe.repository;

import com.spring.cafer.domain.cafe.entity.CoordinateHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordinateHistoryRepository extends JpaRepository<CoordinateHistory, Long> {
    boolean existsByXAndY(String x, String y);
}