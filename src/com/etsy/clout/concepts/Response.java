package com.etsy.clout.concepts;

import java.util.Set;

import com.google.common.collect.Sets;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor(staticName = "of")
@Value
public class Response {

    private Set<String> responses;

    public Response(String response) {
        responses = Sets.newHashSet(response);
    }
}
