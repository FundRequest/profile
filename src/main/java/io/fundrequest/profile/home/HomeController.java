package io.fundrequest.profile.home;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class HomeController {

    @RequestMapping("/")
    public ModelAndView home(@RequestParam(value = "ref", required = false) String ref) {
        String profileLink = "/profile";
        if (StringUtils.isNotBlank(ref)) {
            profileLink += "?ref=" + ref;
        }
        return new ModelAndView(new RedirectView(profileLink));
    }
}
