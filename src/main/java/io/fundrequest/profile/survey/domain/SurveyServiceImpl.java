package io.fundrequest.profile.survey.domain;

import io.fundrequest.profile.survey.infrastructue.SurveyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@AllArgsConstructor
public class SurveyServiceImpl implements SurveyService {

    private final SurveyRepository repository;

    @Transactional(readOnly = true)
    public SurveyDto getSurveyResult(Principal principal) {
        return repository.findByUserId(principal.getName())
                .map(s -> SurveyDto.builder()
                        .status(s.getStatus())
                        .completionDate(s.getCompletionDate())
                        .build()
                ).orElse(null);
    }


}
