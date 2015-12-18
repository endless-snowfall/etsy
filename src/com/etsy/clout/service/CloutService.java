package com.etsy.clout.service;

import java.util.Map;

import com.etsy.clout.concepts.Person;

public interface CloutService {

    void follows(Person source, Person target);

    int getClout(Person person);

    Map<Person, Integer> getAllClout();
}
