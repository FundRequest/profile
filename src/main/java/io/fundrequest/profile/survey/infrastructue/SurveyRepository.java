package io.fundrequest.profile.survey.infrastructue;

import io.fundrequest.profile.survey.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
    Optional<Survey> findByUserId(String userId);

    Optional<Survey> findByEmail(String email);
}
