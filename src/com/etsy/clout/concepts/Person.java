package com.etsy.clout.concepts;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor(staticName = "of")
@Value
public class Person {

    private String name;
}
