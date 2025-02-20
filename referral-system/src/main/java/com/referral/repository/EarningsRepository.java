package com.referral.repository;

import com.referral.model.Earnings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EarningsRepository extends JpaRepository<Earnings, Long> {
    List<Earnings> findByUserId(Long userId);
}
