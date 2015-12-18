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
        createCycleComponent(cloutService);
        cloutService.follows(TED, DUDE);

        Map<Person, Integer> expected = ImmutableMap.of(
            BILL, 2,
            JAKE, 2,
            MESSI, 2,
            DUDE, 1,
            TED, 0);

        assertExpected(cloutService, expected);
    }

    @Test
    public void removeEdge_From_CycleComponent() {
        createCycleComponent(cloutService);
        cloutService.follows(MESSI, DUDE);

        Map<Person, Integer> expected = ImmutableMap.of(
            DUDE, 4,
            MESSI, 3,
            BILL, 1,
            JAKE, 0,
            TED, 0);

        assertExpected(cloutService, expected);
    }
}
