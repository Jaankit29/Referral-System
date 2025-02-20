package com.referral.controller;

import com.referral.service.ReferralService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final ReferralService referralService;  // ✅ Use ReferralService instead of UserService

    /**
     * Adds a referral (parent -> child).
     * Example: POST /api/users/refer?parentId=1&newUserId=2
     */
    @PostMapping("/refer")
    public ResponseEntity<String> referUser(@RequestParam Long parentId, @RequestParam Long newUserId) {
        boolean success = referralService.addReferral(parentId, newUserId);  // ✅ Fix method call
        return success ? ResponseEntity.ok("User referred successfully!")
                : ResponseEntity.badRequest().body("Referral limit exceeded or invalid user!");
    }
}
