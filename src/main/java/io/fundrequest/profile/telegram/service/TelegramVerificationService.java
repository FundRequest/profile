package io.fundrequest.profile.telegram.service;

import io.fundrequest.profile.telegram.domain.TelegramVerification;
import io.fundrequest.profile.telegram.repository.TelegramVerificationRepository;
import org.keycloak.common.util.Base64Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
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

    @Transactional(readOnly = true)
    public boolean existsForUserId(final String userId) {
        return telegramVerificationRepository.findByUserId(userId).isPresent();
    }

    public Optional<TelegramVerification> getByUserId(final Principal principal) {
        return telegramVerificationRepository.findByUserId(principal.getName());
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

    public void createTelegramVerification(final String userId, final String telegramname) {
        final Optional<TelegramVerification> byUserId = telegramVerificationRepository.findByUserId(userId);
        if (byUserId.isPresent()) {
            TelegramVerification telegramVerification = byUserId.get();
            if (telegramVerification.isVerified()) {
                throw new IllegalArgumentException("Principal has already verified a telegram");
            } else {
                telegramVerification.setTelegramName(telegramname);
                telegramVerification.setUserId(userId);
                telegramVerification.setLastAction(new Date());
                telegramVerification.setSecret(createSecret(userId));
                telegramVerificationRepository.save(
                        telegramVerification
                );
            }
        } else {
            telegramVerificationRepository.save(
                    new TelegramVerification(null, telegramname, userId, createSecret(userId), false, new Date())
            );
        }
    }

    public String createSecret(final String userId) {
        final MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] check = md.digest(userId.getBytes(StandardCharsets.UTF_8));
        return Base64Url.encode(check);
    }
}
