package io.fundrequest.profile.home;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @RequestMapping("/")
    public ModelAndView home(@RequestParam(value = "ref", required = false) String ref) {
        String profileLink = "/profile";
        if (StringUtils.isNotBlank(ref)) {
            profileLink += "?ref=" + ref;
        }
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("profileLink", profileLink);
        return mav;
    }
}
