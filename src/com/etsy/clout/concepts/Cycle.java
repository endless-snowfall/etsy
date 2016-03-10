package com.etsy.clout.concepts;

import java.util.List;

import lombok.Setter;
import lombok.Value;
import lombok.experimental.NonFinal;

@Value
public class Cycle {

    @Setter @NonFinal private int cycleClout;
    private List<Person> people;
}
