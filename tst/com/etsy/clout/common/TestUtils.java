package com.etsy.clout.common;

import static com.etsy.clout.common.TestUtils.People.*;
import static org.junit.Assert.*;

import java.util.Map;

import com.etsy.clout.concepts.Person;
import com.etsy.clout.service.CloutService;
import com.google.common.collect.ImmutableMap;

public class TestUtils {

    public static class People {

        public static final Person ANTHONY = Person.of("Anthony");
        public static final Person MIKE = Person.of("Mike");
        public static final Person BOB = Person.of("Bob");

        public static final Person DAVE = Person.of("Dave");
        public static final Person NEYMAR = Person.of("Neymar");
        public static final Person XAVI = Person.of("Xavi");

        public static final Person MESSI = Person.of("Messi");
        public static final Person JAKE = Person.of("Jake");
        public static final Person BILL = Person.of("Bill");
        public static final Person TED = Person.of("Ted");
        public static final Person JASON = Person.of("Jason");

        public static final Person DUDE = Person.of("Dude");
    }

    public static Map<Person, Integer> createAnthonyRootedTreeComponent(CloutService cloutService) {
        cloutService.follows(MIKE, ANTHONY);
        cloutService.follows(BOB, ANTHONY);
        assertEquals(2, cloutService.getClout(ANTHONY));
        assertEquals(0, cloutService.getClout(MIKE));
        assertEquals(0, cloutService.getClout(BOB));

        Map<Person, Integer> expected = ImmutableMap.of(
            ANTHONY, 2,
            MIKE, 0,
            BOB, 0);

        assertExpected(cloutService, expected);
        return expected;
    }

    public static Map<Person, Integer> createDaveRootedTreeComponent(CloutService cloutService) {
        cloutService.follows(NEYMAR, DAVE);
        cloutService.follows(XAVI, DAVE);

        Map<Person, Integer> expected = ImmutableMap.of(
            DAVE, 2,
            NEYMAR, 0,
            XAVI, 0);

        assertExpected(cloutService, expected);
        return expected;
    }

    public static Map<Person, Integer> createCycleComponent(CloutService cloutService) {
        cloutService.follows(MESSI, JAKE);
        cloutService.follows(JAKE, BILL);
        cloutService.follows(BILL, MESSI);

        Map<Person, Integer> expected = ImmutableMap.of(
            MESSI, 2,
            JAKE, 2,
            BILL, 2);

        assertExpected(cloutService, expected);
        return expected;
    }

    public static void assertIncreased(CloutService cloutService, Map<Person, Integer> original, int increase, Person... people) {
        for (Person person : people) {
            assertEquals(person.getName(), original.getOrDefault(person, 0) + increase, cloutService.getClout(person));
        }
    }

    public static void assertDecreased(CloutService cloutService, Map<Person, Integer> original, int decrease, Person... people) {
        assertTrue(decrease >= 0);
        assertIncreased(cloutService, original, decrease * -1, people);
    }

    public static void assertUnchanged(CloutService cloutService, Map<Person, Integer> original, Person... people) {
        for (Person person : people) {
            assertEquals(original.get(person).intValue(), cloutService.getClout(person));
        }
    }

    public static void assertExpected(CloutService cloutService, Map<Person, Integer> expected) {
        expected.entrySet().forEach(entry -> assertEquals(
            entry.getKey().getName(),
            entry.getValue().intValue(),
            cloutService.getClout(entry.getKey())));
    }
}
