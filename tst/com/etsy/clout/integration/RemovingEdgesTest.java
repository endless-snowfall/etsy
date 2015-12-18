package com.etsy.clout.integration;

import static com.etsy.clout.common.TestUtils.*;
import static com.etsy.clout.common.TestUtils.People.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.etsy.clout.concepts.Person;
import com.etsy.clout.service.CloutService;
import com.etsy.clout.service.CloutServiceImpl;
import com.google.common.collect.ImmutableMap;

public class RemovingEdgesTest {

    private CloutService cloutService;

    @Before
    public void before() {
        cloutService = new CloutServiceImpl();
    }

    @Test
    public void removeEdge_From_TreeComponent() {
        Map<Person, Integer> anthonyTreeClout = createAnthonyRootedTreeComponent(cloutService);

        cloutService.follows(BOB, DUDE);

        int decrease = cloutService.getClout(BOB) + 1;
        assertDecreased(cloutService, anthonyTreeClout, decrease, ANTHONY);
        assertUnchanged(cloutService, anthonyTreeClout, BOB);
        assertIncreased(cloutService, anthonyTreeClout, 1, DUDE);
    }

    @Test
    public void removeEdge_From_TreeComponent_ConnectedTo_CycleComponent() {
        Map<Person, Integer> anthonyTreeClout = createAnthonyRootedTreeComponent(cloutService);
        Map<Person, Integer> cycleClout = createCycleComponent(cloutService);

        cloutService.follows(ANTHONY, MESSI);

        int increase = cloutService.getClout(ANTHONY) + 1;
        assertIncreased(cloutService, cycleClout, increase, MESSI, JAKE, BILL);
        assertUnchanged(cloutService, anthonyTreeClout, ANTHONY, MIKE, BOB);
    }

    @Test
    public void removeEdge_From_CycleComponent() {
        Map<Person, Integer> cycleClout = createCycleComponent(cloutService);

        int formerCycleClout = cycleClout.get(MESSI);
        cloutService.follows(MESSI, DUDE);

        Map<Person, Integer> expected = ImmutableMap.of(
            DUDE, 1,
            MESSI, formerCycleClout,
            BILL, 1,
            JAKE, 0);

        assertExpected(cloutService, expected);
    }
}
