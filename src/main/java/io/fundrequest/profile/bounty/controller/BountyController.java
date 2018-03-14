package io.fundrequest.profile.bounty.controller;

import io.fundrequest.profile.bounty.service.BountyService;
import io.fundrequest.profile.github.GithubBountyService;
import io.fundrequest.profile.linkedin.LinkedInService;
import io.fundrequest.profile.profile.ProfileService;
import io.fundrequest.profile.stackoverflow.StackOverflowBountyService;
import io.fundrequest.profile.survey.domain.SurveyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class BountyController {

    private SurveyService surveyService;
    private GithubBountyService githubBountyService;
    private StackOverflowBountyService stackOverflowBountyService;
    private BountyService bountyService;
    private ProfileService profileService;
    private LinkedInService linkedInService;

    public BountyController(final SurveyService surveyService,
                            final GithubBountyService githubBountyService,
                            final StackOverflowBountyService stackOverflowBountyService,
                            final BountyService bountyService,
                            final ProfileService profileService,
                            LinkedInService linkedInService) {
        this.surveyService = surveyService;
        this.githubBountyService = githubBountyService;
        this.stackOverflowBountyService = stackOverflowBountyService;
        this.bountyService = bountyService;
        this.linkedInService = linkedInService;
        this.profileService = profileService;
    }

    @RequestMapping("/rewards")
    public ModelAndView rewards(Principal principal, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("rewards");
        mav.addObject("survey", surveyService.getSurveyResult(principal));
        mav.addObject("githubVerification", githubBountyService.getVerification(principal));
        mav.addObject("stackOverflowVerification", stackOverflowBountyService.getVerification(principal));
        mav.addObject("linkedInVerification", linkedInService.getVerification(principal));
        mav.addObject("profile", profileService.getUserProfile(request, principal));
        mav.addObject("bounty", bountyService.getBounties(principal));
        return mav;
    }
}
