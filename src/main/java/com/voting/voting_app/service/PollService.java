package com.voting.voting_app.service;

import com.voting.voting_app.entity.OptionVote;
import com.voting.voting_app.entity.Poll;
import com.voting.voting_app.repository.PollRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PollService {

    private final PollRepository pollRepository;

    public PollService(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    public Poll createPoll(Poll poll) {
        return pollRepository.save(poll);
    }

    public List<Poll> getAllPolls() {
        List<Poll> polls=pollRepository.findAll();
        System.out.println(polls);
        return pollRepository.findAll();
    }

    public Optional<Poll> findPollById(Long id) {
        return pollRepository.findById(id);
    }

    public void vote(Long pollId, int optionIndex) {

        Poll poll=pollRepository.findById(pollId)
                .orElseThrow(()->new RuntimeException("Poll not found"));
        List<OptionVote> options=poll.getOptions();
        if(optionIndex<0 || optionIndex>=options.size()){
            throw new IllegalArgumentException("Invalid option index");
        }
         OptionVote option=options.get(optionIndex);
        option.setVoteCount(option.getVoteCount()+1);
        pollRepository.save(poll);

    }
}
