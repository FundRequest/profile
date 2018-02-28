package io.fundrequest.profile.bounty.service;

import io.fundrequest.profile.bounty.model.TwitterBounty;
import io.fundrequest.profile.bounty.repository.TwitterBountyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TwitterBountyService {

    @Autowired
    private TwitterBountyRepository twitterBountyRepository;

    public Optional<TwitterBounty> findById() {
        //try
        return Optional.empty();
    }

}
