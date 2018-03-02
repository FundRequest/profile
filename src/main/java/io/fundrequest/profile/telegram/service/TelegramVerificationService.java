package io.fundrequest.profile.telegram.service;

import io.fundrequest.profile.telegram.domain.TelegramVerification;
import io.fundrequest.profile.telegram.repository.TelegramVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class TelegramVerificationService {

    @Autowired
    private TelegramVerificationRepository telegramVerificationRepository;

    @Transactional(readOnly = true)
    public boolean exists(final String telegramName) {
        return telegramVerificationRepository.findByTelegramName(telegramName).isPresent();
    }

    @Transactional
    public boolean verify(final String telegramName, final String secret) {
        final Optional<TelegramVerification> byUserIdAndSecret = telegramVerificationRepository.findByTelegramNameAndSecret(telegramName, secret);
        if (byUserIdAndSecret.isPresent()) {
            TelegramVerification telegramVerification = byUserIdAndSecret.get();
            if (!telegramVerification.isVerified()) {
                //payout lads!
            }
            telegramVerification.setLastAction(new Date());
            telegramVerification.setVerified(true);
            telegramVerificationRepository.save(telegramVerification);
            return true;
        } else {
            return false;
        }
    }

}
