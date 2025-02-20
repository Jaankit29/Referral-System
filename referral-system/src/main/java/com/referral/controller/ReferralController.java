package com.referral.controller;

import com.referral.model.User;
import com.referral.service.ReferralService;
import com.referral.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/referral")
//@RequiredArgsConstructor
public class ReferralController {

    private final ReferralService referralService;
    private final UserService userService;

    // Manually define the constructor
    public ReferralController(ReferralService referralService, UserService userService) {
        this.referralService = referralService;
        this.userService = userService;
    }
    /**
     * Adds a referral (parent -> child).
     * URL Example: POST /api/referral/refer/1/2
     */
    @PostMapping("/refer/{parentId}/{newUserId}")
    public ResponseEntity<String> referUser(@PathVariable Long parentId, @PathVariable Long newUserId) {
        boolean success = referralService.addReferral(parentId, newUserId);
        return success ? ResponseEntity.ok("Referral added successfully") :
                ResponseEntity.badRequest().body("Referral limit exceeded or invalid user");
    }

    /**
     * Retrieves direct referrals of a user.
     * URL Example: GET /api/referral/direct/1
     */
    @GetMapping("/direct/{userId}")
    public ResponseEntity<List<User>> getDirectReferrals(@PathVariable Long userId) {
        List<User> referrals = referralService.getDirectReferrals(userId);
        return ResponseEntity.ok(referrals);
    }

    /**
     * Registers a new user.
     * URL Example: POST /api/referral/register
     * Request Body: { "name": "John Doe", "email": "john@example.com", "referredBy": 1 }
     */
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User registeredUser = userService.registerUser(user.getName(), user.getEmail(), user.getReferredBy());
        return ResponseEntity.ok(registeredUser);
    }

    /**
     * Fetches user details.
     * URL Example: GET /api/referral/user/1
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        Optional<User> userOpt = userService.getUserById(userId);
        return userOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
