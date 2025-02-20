package com.referral.service;

import com.referral.model.User;
import com.referral.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ReferralService referralService;

    /**
     * Registers a new user.
     */
    public User registerUser(String name, String email, Long referredBy) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setReferredBy(referredBy);

        userRepository.save(user);
        return user;
    }

    /**
     * Processes a referral by adding a referred user.
     */
    public boolean processReferral(Long parentId, Long newUserId) {
        return referralService.addReferral(parentId, newUserId);
    }

    /**
     * Gets user details by ID.
     */
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }
}
