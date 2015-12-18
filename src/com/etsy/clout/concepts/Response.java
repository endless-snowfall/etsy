package com.etsy.clout.concepts;

import java.util.Set;

import com.google.common.collect.Sets;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode
public class Response {

    @Getter private Set<String> responses;

    public Response(String response) {
        responses = Sets.newHashSet(response);
    }
}
