package com.etsy.clout.service;

import static com.etsy.clout.common.TestUtils.People.*;
import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.etsy.clout.concepts.Person;
import com.google.common.collect.ImmutableMap;

public class CloutServiceImplTest {

    private CloutService cloutService;

    @Before
    public void before() {
        cloutService = new CloutServiceImpl();
    }

    @Test
    public void getClout_UnknownPerson() {
        assertTrue(cloutService.getAllClout().isEmpty());
        assertEquals(0, cloutService.getClout(ANTHONY));
    }

    @Test
    public void follows_Self() {
        cloutService.follows(ANTHONY, ANTHONY);

        assertEquals(0, cloutService.getClout(ANTHONY));
    }

    @Test
    public void follows_TwoPersonLine() {
        cloutService.follows(ANTHONY, MIKE);

        assertEquals(0, cloutService.getClout(ANTHONY));
        assertEquals(1, cloutService.getClout(MIKE));
    }

    @Test
    public void follows_TwoPersonLine_AlreadyFollowing() {
        cloutService.follows(ANTHONY, MIKE);
        cloutService.follows(ANTHONY, MIKE);

        assertEquals(0, cloutService.getClout(ANTHONY));
        assertEquals(1, cloutService.getClout(MIKE));
    }

    @Test
    public void follows_ThreePersonLine_Inwards() {
        cloutService.follows(MIKE, DAVE);
        cloutService.follows(ANTHONY, MIKE);

        assertEquals(0, cloutService.getClout(ANTHONY));
        assertEquals(1, cloutService.getClout(MIKE));
        assertEquals(2, cloutService.getClout(DAVE));
    }

    @Test
    public void follows_ThreePersonLine_Outwards() {
        cloutService.follows(ANTHONY, MIKE);
        cloutService.follows(MIKE, DAVE);

        assertEquals(0, cloutService.getClout(ANTHONY));
        assertEquals(1, cloutService.getClout(MIKE));
        assertEquals(2, cloutService.getClout(DAVE));
    }

    @Test
    public void follows_Triangle() {
        cloutService.follows(ANTHONY, BOB);
        cloutService.follows(MIKE, BOB);

        assertEquals(0, cloutService.getClout(ANTHONY));
        assertEquals(0, cloutService.getClout(MIKE));
        assertEquals(2, cloutService.getClout(BOB));
    }

    @Test
    public void follows_TriangleReparent() {
        cloutService.follows(ANTHONY, BOB);
        cloutService.follows(MIKE, BOB);

        assertEquals(0, cloutService.getClout(ANTHONY));
        assertEquals(0, cloutService.getClout(MIKE));
        assertEquals(2, cloutService.getClout(BOB));

        cloutService.follows(MIKE, DAVE);

        Map<Person, Integer> expected = ImmutableMap.of(
            ANTHONY, 0,
            MIKE, 0,
            BOB, 1,
            DAVE, 1);

        assertEquals(expected, cloutService.getAllClout());
    }

    @Test
    public void follows_TwoPersonCycle() {
        cloutService.follows(ANTHONY, MIKE);
        cloutService.follows(MIKE, ANTHONY);

        assertEquals(1, cloutService.getClout(ANTHONY));
        assertEquals(1, cloutService.getClout(MIKE));
    }

    @Test
    public void follows_ThreePersonCycle() {
        Map<Person, Integer> expected = ImmutableMap.of(
            ANTHONY, 2,
            MIKE, 2,
            DAVE, 2);

        cloutService.follows(ANTHONY, MIKE);
        cloutService.follows(MIKE, DAVE);
        cloutService.follows(DAVE, ANTHONY);

        assertEquals(expected, cloutService.getAllClout());
    }
}
