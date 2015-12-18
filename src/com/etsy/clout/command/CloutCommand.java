package com.etsy.clout.command;

import java.util.Optional;
import java.util.stream.Collectors;

import com.etsy.clout.concepts.Person;
import com.etsy.clout.concepts.Response;
import com.etsy.clout.service.CloutService;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor(staticName = "of")
@Value
public class CloutCommand implements Command {

    private CloutService cloutService;
    private Optional<Person> maybePerson;

    @Override
    public Response execute() {
        if (maybePerson.isPresent()) {
            Person person = maybePerson.get();
            return new Response(buildResponse(person, cloutService.getClout(person)));
        }

        return Response.of(cloutService.getAllClout().entrySet().stream()
            .map(entry -> buildResponse(entry.getKey(), entry.getValue()))
            .collect(Collectors.toSet()));
    }

    private String buildResponse(Person person, Integer clout) {
        return String.format("%s has %s.", person.getName(), buildFollowingString(clout));
    }

    private String buildFollowingString(int clout) {
        if (clout == 0) {
            return "no followers";
        } else if (clout == 1) {
            return "1 follower";
        } else {
            return clout + " followers";
        }
    }
}
