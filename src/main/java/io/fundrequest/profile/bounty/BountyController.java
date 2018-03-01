package io.fundrequest.profile.bounty;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BountyController {

    @RequestMapping("/rewards")
    public String rewards() {
        return "rewards";
    }
}
