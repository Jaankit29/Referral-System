package com.referral.repository;

import com.referral.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    // Find all users referred by a specific user (for referral hierarchy)
    List<User> findByReferredBy(Long referredBy);
}
