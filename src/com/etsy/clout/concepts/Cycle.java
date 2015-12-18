package com.etsy.clout.concepts;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(staticName = "of")
@Getter
public class Cycle {

    @Setter private int cycleClout;
    private Set<Person> people;
}
