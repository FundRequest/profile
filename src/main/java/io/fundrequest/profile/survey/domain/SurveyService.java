package io.fundrequest.profile.survey.domain;

import java.security.Principal;

public interface SurveyService {
    SurveyDto getSurveyResult(Principal principal);
}
