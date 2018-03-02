package io.fundrequest.profile.telegram.repository;

import io.fundrequest.profile.telegram.domain.TelegramVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TelegramVerificationRepository extends JpaRepository<TelegramVerification, Long> {

    Optional<TelegramVerification> findByTelegramNameAndSecret(@Param("telegramName") final String userId, final @Param("secret") String secret);

    Optional<TelegramVerification> findByTelegramName(@Param("telegramName") final String telegramName);
}
