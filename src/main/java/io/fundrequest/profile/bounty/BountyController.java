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
    private BountyService bountyService;

    public BountyController(final SurveyService surveyService,
                            final GithubBountyService githubBountyService,
                            final StackOverflowBountyService stackOverflowBountyService,
                            final BountyService bountyService) {
        this.surveyService = surveyService;
        this.githubBountyService = githubBountyService;
        this.stackOverflowBountyService = stackOverflowBountyService;
        this.bountyService = bountyService;
    }

    @RequestMapping("/rewards")
    public ModelAndView rewards(Principal principal) {
        ModelAndView mav = new ModelAndView("rewards");
        mav.addObject("survey", surveyService.getSurveyResult(principal));
        mav.addObject("githubVerification", githubBountyService.getVerification(principal));
        mav.addObject("stackOverflowVerification", stackOverflowBountyService.getVerification(principal));
        mav.addObject("bounty", bountyService.getBounties(principal));
        return mav;
    }
}
