package com.etsy.clout.concepts;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class Cycle {

    @Setter private int cycleClout;
    private List<Person> people;
}
