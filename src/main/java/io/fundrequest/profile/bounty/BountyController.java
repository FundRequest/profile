package io.fundrequest.profile.bounty;

import io.fundrequest.profile.survey.domain.SurveyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class BountyController {

    private SurveyService surveyService;

    public BountyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @RequestMapping("/rewards")
    public ModelAndView rewards(Principal principal) {
        ModelAndView mav = new ModelAndView("rewards");
        mav.addObject("survey", surveyService.getSurveyResult(principal));
        return mav;
    }
}
