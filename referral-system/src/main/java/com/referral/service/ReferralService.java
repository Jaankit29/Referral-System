package com.referral.service;

import com.referral.model.User;
import com.referral.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReferralService {

    private final UserRepository userRepository;

    /**
     * Adds a new referral to the parent user.
     * A user can refer up to 8 users directly.
     */
    public boolean addReferral(Long parentId, Long newUserId) {
        Optional<User> newUserOpt = userRepository.findById(newUserId);
        Optional<User> parentUserOpt = userRepository.findById(parentId);

        if (newUserOpt.isEmpty() || parentUserOpt.isEmpty()) {
            return false; // Invalid users
        }

        List<User> directReferrals = userRepository.findByReferredBy(parentId);
        if (directReferrals.size() >= 8) {
            return false; // Max referral limit reached
        }

        User newUser = newUserOpt.get();
        newUser.setReferredBy(parentId);
        userRepository.save(newUser);
        return true;
    }

    /**
     * Retrieves the direct referrals of a user.
     */
    public List<User> getDirectReferrals(Long userId) {
        return userRepository.findByReferredBy(userId);
    }
}
