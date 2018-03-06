package io.fundrequest.profile.bounty;

import io.fundrequest.profile.github.GithubBountyService;
import io.fundrequest.profile.stackoverflow.StackOverflowBountyService;
import io.fundrequest.profile.survey.domain.SurveyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class BountyController {

    private SurveyService surveyService;
    private GithubBountyService githubBountyService;
    private StackOverflowBountyService stackOverflowBountyService;

    public BountyController(SurveyService surveyService, GithubBountyService githubBountyService, StackOverflowBountyService stackOverflowBountyService) {
        this.surveyService = surveyService;
        this.githubBountyService = githubBountyService;
        this.stackOverflowBountyService = stackOverflowBountyService;
    }

    @RequestMapping("/rewards")
    public ModelAndView rewards(Principal principal) {
        ModelAndView mav = new ModelAndView("rewards");
        mav.addObject("survey", surveyService.getSurveyResult(principal));
        mav.addObject("githubVerification", githubBountyService.getVerification(principal));
        mav.addObject("stackOverflowVerification", stackOverflowBountyService.getVerification(principal));
        return mav;
    }
}
