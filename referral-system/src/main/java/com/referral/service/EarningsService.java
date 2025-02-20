package com.referral.service;

import com.referral.model.Earnings;
import com.referral.model.User;
import com.referral.repository.EarningsRepository;
import com.referral.repository.UserRepository;
import com.referral.websocket.EarningsWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EarningsService {

    private final EarningsRepository earningsRepository;
    private final UserRepository userRepository;
    private final EarningsWebSocketHandler earningsWebSocketHandler;

    @Transactional
    public void recordEarnings(Long userId, double purchaseAmount) {
        if (purchaseAmount < 1000) {
            return; // Ignore purchases below 1000Rs
        }

        Optional<User> buyerOpt = userRepository.findById(userId);
        if (buyerOpt.isEmpty()) {
            return;
        }

        User buyer = buyerOpt.get();
        Long parentId = buyer.getReferredBy();

        // Level 1 Profit (Direct Referral - 5%)
        if (parentId != null) {
            double parentEarning = purchaseAmount * 0.05;
            addEarnings(parentId, parentEarning, "Direct Referral from " + buyer.getEmail());
        }

        // Level 2 Profit (Indirect Referral - 1%)
        Optional<User> parentOpt = userRepository.findById(parentId);
        if (parentOpt.isPresent()) {
            Long grandParentId = parentOpt.get().getReferredBy();
            if (grandParentId != null) {
                double grandParentEarning = purchaseAmount * 0.01;
                addEarnings(grandParentId, grandParentEarning, "Indirect Referral from " + buyer.getEmail());
            }
        }
    }

    private void addEarnings(Long userId, double amount, String source) {
        Earnings earnings = new Earnings();
        earnings.setUserId(userId);
        earnings.setAmount(amount);
        earnings.setSourceUserEmail(source);
        earningsRepository.save(earnings);

        // Send real-time WebSocket update
        earningsWebSocketHandler.sendEarningsUpdate(userId, "New earnings: Rs. " + amount + " from " + source);
    }
}
