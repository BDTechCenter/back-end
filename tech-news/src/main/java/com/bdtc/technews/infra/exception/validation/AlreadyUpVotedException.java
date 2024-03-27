package com.bdtc.technews.infra.exception.validation;

public class AlreadyUpVotedException extends RuntimeException {
    public AlreadyUpVotedException() {
        super("You can't upVote twice!");
    }
}
