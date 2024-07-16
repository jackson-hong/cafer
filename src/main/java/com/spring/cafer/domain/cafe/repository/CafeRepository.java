package com.spring.cafer.domain.cafe.repository;

import com.spring.cafer.domain.cafe.entity.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeRepository extends JpaRepository<Cafe, Long> {
}
