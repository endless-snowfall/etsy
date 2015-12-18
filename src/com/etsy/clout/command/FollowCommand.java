package com.etsy.clout.command;

import com.etsy.clout.concepts.Person;
import com.etsy.clout.concepts.Response;
import com.etsy.clout.service.CloutService;
import com.google.common.annotations.VisibleForTesting;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(staticName = "of")
@Getter
public class FollowCommand implements Command {

    @VisibleForTesting static final Response SELF_FOLLOW_RESPONSE = new Response("Interesting, but that doesn't make sense.");
    @VisibleForTesting static final Response OK_RESPONSE = new Response("OK!");

    private CloutService cloutService;
    private Person source;
    private Person target;

    @Override
    public Response execute() {
        if (source.equals(target)) {
            return SELF_FOLLOW_RESPONSE;
        }

        cloutService.follows(source, target);
        return OK_RESPONSE;
    }
}
