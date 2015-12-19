package com.etsy.clout.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.etsy.clout.concepts.Cycle;
import com.etsy.clout.concepts.Person;

public class CloutServiceImpl implements CloutService {

    private Map<Person, Person> follows = new HashMap<>();
    private Map<Person, Integer> nonCycleClout = new HashMap<>();
    private Map<Person, Cycle> cycles = new HashMap<>();

    @Override
    public void follows(Person source, Person target) {
        if (isSamePerson(source, target) || isAlreadyFollowing(source, target)) {
            return;
        }

        // need to remove an existing edge in the graph
        if (follows.containsKey(source)) {
            removeEdge(source, follows.get(source));
        }

        addEdge(source, target);
    }

    @Override
    public int getClout(Person person) {
        return cycles.containsKey(person)
            ? cycles.get(person).getCycleClout()
            : nonCycleClout.getOrDefault(person, 0);
    }

    @Override
    public Map<Person, Integer> getAllClout() {
        Map<Person, Integer> result = new HashMap<>();
        result.putAll(nonCycleClout);
        cycles.entrySet().stream()
            .forEach(entry -> result.put(entry.getKey(), entry.getValue().getCycleClout()));
        return result;
    }

    private boolean isSamePerson(Person source, Person target) {
        return source.equals(target);
    }

    private boolean isAlreadyFollowing(Person source, Person target) {
        return follows.containsKey(source) && follows.get(source).equals(target);
    }

    private void removeEdge(Person source, Person target) {
        follows.remove(source, target);

        if (cycles.containsKey(source)) {
            removeCycleEdge(target);
        } else {
            removeNonCycleEdge(source, target);
        }
    }

    private void removeCycleEdge(Person target) {
        Person current = target;
        int accumulatedClout = 0;

        while (current != null) {
            cycles.remove(current);
            int nonCycleCloutValue = nonCycleClout.get(current);
            nonCycleClout.put(current, nonCycleCloutValue + accumulatedClout);
            accumulatedClout += nonCycleCloutValue + 1;
            current = follows.get(current);
        }
    }

    private void removeNonCycleEdge(Person source, Person target) {
        int cloutToSubtract = nonCycleClout.getOrDefault(source, 0) + 1;
        Person current = target;

        while (current != null) {
            nonCycleClout.put(current, nonCycleClout.getOrDefault(current, 0) - cloutToSubtract);

            if (cycles.containsKey(current)) {
                Cycle cycle = cycles.get(current);
                cycle.setCycleClout(cycle.getCycleClout() - cloutToSubtract);
                break;
            }

            current = follows.get(current);
        }
    }

    private void addEdge(Person source, Person target) {
        nonCycleClout.putIfAbsent(source, 0);
        int sourceClout = nonCycleClout.get(source);

        if (willCreateCycle(source, target)) {
            createCycle(target, sourceClout);
            follows.put(source, target);
            return;
        }

        int cloutToAdd = sourceClout + 1;
        Person current = target;

        while (current != null) {

            // if (current == source) {
            // createCycle(target, sourceClout);
            // follows.put(source, target);
            // break;
            // }

            nonCycleClout.put(current, nonCycleClout.getOrDefault(current, 0) + cloutToAdd);

            if (cycles.containsKey(current)) {
                Cycle cycle = cycles.get(current);
                cycle.setCycleClout(cycle.getCycleClout() + cloutToAdd);
                break;
            }

            current = follows.get(current);
        }

        follows.put(source, target);
    }

    private void createCycle(Person start, int cycleClout) {
        List<Person> people = new LinkedList<>();
        Cycle cycle = new Cycle(cycleClout, people);
        Person current = start;
        int accumulatedClout = 0;

        while (current != null) {
            people.add(current);
            int nonCycleCloutValue = nonCycleClout.get(current);
            nonCycleClout.put(current, nonCycleCloutValue - accumulatedClout);
            accumulatedClout = nonCycleCloutValue + 1;
            cycles.put(current, cycle);
            current = follows.get(current);
        }
    }

    private boolean willCreateCycle(Person start, Person target) {
        Set<Person> seen = new HashSet<>();
        Person current = target;

        while (current != null) {
            if (follows.get(current) == start) {
                return true;
            }

            if (seen.contains(current)) {
                return false;
            }

            seen.add(current);
            current = follows.get(current);
        }

        return false;
    }
}
